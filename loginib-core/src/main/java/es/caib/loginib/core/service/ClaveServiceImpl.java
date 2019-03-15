package es.caib.loginib.core.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.loginib.core.api.exception.ErrorNoControladoException;
import es.caib.loginib.core.api.exception.ErrorRespuestaClaveException;
import es.caib.loginib.core.api.exception.GenerarPeticionClaveException;
import es.caib.loginib.core.api.exception.TicketNoValidoException;
import es.caib.loginib.core.api.model.comun.ConstantesNumero;
import es.caib.loginib.core.api.model.login.DatosLogoutSesion;
import es.caib.loginib.core.api.model.login.DatosRepresentante;
import es.caib.loginib.core.api.model.login.DatosSesion;
import es.caib.loginib.core.api.model.login.DatosUsuario;
import es.caib.loginib.core.api.model.login.PeticionClave;
import es.caib.loginib.core.api.model.login.PeticionClaveLogout;
import es.caib.loginib.core.api.model.login.RespuestaClaveLogout;
import es.caib.loginib.core.api.model.login.TicketClave;
import es.caib.loginib.core.api.model.login.types.TypeIdp;
import es.caib.loginib.core.api.service.ClaveService;
import es.caib.loginib.core.api.util.ClaveLoginUtil;
import es.caib.loginib.core.interceptor.NegocioInterceptor;
import es.caib.loginib.core.service.repository.dao.ClaveDao;
import es.caib.loginib.core.service.util.AFirmaUtil;
import es.caib.loginib.core.service.util.SamlUtil;
import eu.stork.peps.auth.commons.IPersonalAttributeList;
import eu.stork.peps.auth.commons.PEPSUtil;
import eu.stork.peps.auth.commons.PersonalAttribute;
import eu.stork.peps.auth.commons.PersonalAttributeList;
import eu.stork.peps.auth.commons.STORKAuthnRequest;
import eu.stork.peps.auth.commons.STORKAuthnResponse;
import eu.stork.peps.auth.commons.STORKLogoutRequest;
import eu.stork.peps.auth.commons.STORKLogoutResponse;
import eu.stork.peps.auth.engine.STORKSAMLEngine;
import eu.stork.peps.exceptions.STORKSAMLEngineException;

/**
 * Implementación SecurityService.
 *
 * @author Indra
 */
@Service("claveService")
@Transactional
public final class ClaveServiceImpl implements ClaveService {

	/** Log. */
	private final org.slf4j.Logger log = LoggerFactory.getLogger(ClaveServiceImpl.class);

	/** Configuracion. */
	@Autowired
	private ModuleConfig config;

	/** Dao. */
	@Autowired
	private ClaveDao claveDao;

	@Override
	@NegocioInterceptor
	public String iniciarLoginClave(final String entidad, final String pUrlCallback, final String pUrlCallbackError,
			final String idioma, final List<TypeIdp> idps, final int qaa, final boolean forceAuth,
			final String aplicacion) {
		log.debug(" Crea sesion clave: [idps = " + idps + "] [urlCallback = " + pUrlCallback + "]");
		final String idSesion = claveDao.crearSesionLogin(entidad, pUrlCallback, pUrlCallbackError, idioma, idps, qaa,
				forceAuth, aplicacion);
		log.debug(" Creada sesion clave:  [idSesion = " + idSesion + "] [idps = " + idps + "] [urlCallback = "
				+ pUrlCallback + "]");
		return idSesion;
	}

	@Override
	@NegocioInterceptor
	public String iniciarLogoutClave(final String entidad, final String pUrlCallback, final String idioma,
			final String aplicacion) {
		log.debug(" Crea logout sesion clave...");
		final String idSesion = claveDao.crearSesionLogut(entidad, pUrlCallback, idioma, aplicacion);
		log.debug(" Creada logout sesion clave: " + idSesion);
		return idSesion;
	}

	@Override
	@NegocioInterceptor
	public DatosSesion obtenerDatosSesionLogin(final String idSesion) {
		// Obtener datos sesion
		final DatosSesion datosSesion = claveDao.obtenerDatosSesionLogin(idSesion);
		return datosSesion;
	}

	@Override
	@NegocioInterceptor
	public PeticionClave generarPeticionLoginClave(final String idSesion) {

		log.debug(" Generar peticion clave: [idSesion = " + idSesion + "]");

		// Obtener datos sesion
		final DatosSesion datosSesion = claveDao.obtenerDatosSesionLogin(idSesion);
		if (datosSesion.getFechaTicket() != null) {
			throw new GenerarPeticionClaveException(
					"Sesion ya ha sido autenticada [idSesion = " + datosSesion.getIdSesion() + "]");
		}

		// Atributos a consultar
		final IPersonalAttributeList pAttList = new PersonalAttributeList();
		/* eIdentifier */
		PersonalAttribute att = new PersonalAttribute();
		att.setName("eIdentifier");
		att.setIsRequired(true);
		pAttList.add(att);
		/* givenName */
		att = new PersonalAttribute();
		att.setName("givenName");
		att.setIsRequired(true);
		pAttList.add(att);
		/* surname */
		att = new PersonalAttribute();
		att.setName("surname");
		att.setIsRequired(false);
		pAttList.add(att);
		/* inheritedFamilyName: desglose apellidos */
		att = new PersonalAttribute();
		att.setName("inheritedFamilyName");
		att.setIsRequired(false);
		pAttList.add(att);
		/* respuesta @firma */
		att = new PersonalAttribute();
		att.setName("afirmaResponse");
		att.setIsRequired(false);
		pAttList.add(att);

		// Parametros peticion
		STORKAuthnRequest authnRequest = new STORKAuthnRequest();
		authnRequest.setDestination(config.getPepsUrl());
		authnRequest.setProviderName(config.getProviderName(datosSesion.getEntidad()));
		authnRequest.setForceAuthN(datosSesion.isForceAuth());
		authnRequest.setQaa(datosSesion.getQaa());
		authnRequest.setPersonalAttributeList(pAttList);
		authnRequest.setAssertionConsumerServiceURL((config.getLoginCallbackClave()) + "/" + idSesion + ".html");
		authnRequest.setSpSector(config.getSpSector(datosSesion.getEntidad()));
		authnRequest.setSpApplication(config.getSpApplication(datosSesion.getEntidad()));
		authnRequest.setSPID(config.getSpId(datosSesion.getEntidad()));

		// Generamos peticion SAML
		final STORKSAMLEngine engine = STORKSAMLEngine.getInstance(datosSesion.getEntidad());
		if (engine == null) {
			throw new GenerarPeticionClaveException(
					"Error creando engine STORK. Revise localizacion fichero SignModule_<entidad>.xml (segun SamlEngine.xml)");
		}
		try {
			authnRequest = engine.generateSTORKAuthnRequest(authnRequest);
		} catch (final STORKSAMLEngineException e) {
			throw new GenerarPeticionClaveException(e);
		}

		// Pasamos a B64 y retornamos
		final byte[] token = authnRequest.getTokenSaml();
		final String samlRequestB64 = PEPSUtil.encodeSAMLToken(token);
		final String samlRequestXML = new String(token);
		log.debug(" Peticion generada [idSesion = " + idSesion + "]: " + samlRequestXML);

		// Extraemos saml id peticion y guardamos en sesion
		final String samlId = SamlUtil.extraerSamlId(samlRequestB64);
		if (StringUtils.isBlank(samlId)) {
			throw new GenerarPeticionClaveException(
					"No se ha podido extraer SamlId de la peticion [idSesion = " + idSesion + "]");
		}
		claveDao.establecerSamlIdSesionLogin(idSesion, samlId);

		// Devolvemos datos necesarios para invocar a Clave
		final PeticionClave peticionClave = new PeticionClave();
		peticionClave.setSamlRequestB64(samlRequestB64);
		peticionClave.setUrlClave(config.getPepsUrl());
		peticionClave.setIdioma(datosSesion.getIdioma());
		peticionClave.setIdps(ClaveLoginUtil.removeAnonimoFromIdps(datosSesion.getIdps()));
		return peticionClave;

	}

	@Override
	@NegocioInterceptor
	public TicketClave procesarRespuestaLoginClave(final String pIdSesion, final String pSamlResponseB64,
			final String relayStateRequest) {

		log.debug(" Procesando respuesta clave [idSesion = " + pIdSesion + "]");

		// Recuperamos datos sesion
		final DatosSesion datosSesion = claveDao.obtenerDatosSesionLogin(pIdSesion);
		if (datosSesion.getFechaTicket() != null) {
			throw new GenerarPeticionClaveException(
					"Sesion ya ha sido autenticada [idSesion = " + datosSesion.getIdSesion() + "]");
		}

		// Decodifica respuesta Clave
		STORKAuthnResponse authnResponse = null;
		// final IPersonalAttributeList personalAttributeList = null;
		final byte[] decSamlToken = PEPSUtil.decodeSAMLToken(pSamlResponseB64);
		final STORKSAMLEngine engine = STORKSAMLEngine.getInstance(datosSesion.getEntidad());
		if (engine == null) {
			throw new GenerarPeticionClaveException(
					"Error creando engine STORK. Revise localizacion fichero SignModule_<entidad>.xml (segun SamlEngine.xml)");
		}
		try {
			authnResponse = engine.validateSTORKAuthnResponse(decSamlToken, "");
		} catch (final STORKSAMLEngineException e) {
			throw new ErrorRespuestaClaveException(e, pIdSesion);
		}

		// Verificamos si hay error al interpretar la respuesta
		if (authnResponse.isFail()) {
			log.debug("La respuesta indica que hay un error [idSesion = " + pIdSesion + "]: "
					+ authnResponse.getMessage());
			throw new ErrorRespuestaClaveException(
					"La respuesta indica que hay un error : " + authnResponse.getMessage(), pIdSesion);
		}

		// Verificamos que la peticion se corresponde a la sesion
		if (!StringUtils.equals(datosSesion.getSamlIdPeticion(), authnResponse.getInResponseTo())) {
			log.debug("La respuesta no se corresponde a la peticion SAML origen [idSesion = " + pIdSesion + "]");
			throw new ErrorRespuestaClaveException("La respuesta no se corresponde a la peticion SAML origen",
					pIdSesion);
		}

		// Obtenemos atributos

		// TODO PENDIENTE RESOLVER COMPILACION
		// final String idpClave =
		// authnResponse.getAssertions().get(0).getIssuer().getValue();
		final String idpClave = "PENDIENTE";

		log.debug(" Idp retornado de Clave [idSesion = " + pIdSesion + "]: " + idpClave);

		final TypeIdp idp = ClaveLoginUtil.traduceIdpClaveToIdp(idpClave);
		if (idp == null) {
			throw new ErrorRespuestaClaveException(
					"Idp retornado no contemplado [idSesion = " + pIdSesion + "]: " + idpClave, pIdSesion);
		}

		String nifClave = null;
		String nombre = null;
		String apellidos = null;
		String apellido1 = null;
		String apellido2 = null;
		String afirmaResponse = null;
		final ArrayList<PersonalAttribute> attrList = new ArrayList<PersonalAttribute>(
				authnResponse.getPersonalAttributeList().values());
		for (final PersonalAttribute pa : attrList) {
			log.debug(pa.getName() + " = " + pa.getValue().get(0));
			if ("eIdentifier".equals(pa.getName())) {
				nifClave = pa.getValue().get(0);
				log.debug("eIdentifier -> NIF: " + nifClave);
			} else if ("givenName".equals(pa.getName())) {
				nombre = pa.getValue().get(0);
				log.debug("givenName -> Nombre: " + nombre);
			} else if ("surname".equals(pa.getName())) {
				apellidos = pa.getValue().get(0);
				log.debug("surname -> Apellidos: " + apellidos);
			} else if ("inheritedFamilyName".equals(pa.getName())) {
				apellido1 = pa.getValue().get(0);
				log.debug("inheritedFamilyName -> Apellido1: " + apellido1);
			} else if ("afirmaResponse".equals(pa.getName())) {
				afirmaResponse = pa.getValue().get(0);
			}
		}

		String nif = ClaveLoginUtil.extraerNif(nifClave);
		if (nif == null) {
			log.debug("La respuesta devuelve un nif no valido [idSesion = " + pIdSesion + "]");
			throw new ErrorRespuestaClaveException("La respuesta devuelve un nif no valido: " + nifClave, pIdSesion);
		}

		if (!ClaveLoginUtil.esCif(nif)) {
			// Extrae apellidos si vienen desglosados
			if (apellido1 != null && apellidos != null) {
				// Control extranjeros, solo 1 apellido
				if (apellidos.length() > apellido1.length()) {
					apellido2 = apellidos.substring(apellido1.length() + 1);
				}
			}
		} else {
			apellidos = null;
			apellido1 = null;
			apellido2 = null;
		}

		// Verificamos si son certificados de tipo 11 y 12 para extraer la
		// persona juridica
		DatosRepresentante representante = null;
		if (afirmaResponse != null) {
			final Map<String, String> infoCertificado = AFirmaUtil.extraerInfoCertificado(pIdSesion, afirmaResponse);
			final String clasificacion = infoCertificado.get("clasificacion");
			if ("11".equals(clasificacion) || "12".equals(clasificacion)) {
				// Datos representante
				representante = new DatosRepresentante();
				representante.setNif(nif);
				representante.setNombre(nombre);
				representante.setApellidos(apellidos);
				representante.setApellido1(apellido1);
				representante.setApellido2(apellido2);

				// Datos persona juridica
				nif = infoCertificado.get("NIF-CIF");
				nombre = infoCertificado.get("razonSocial");
				apellidos = null;
				apellido1 = null;
				apellido2 = null;
			}
		}

		// Almacenar en tabla y generar ticket sesion (OTP)
		log.debug(" Datos obtenidos clave [idSesion = " + pIdSesion + "]: Nivel=" + idp + ", Nif=" + nif + ", Nombre="
				+ nombre + " " + apellidos);

		final TicketClave respuesta = claveDao.generateTicketSesionLogin(pIdSesion, idp, nif, nombre, apellidos,
				apellido1, apellido2, representante);

		log.debug(" Ticket generado [idSesion = " + pIdSesion + "]: " + respuesta.getTicket());

		return respuesta;
	}

	@Override
	@NegocioInterceptor
	public TicketClave simularRespuestaClave(final String pIdSesion, final TypeIdp pIdp, final String pNif,
			final String pNombre, final String pApellidos, final String pApellido1, final String pApellido2) {

		return claveDao.generateTicketSesionLogin(pIdSesion, pIdp, pNif, pNombre, pApellidos, pApellido1, pApellido2,
				null);
	}

	@Override
	@NegocioInterceptor
	public TicketClave loginAnonimo(final String pIdSesion) {

		// Recuperamos datos sesion
		final DatosSesion datosSesion = claveDao.obtenerDatosSesionLogin(pIdSesion);
		if (datosSesion.getFechaTicket() != null) {
			throw new GenerarPeticionClaveException(
					"Sesion ya ha sido autenticada [idSesion = " + datosSesion.getIdSesion() + "]");
		}

		// Genera ticket para acceso sin autenticacion
		return claveDao.generateTicketSesionLogin(pIdSesion, TypeIdp.ANONIMO, null, null, null, null, null, null);
	}

	@Override
	@NegocioInterceptor
	public boolean isAccesoClaveDeshabilitado() {
		return config.isAccesoClaveDeshabilitado();
	}

	@Override
	@NegocioInterceptor
	public void purgar() {
		// Procesos de login
		claveDao.purgaTicketSesionLogin(config.getTimeoutProcesoAutenticacion(), config.getTimeoutTicketAutenticacion(),
				config.getTimeoutPurga());
		// Procesos de logout
		claveDao.purgaTicketSesionLogout(config.getTimeoutProcesoAutenticacion(),
				config.getTimeoutTicketAutenticacion(), config.getTimeoutPurga());

	}

	@Override
	@NegocioInterceptor
	public DatosUsuario obtenerDatosAutenticacion(final String pTicket) {

		final DatosUsuario t = claveDao.consumirTicketSesionLogin(pTicket);

		// No existe ticket
		if (t == null || t.getFechaTicket() == null) {
			throw new TicketNoValidoException("No existe ticket");
		}

		// Ticket caducado
		if (t.getFechaTicket() != null && (new Date()).getTime()
				- t.getFechaTicket().getTime() > (config.getTimeoutTicketAutenticacion() * ConstantesNumero.N1000)) {
			throw new TicketNoValidoException("Ticket caducado");
		}

		// Devuelve datos usuario
		return t;
	}

	@Override
	@NegocioInterceptor
	public String obtenerUrlRedireccionLoginClave(final String pIdSesion, final String pIdioma) {
		try {
			return config.getLoginRedireccionClave() + "?idioma=" + pIdioma + "&idSesion="
					+ URLEncoder.encode(pIdSesion, "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			throw new ErrorNoControladoException(e);
		}
	}

	@Override
	@NegocioInterceptor
	public String obtenerUrlRedireccionLogoutClave(final String pIdSesion) {
		try {
			return config.getLogoutRedireccionClave() + "?idSesion=" + URLEncoder.encode(pIdSesion, "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			throw new ErrorNoControladoException(e);
		}
	}

	@Override
	@NegocioInterceptor
	public boolean isAccesoClaveSimulado() {
		return config.isAccesoClaveSimulado();
	}

	@Override
	@NegocioInterceptor
	public PeticionClaveLogout generarPeticionLogoutClave(final String idSesion) {

		log.debug(" Generar peticion logout... ");

		// TODO DAO PARA SESION LOGOUT (DatosSesionLogout)
		final DatosLogoutSesion datosSesion = claveDao.obtenerDatosSesionLogout(idSesion);
		if (datosSesion.getFechaTicket() != null) {
			throw new GenerarPeticionClaveException("sesion ya ha sido autenticada en clave");
		}

		// Parametros peticion
		final STORKLogoutRequest req = new STORKLogoutRequest();
		req.setDestination(config.getPepsLogout());
		req.setSpProvidedId(config.getProviderName(datosSesion.getEntidad()));
		req.setIssuer(config.getLogoutCallbackClave() + "/" + idSesion + ".html");

		// Generamos peticion SAML
		final STORKSAMLEngine engine = STORKSAMLEngine.getInstance(datosSesion.getEntidad());
		if (engine == null) {
			throw new GenerarPeticionClaveException(
					"Error creando engine STORK. Revise localizacion fichero SignModule_<entidad>.xml (segun SamlEngine.xml)");
		}
		STORKLogoutRequest logoutReq = null;
		try {
			logoutReq = engine.generateSTORKLogoutRequest(req);
		} catch (final STORKSAMLEngineException e) {
			throw new GenerarPeticionClaveException(e);
		}

		// Pasamos a B64 y retornamos
		final byte[] token = logoutReq.getTokenSaml();
		final String samlRequestB64 = PEPSUtil.encodeSAMLToken(token);
		final String samlRequestXML = new String(token);
		log.debug(" Peticion generada: " + samlRequestXML);

		// TODO Probablemente sea necesario como extraer el saml id para el
		// logout
		// Extraemos saml id peticion y guardamos en sesion
		final String samlId = SamlUtil.extraerSamlIdLogout(samlRequestB64);
		if (StringUtils.isBlank(samlId)) {
			throw new GenerarPeticionClaveException("No se ha podido extraer SamlId de la peticion");
		}
		claveDao.establecerSamlIdSesionLogout(idSesion, samlId);

		// Devolvemos datos necesarios para invocar a Clave
		final PeticionClaveLogout peticionClave = new PeticionClaveLogout();
		peticionClave.setSamlRequestB64(samlRequestB64);
		peticionClave.setUrlClave(config.getPepsLogout());
		return peticionClave;

	}

	@Override
	@NegocioInterceptor
	public RespuestaClaveLogout procesarRespuestaLogoutClave(final String pIdSesion, final String pSamlResponseB64) {
		log.debug(" Procesando respuesta clave logout");
		final DatosLogoutSesion datosSesion = claveDao.obtenerDatosSesionLogout(pIdSesion);
		final String urlCallback = datosSesion.getUrlCallback();
		final String samlIdPeticion = datosSesion.getSamlIdPeticion();

		// Decodifica respuesta Clave
		final byte[] decSamlToken = PEPSUtil.decodeSAMLToken(pSamlResponseB64);
		final STORKSAMLEngine engine = STORKSAMLEngine.getInstance(datosSesion.getEntidad());
		if (engine == null) {
			throw new GenerarPeticionClaveException(
					"Error creando engine STORK. Revise localizacion fichero <entidad>_SignModule.xml (segun SamlEngine.xml)");
		}
		STORKLogoutResponse logoutReq = null;
		try {
			logoutReq = engine.validateSTORKLogoutResponse(decSamlToken, null);
		} catch (final STORKSAMLEngineException e) {
			throw new ErrorRespuestaClaveException(e, pIdSesion);
		}

		// Verificamos si hay error al interpretar la respuesta
		// TODO VER QUE PASA SI NO HAY NINGUNA SESION ACTIVA EN CLAVE
		RespuestaClaveLogout respuesta = null;
		if (logoutReq.isFail()) {
			respuesta = new RespuestaClaveLogout();
			respuesta.setLogout(false);
			respuesta.setUrlCallback(urlCallback);
		} else {
			// Verificamos que la peticion se corresponde a la sesion
			if (!StringUtils.equals(samlIdPeticion, logoutReq.getInResponseTo())) {
				throw new ErrorRespuestaClaveException("La respuesta no se corresponde a la peticion SAML origen",
						pIdSesion);
			}

			respuesta = new RespuestaClaveLogout();
			respuesta.setLogout(true);
			respuesta.setUrlCallback(urlCallback);

		}

		log.debug("Logout realizado: " + respuesta.isLogout());

		return respuesta;
	}

}
