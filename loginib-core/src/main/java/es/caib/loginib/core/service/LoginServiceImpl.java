package es.caib.loginib.core.service;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.fundaciobit.plugins.certificate.ICertificatePlugin;
import org.fundaciobit.plugins.certificate.ResultatValidacio;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.jose.jws.JWSInput;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;
import org.keycloak.representations.idm.authorization.AuthorizationResponse;
import org.opensaml.saml2.core.LogoutResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;

import es.caib.loginib.core.api.exception.ErrorNoControladoException;
import es.caib.loginib.core.api.exception.ErrorParametroException;
import es.caib.loginib.core.api.exception.GenerarPeticionClaveException;
import es.caib.loginib.core.api.exception.ValidateClaveException;
import es.caib.loginib.core.api.exception.ValidateClientCertException;
import es.caib.loginib.core.api.exception.ValidateUsuarioPasswordException;
import es.caib.loginib.core.api.model.login.AccesosPermitidos;
import es.caib.loginib.core.api.model.login.DatosAutenticacion;
import es.caib.loginib.core.api.model.login.DatosLogoutSesion;
import es.caib.loginib.core.api.model.login.DatosPersona;
import es.caib.loginib.core.api.model.login.DatosSesion;
import es.caib.loginib.core.api.model.login.DatosSesionData;
import es.caib.loginib.core.api.model.login.DesgloseApellidos;
import es.caib.loginib.core.api.model.login.EvidenciasAutenticacion;
import es.caib.loginib.core.api.model.login.EvidenciasLista;
import es.caib.loginib.core.api.model.login.PersonalizacionEntidad;
import es.caib.loginib.core.api.model.login.PeticionClave;
import es.caib.loginib.core.api.model.login.PeticionClaveLogout;
import es.caib.loginib.core.api.model.login.PropiedadAutenticacion;
import es.caib.loginib.core.api.model.login.RespuestaClaveLogout;
import es.caib.loginib.core.api.model.login.SesionLogin;
import es.caib.loginib.core.api.model.login.TicketClave;
import es.caib.loginib.core.api.model.login.TicketDesglose;
import es.caib.loginib.core.api.model.login.ValidacionUsuarioPassword;
import es.caib.loginib.core.api.model.login.types.TypeIdp;
import es.caib.loginib.core.api.model.login.types.TypePropiedad;
import es.caib.loginib.core.api.service.LoginService;
import es.caib.loginib.core.api.util.ClaveLoginUtil;
import es.caib.loginib.core.interceptor.NegocioInterceptor;
import es.caib.loginib.core.service.model.ConfiguracionAuditoria;
import es.caib.loginib.core.service.model.ConfiguracionKeycloak;
import es.caib.loginib.core.service.model.ConfiguracionProcesos;
import es.caib.loginib.core.service.repository.dao.DesgloseApellidosDao;
import es.caib.loginib.core.service.repository.dao.LoginDao;
import es.caib.loginib.core.service.util.AFirmaUtil;
import es.caib.loginib.core.service.util.ConvertUtil;
import es.caib.loginib.core.service.util.JsonUtil;
import eu.eidas.auth.commons.EidasStringUtil;
import eu.eidas.auth.commons.attribute.AttributeDefinition;
import eu.eidas.auth.commons.attribute.AttributeValue;
import eu.eidas.auth.commons.attribute.ImmutableAttributeMap;
import eu.eidas.auth.commons.attribute.PersonType;
import eu.eidas.auth.commons.attribute.impl.StringAttributeValueMarshaller;
import eu.eidas.auth.commons.protocol.IAuthenticationResponseNoMetadata;
import eu.eidas.auth.commons.protocol.IRequestMessageNoMetadata;
import eu.eidas.auth.commons.protocol.eidas.LevelOfAssurance;
import eu.eidas.auth.commons.protocol.eidas.LevelOfAssuranceComparison;
import eu.eidas.auth.commons.protocol.eidas.impl.EidasAuthenticationRequestNoMetadata;
import eu.eidas.auth.commons.protocol.impl.EidasSamlBinding;
import eu.eidas.auth.commons.protocol.impl.SamlNameIdFormat;
import eu.eidas.auth.engine.ProtocolEngineFactoryNoMetadata;
import eu.eidas.auth.engine.ProtocolEngineNoMetadataI;
import eu.eidas.auth.engine.configuration.SamlEngineConfigurationException;
import eu.eidas.auth.engine.configuration.dom.ProtocolEngineConfigurationFactoryNoMetadata;
import eu.eidas.auth.engine.xml.opensaml.SAMLEngineUtils;
import eu.eidas.auth.engine.xml.opensaml.SecureRandomXmlIdGenerator;
import eu.eidas.engine.exceptions.EIDASSAMLEngineException;

/**
 * Implementación LoginService.
 *
 * @author Indra
 */
@Service("loginService")
@Transactional
public final class LoginServiceImpl implements LoginService {

	/** Log. */
	private final org.slf4j.Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

	/** Configuracion. */
	@Autowired
	private ModuleConfig config;

	/** Dao. */
	@Autowired
	private LoginDao loginDao;

	/** Dao. */
	@Autowired
	DesgloseApellidosDao desgloseDao;

	@Override
	@NegocioInterceptor
	public String iniciarSesionLogin(final String entidad, final String pUrlCallback, final String pUrlCallbackError,
			final String idioma, final List<TypeIdp> idps, final Integer qaa, final boolean iniClaAuto,
			final boolean forceAuth, final String aplicacion, final boolean auditar,
			final Map<String, String> paramsApp) {
		log.debug(" Crea sesion clave: [idps = " + idps + "] [urlCallback = " + pUrlCallback + "]");

		if (StringUtils.isBlank(entidad)) {
			throw new ErrorParametroException("No se ha especificado parametro entidad");
		}
		if (StringUtils.isBlank(pUrlCallback)) {
			throw new ErrorParametroException("No se ha especificado parametro url callback");
		}
		if (StringUtils.isBlank(pUrlCallbackError)) {
			throw new ErrorParametroException("No se ha especificado parametro url callback error");
		}
		if (StringUtils.isBlank(idioma)) {
			throw new ErrorParametroException("No se ha especificado parametro idioma");
		}
		if (idps == null || idps.isEmpty()) {
			throw new ErrorParametroException("No se han especificado idps");
		}
		boolean idpAutenticado = false;
		for (final TypeIdp i : idps) {
			if (i != TypeIdp.ANONIMO) {
				idpAutenticado = true;
				break;
			}
		}
		if (idpAutenticado && (qaa == null || (qaa <= 0 || qaa > 3))) {
			throw new ErrorParametroException("No se ha especificado un qaa valido");
		}

		final String idSesion = loginDao.crearSesionLogin(entidad, pUrlCallback, pUrlCallbackError, idioma, idps, qaa,
				iniClaAuto, forceAuth, aplicacion, auditar, paramsApp, false);
		log.debug(" Creada sesion clave:  [idSesion = " + idSesion + "] [idps = " + idps + "] [urlCallback = "
				+ pUrlCallback + "]");
		return idSesion;
	}

	@Override
	@NegocioInterceptor
	public String iniciarSesionLogout(final String entidad, final String pUrlCallback, final String idioma,
			final String aplicacion) {
		log.debug(" Crea logout sesion clave...");
		final String idSesion = loginDao.crearSesionLogut(entidad, pUrlCallback, idioma, aplicacion);
		log.debug(" Creada logout sesion clave: " + idSesion);
		return idSesion;
	}

	@Override
	@NegocioInterceptor
	public DatosSesion obtenerDatosSesionLogin(final String idSesion) {
		// Obtener datos sesion
		return recuperarDatosSesionLogin(idSesion);
	}

	@Override
	@NegocioInterceptor
	public PeticionClave generarPeticionLoginClave(final String idSesion) {

		log.debug(" Generar peticion clave: [idSesion = " + idSesion + "]");

		// Obtener datos sesion
		final DatosSesion datosSesion = recuperarDatosSesionLogin(idSesion);

		if (!datosSesion.getAccesosPermitidos().isAccesoClave()) {
			throw new GenerarPeticionClaveException("No está habilitado acceso a Cl@ve");
		}

		// Generamos peticion clave
		PeticionClave res = null;
		if (datosSesion.getAccesosPermitidos().isAccesoClaveSimulado()) {
			res = new PeticionClave();
			res.setSimulado(true);
		} else {
			res = generarPeticionClaveReal(datosSesion);
		}

		if (datosSesion.getSesion().isTipoTest()) {
			loginDao.updateSesionSamlResponse(idSesion, res.getSamlRequestB64());
		}
		return res;
	}

	@Override
	@NegocioInterceptor
	public TicketClave procesarRespuestaLoginClave(final String pIdSesion, final String pSamlResponseB64,
			final String relayStateRequest, final Map<String, String> headersRequest, final String ipAddressFrom) {

		log.debug(" Procesando respuesta clave [idSesion = " + pIdSesion + "]");

		// Recuperamos datos sesion
		final DatosSesion datosSesion = recuperarDatosSesionLogin(pIdSesion);
		if (!datosSesion.getAccesosPermitidos().isAccesoClave()) {
			throw new ValidateClaveException("No se permite acceso por Clave", pIdSesion);
		}
		if (datosSesion.getSesion().getFechaTicket() != null) {
			throw new GenerarPeticionClaveException(
					"Sesion ya ha sido autenticada [idSesion = " + datosSesion.getSesion().getIdSesion() + "]");
		}

		// Decodifica respuesta Clave
		final ProtocolEngineNoMetadataI engine = getEngineSamlFactory(datosSesion.getSesion().getEntidad());

		// Primero se extraen los bytes de la respuesta recibida
		final byte[] decSamlTicket = EidasStringUtil.decodeBytesFromBase64(pSamlResponseB64);
		String samlXml = null;
		try {
			samlXml = new String(decSamlTicket, "UTF-8");
		} catch (final UnsupportedEncodingException ueo) {
			throw new ValidateClaveException("Error pasando respuesta a UTF-8", ueo, pIdSesion);
		}

		// Se lee y se valida
		IAuthenticationResponseNoMetadata authnResponse;

		// En caso de error durante la validación se produce una excepción
		try {
			authnResponse = engine.unmarshallResponseAndValidate(decSamlTicket, new URI(config.getPepsUrl()).getHost(),
					config.getClaveResponseValidationNotBefore(), config.getClaveResponseValidationNotBefore(),
					config.getLoginCallbackClave() + "/" + datosSesion.getSesion().getIdSesion() + ".html");
		} catch (EIDASSAMLEngineException | URISyntaxException e) {
			throw new ValidateClaveException(e.getMessage(), e, pIdSesion);
		}

		// Con el objeto obtenido se puede comprobar si es una autenticción
		// exitosa y final extraer los datos recibidos.
		if (authnResponse.isFailure()) {
			log.error("La respuesta indica que hay un error [idSesion = " + pIdSesion + "]: "
					+ authnResponse.getStatusMessage());
			throw new ValidateClaveException("Saml Response is fail: " + authnResponse.getStatusMessage(), pIdSesion);
		}

		// Extrae datos autenticación
		final DatosAutenticacion datosAutenticacion = extractDatosAutenticacionClave(datosSesion, relayStateRequest,
				authnResponse);

		// Generar evidencias autenticación
		EvidenciasAutenticacion evidencias = null;
		if (datosSesion.getSesion().isAuditar()) {
			final List<PropiedadAutenticacion> evidenciasMetodo = new ArrayList<>();
			evidenciasMetodo.add(new PropiedadAutenticacion("SAML", TypePropiedad.XML_B64, pSamlResponseB64, true));
			evidencias = generarEvidenciasAutenticacion(datosAutenticacion, headersRequest, ipAddressFrom,
					evidenciasMetodo);
		}

		// Almacenar en tabla y generar ticket sesion (OTP)
		log.debug(" Datos obtenidos clave [idSesion = " + pIdSesion + "]: " + datosAutenticacion.print());
		final TicketClave respuesta = loginDao.generateTicketSesionLogin(pIdSesion, datosAutenticacion, evidencias,
				config.isOmitirDesglose());
		respuesta.setPersonalizacion(datosSesion.getPersonalizacionEntidad());
		log.debug(" Ticket generado [idSesion = " + pIdSesion + "]: " + respuesta.getTicket());

		return respuesta;
	}

	@Override
	@NegocioInterceptor
	public TicketClave simularRespuestaClave(final String pIdSesion, final TypeIdp pIdp, final DatosPersona persona,
			final Map<String, String> headersRequest, final String ipAddressFrom) {

		final DatosSesion datosSesion = this.obtenerDatosSesionLogin(pIdSesion);

		if (!datosSesion.getAccesosPermitidos().isAccesoClave()
				|| !datosSesion.getAccesosPermitidos().isAccesoClaveSimulado()) {
			throw new ValidateClaveException("No se permite acceso mediante Clave simulado", pIdSesion);
		}
		//Normalizamos NIF persona
		persona.setNif(normalizarNif(persona.getNif()));

		//Normalizamos textos de nombre y apellidos
		persona.setNombre(normalizarTexto(persona.getNombre()));
		persona.setApellidos(normalizarTexto(persona.getApellidos()));
		persona.setApellido1(normalizarTexto(persona.getApellido1()));
		persona.setApellido2(normalizarTexto(persona.getApellido2()));

		final DatosAutenticacion datosAutenticacion = new DatosAutenticacion(pIdSesion, new Date(), 2, pIdp, persona,
				null, datosSesion.getSesion().getParamsApp());

		// Generar evidencias autenticación
		EvidenciasAutenticacion evidencias = null;
		if (datosSesion.getSesion().isAuditar()) {
			final List<PropiedadAutenticacion> evidenciasMetodo = new ArrayList<>();
			String samlSimulado;
			try {
				samlSimulado = ConvertUtil.cadenaToBase64("<SAML SIMULADO/>");
			} catch (final Exception e) {
				throw new ValidateClaveException("Error al generar SAML simulado: " + e.getMessage(), e, pIdSesion);
			}
			evidenciasMetodo.add(new PropiedadAutenticacion("SAML", TypePropiedad.XML_B64, samlSimulado, true));
			evidencias = generarEvidenciasAutenticacion(datosAutenticacion, headersRequest, ipAddressFrom,
					evidenciasMetodo);
		}

		TicketClave ticket = loginDao.generateTicketSesionLogin(pIdSesion, datosAutenticacion, evidencias,
				config.isOmitirDesglose());
		if (ticket.isTest()) {
			ticket.setPersonalizacion(datosSesion.getPersonalizacionEntidad());
		}
		return ticket;
	}

	@Override
	@NegocioInterceptor
	public TicketClave loginAnonimo(final String pIdSesion, final Map<String, String> headers,
			final String ipAddressFrom) {
		// Recuperamos datos sesion
		final DatosSesion datosSesion = recuperarDatosSesionLogin(pIdSesion);
		if (!datosSesion.getAccesosPermitidos().isAccesoAnonimo()) {
			throw new ErrorNoControladoException("No se permite acceso mediante Clave simulado");
		}
		if (datosSesion.getSesion().getFechaTicket() != null) {
			throw new GenerarPeticionClaveException(
					"Sesion ya ha sido autenticada [idSesion = " + datosSesion.getSesion().getIdSesion() + "]");
		}
		// Genera ticket para acceso sin autenticacion
		final DatosAutenticacion datosAutenticacion = new DatosAutenticacion(pIdSesion, new Date(), null,
				TypeIdp.ANONIMO, null, null, datosSesion.getSesion().getParamsApp());
		// Generar evidencias autenticación
		EvidenciasAutenticacion evidencias = null;
		if (datosSesion.getSesion().isAuditar()) {
			final List<PropiedadAutenticacion> evidenciasMetodo = new ArrayList<>();
			// TODO VER SI SE METE ALGO MAS
			evidencias = generarEvidenciasAutenticacion(datosAutenticacion, headers, ipAddressFrom, evidenciasMetodo);
		}
		return loginDao.generateTicketSesionLogin(pIdSesion, datosAutenticacion, evidencias, config.isOmitirDesglose());
	}

	@Override
	@NegocioInterceptor
	public void purgar() {

		final ConfiguracionProcesos conf = config.getConfiguracionProcesos();

		// Procesos de login
		loginDao.purgaTicketSesionLogin(conf);
		// Procesos de logout
		loginDao.purgaTicketSesionLogout(conf);
	}

	@Override
	@NegocioInterceptor
	public DatosAutenticacion obtenerDatosAutenticacion(final String pTicket) {
		final ConfiguracionProcesos configuracionProcesos = config.getConfiguracionProcesos();
		final DatosAutenticacion t = loginDao.consumirTicketSesionLoginAll(pTicket,
				configuracionProcesos.getTimeoutTicketAutenticacion());
		return t;
	}

	@Override
	@NegocioInterceptor
	public DatosAutenticacion obtenerDatosAutenticacionAll(final String pTicket) {
		final ConfiguracionProcesos configuracionProcesos = config.getConfiguracionProcesos();
		final DatosAutenticacion t = loginDao.consumirTicketSesionLogin(pTicket,
				configuracionProcesos.getTimeoutTicketAutenticacion());
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
	public PeticionClaveLogout generarPeticionLogoutClave(final String idSesion) {

		log.debug(" Generar peticion logout... ");

		// Recupera datos logout
		final DatosLogoutSesion datosSesion = loginDao.obtenerDatosSesionLogout(idSesion);

		if (config.isAccesoClaveDeshabilitado()) {
			throw new GenerarPeticionClaveException("No está habilitado acceso a Cl@ve");
		}

		if (config.isAccesoClaveSimulado()) {
			throw new RuntimeException("No está implementado logout para acceso simulado");
		}

		if (datosSesion.getFechaTicket() != null) {
			throw new GenerarPeticionClaveException("sesion ya ha sido autenticada en clave");
		}

		// Generamos peticion SAML
		// final String relayState =
		// SecureRandomXmlIdGenerator.INSTANCE.generateIdentifier(8);
		final String idSaml = SAMLEngineUtils.generateNCName();

		final ProtocolEngineNoMetadataI engine = getEngineSamlFactory(datosSesion.getEntidad());
		byte[] token = null;
		try {
			token = engine.generateLogoutRequestMessage(config.getLogoutCallbackClave() + "/" + idSesion + ".html",
					config.getProviderName(datosSesion.getEntidad()), config.getPepsLogout(), idSaml);
		} catch (final EIDASSAMLEngineException e) {
			throw new GenerarPeticionClaveException(e);
		}

		// Pasamos a B64 y retornamos
		final String samlRequestB64 = EidasStringUtil.encodeToBase64(token);
		final String samlRequestXML = new String(token);
		log.debug(" Peticion generada: " + samlRequestXML);

		// Guardamos en sesion id saml
		loginDao.establecerIdPeticionLogout(idSesion, idSaml);

		// Devolvemos datos necesarios para invocar a Clave
		final PeticionClaveLogout peticionClave = new PeticionClaveLogout();
		peticionClave.setSamlRequestB64(samlRequestB64);
		peticionClave.setUrlClave(config.getPepsLogout());
		return peticionClave;

	}

	@Override
	@NegocioInterceptor
	public RespuestaClaveLogout procesarRespuestaLogoutClave(final String pIdSesion, final String pSamlResponseB64) {

		boolean error = false;

		log.debug(" Procesando respuesta clave logout");
		final DatosLogoutSesion datosSesion = loginDao.obtenerDatosSesionLogout(pIdSesion);
		final String urlCallback = datosSesion.getUrlCallback();
		final String samlIdPeticion = datosSesion.getSamlIdPeticion();

		// Primero se extraen los bytes de la respuesta recibida
		final byte[] decSamlTicket = EidasStringUtil.decodeBytesFromBase64(pSamlResponseB64);

		// Decodifica respuesta Clave
		final ProtocolEngineNoMetadataI engine = getEngineSamlFactory(datosSesion.getEntidad());
		LogoutResponse logoutReq = null;
		try {
			logoutReq = engine.unmarshallLogoutResponseAndValidate(decSamlTicket,
					new URI(config.getPepsLogout()).getHost(), config.getClaveResponseValidationNotBefore(),
					config.getClaveResponseValidationNotBefore(),
					config.getLogoutCallbackClave() + "/" + pIdSesion + ".html");
		} catch (EIDASSAMLEngineException | URISyntaxException e) {
			error = true;
			log.error("Error procesando respuesta logout: " + e.getMessage(), e);
			// throw new ErrorRespuestaClaveException(e);
		}

		// Verificamos que la final peticion se corresponde final a la
		// sesion
		if (!error && !StringUtils.equals(samlIdPeticion, logoutReq.getInResponseTo())) {
			error = true;
			log.error("Error procesando respuesta logout: la respuesta no se corresponde a la peticion SAML origen");
			// throw new ErrorRespuestaClaveException("La respuesta no se corresponde a la
			// peticion SAML origen");
		}

		// Devolvemos respuesta
		log.debug("Logout resultado: " + !error);
		final RespuestaClaveLogout respuesta = new RespuestaClaveLogout();
		respuesta.setLogout(!error);
		respuesta.setUrlCallback(urlCallback);
		return respuesta;
	}

	@Override
	@NegocioInterceptor
	public TicketClave loginClientCert(final String pIdSesion, final Map<String, String> headers,
			final X509Certificate certificadoRequest, final String ipAddressFrom) {

		// Recuperamos datos sesion
		final DatosSesion datosSesion = recuperarDatosSesionLogin(pIdSesion);
		if (!datosSesion.getAccesosPermitidos().isAccesoClientCert()) {
			throw new ValidateClientCertException("No se permite acceso por ClientCert", pIdSesion);
		}
		if (datosSesion.getSesion().getFechaTicket() != null) {
			throw new GenerarPeticionClaveException(
					"Sesion ya ha sido autenticada [idSesion = " + datosSesion.getSesion().getIdSesion() + "]");
		}

		// Obtiene certificado segun metodo
		X509Certificate certificado = null;
		switch (config.getClientCertMetodo()) {
		case HEADER:
			certificado = recuperarCertificadoHeader(pIdSesion, headers, ipAddressFrom);
			break;
		case AJP:
			certificado = certificadoRequest;
			break;
		}

		// Verificamos si existe certificado
		if (certificado == null) {
			throw new ValidateClientCertException("No se encuentra certificado en la petición", pIdSesion);
		}

		final DatosAutenticacion datosAutenticacion = extractDatosAutenticacionCertificado(pIdSesion, certificado);

		// Generar evidencias autenticación
		EvidenciasAutenticacion evidencias = null;
		if (datosSesion.getSesion().isAuditar()) {
			final List<PropiedadAutenticacion> evidenciasMetodo = new ArrayList<>();
			// TODO VER SI SE METE ALGO MAS
			evidencias = generarEvidenciasAutenticacion(datosAutenticacion, headers, ipAddressFrom, evidenciasMetodo);
		}

		// Almacenar en tabla y generar ticket sesion (OTP)
		log.debug(" Datos obtenidos [idSesion = " + pIdSesion + "]: " + datosAutenticacion.print());

		final TicketClave respuesta = loginDao.generateTicketSesionLogin(pIdSesion, datosAutenticacion, evidencias,
				config.isOmitirDesglose());

		log.debug(" Ticket generado [idSesion = " + pIdSesion + "]: " + respuesta.getTicket());

		return respuesta;
	}

	@Override
	@NegocioInterceptor
	public ValidacionUsuarioPassword loginUsuarioPassword(final String idSesion, final String usuario,
			final String password, final Map<String, String> headers, final String ipAddressFrom) {

		// Recupera datos sesión
		final DatosSesion datosSesion = recuperarDatosSesionLogin(idSesion);
		if (!datosSesion.getAccesosPermitidos().isAccesoUsuarioPassword()) {
			throw new ValidateUsuarioPasswordException("No se puede permite validación por usuario/password", idSesion);
		}
		if (datosSesion.getSesion().getFechaTicket() != null) {
			throw new GenerarPeticionClaveException(
					"Sesion ya ha sido autenticada [idSesion = " + datosSesion.getSesion().getIdSesion() + "]");
		}

		// Obtenemos configuracion keycloak
		final ConfiguracionKeycloak keycloakConfig = obtenerConfiguracionKeycloak(idSesion);

		// Creamos cliente (si no puede conectar con keycloak dará error)
		AuthzClient authzClient = null;
		try (final ByteArrayInputStream bis = new ByteArrayInputStream(
				keycloakConfig.getConfiguracion().getBytes(StandardCharsets.UTF_8))) {
			authzClient = AuthzClient.create(bis);
		} catch (final Exception ex) {
			throw new ValidateUsuarioPasswordException("No se puede conectar a servidor autenticación", ex, idSesion);
		}

		// Verificamos usuario/password
		AuthorizationResponse response = null;
		boolean validacion;
		try {
			final AuthorizationRequest request = new AuthorizationRequest();
			response = authzClient.authorization(usuario, password).authorize(request);
			validacion = true;
		} catch (final Exception ex) {
			// Si falla aqui, suponemos que es porque esta mal el usuario/pass. Dejamos
			// continuar para que intente de nuevo.
			log.debug("Error al validar usuario " + usuario + ": " + ex.getMessage(), ex);
			validacion = false;
		}

		// Devolvemos respuesta
		final ValidacionUsuarioPassword res = new ValidacionUsuarioPassword();
		if (!validacion) {
			// Usuario no valido: intentar de nuevo
			res.setUsuarioValido(false);
		} else {
			// Obtenemos datos usuario y retornamos
			AccessToken accessToken = null;
			try {
				accessToken = new JWSInput(response.getToken()).readJsonContent(AccessToken.class);
			} catch (final Exception ex) {
				throw new ValidateUsuarioPasswordException(
						"Error al obtener datos usuario del servidor de autenticación", ex, idSesion);
			}

			// Extraer datos usuario.
			final DatosAutenticacion datosAutenticacion = extractDatosAutenticacionKeycloak(idSesion, accessToken,
					keycloakConfig);

			// Generar evidencias autenticación
			EvidenciasAutenticacion evidencias = null;
			if (datosSesion.getSesion().isAuditar()) {
				final List<PropiedadAutenticacion> evidenciasMetodo = new ArrayList<>();
				evidenciasMetodo.add(new PropiedadAutenticacion("USUARIO", TypePropiedad.TEXTO, usuario, true));
				evidencias = generarEvidenciasAutenticacion(datosAutenticacion, headers, ipAddressFrom,
						evidenciasMetodo);
			}

			// Almacenar en tabla y generar ticket sesion (OTP)
			log.debug(" Datos obtenidos clave [idSesion = " + idSesion + "]: " + datosAutenticacion.print());
			final TicketClave ticket = loginDao.generateTicketSesionLogin(idSesion, datosAutenticacion, evidencias,
					config.isOmitirDesglose());
			log.debug(" Ticket generado [idSesion = " + idSesion + "]: " + ticket.getTicket());

			res.setUsuarioValido(true);
			res.setTicket(ticket);
		}

		return res;

	}

	@Override
	@NegocioInterceptor
	public EvidenciasAutenticacion obtenerEvidenciasSesionLogin(final String idSesion) {
		return loginDao.obtenerEvidenciasSesionLogin(idSesion);
	}

	@Override
	@NegocioInterceptor
	public Map<String, String> obtenerMapeoErroresValidacion(final String key) {
		final Map<String, String> errores = new HashMap<>();
		final String prefix = key + ".error.";
		final Map<String, String> props = config.getPropiedadesByPrefix(prefix);
		for (final String propKey : props.keySet()) {
			errores.put(propKey.substring(prefix.length()), props.get(propKey));
		}
		return errores;
	}

	// ---------------------------------------------------------------------------
	// FUNCIONES AUXILIARES
	// ---------------------------------------------------------------------------

	/**
	 * Obtiene personalizacion entidad.
	 *
	 * @param entidad entidad
	 * @param idioma  idioma
	 * @return personalizacion
	 */
	private PersonalizacionEntidad getPersonalizacionEntidad(final String entidad, final String idioma) {
		final PersonalizacionEntidad res = new PersonalizacionEntidad();
		res.setCss(config.getPropiedadPersonalizacion(entidad, "css"));
		res.setFavicon(config.getPropiedadPersonalizacion(entidad, "favicon"));
		res.setLogourl(config.getPropiedadPersonalizacion(entidad, "logo.url"));
		res.setLogoalt(config.getPropiedadPersonalizacion(entidad, "logo.url", idioma));
		res.setTitle(config.getPropiedadPersonalizacion(entidad, "title", idioma));
		res.setTitulo(config.getPropiedadPersonalizacion(entidad, "titulo", idioma));
		return res;
	}

	/**
	 * Obtiene engine para saml de Clave.
	 *
	 * @param entidad entidad
	 * @return engine
	 */
	private ProtocolEngineNoMetadataI getEngineSamlFactory(final String entidad) {
		// TODO CLAVE2 En clave2 se cachea
		try {
			final ProtocolEngineConfigurationFactoryNoMetadata protocolEngineConfigurationFactory = new ProtocolEngineConfigurationFactoryNoMetadata(
					entidad + "_SamlEngine.xml", null, config.getDirectorioConfiguracion());
			final ProtocolEngineFactoryNoMetadata factory = new ProtocolEngineFactoryNoMetadata(
					protocolEngineConfigurationFactory);
			final ProtocolEngineNoMetadataI protocolEngine = factory.getProtocolEngine("SPNoMetadata");
			if (protocolEngine == null) {
				throw new GenerarPeticionClaveException("Error generando engine, retorna nulo");
			}
			return protocolEngine;
		} catch (final SamlEngineConfigurationException e) {
			throw new GenerarPeticionClaveException(e);
		}
	}

	/**
	 * Genera petición clave.
	 *
	 * @param datosSesion datos sesión
	 * @return petición clave
	 */
	private PeticionClave generarPeticionClaveReal(final DatosSesion datosSesion) {
		if (datosSesion.getSesion().getFechaTicket() != null) {
			throw new GenerarPeticionClaveException(
					"Sesion ya ha sido autenticada [idSesion = " + datosSesion.getSesion().getIdSesion() + "]");
		}

		// Atributos personales que se desean conocer
		final ImmutableAttributeMap.Builder reqAttrMapBuilder = new ImmutableAttributeMap.Builder();

		if (!datosSesion.getSesion().getIdps().contains(TypeIdp.CERTIFICADO)) {
			reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://es.minhafp.clave/AFirmaIdP")
					.friendlyName("AFirmaIdP").personType(PersonType.NATURAL_PERSON).required(false)
					.uniqueIdentifier(true).xmlType("http://www.w3.org/2001/XMLSchema", "AFirmaIdPType", "cl")
					.attributeValueMarshaller(new StringAttributeValueMarshaller()).build());
		}
		if (!datosSesion.getSesion().getIdps().contains(TypeIdp.CLAVE_PERMANENTE)) {
			reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://es.minhafp.clave/GISSIdP")
					.friendlyName("GISSIdP").personType(PersonType.NATURAL_PERSON).required(false)
					.uniqueIdentifier(true).xmlType("http://www.w3.org/2001/XMLSchema", "GISSIdPType", "cl")
					.attributeValueMarshaller(new StringAttributeValueMarshaller()).build());
		}
		if (!datosSesion.getSesion().getIdps().contains(TypeIdp.CLAVE_PIN)) {
			reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://es.minhafp.clave/AEATIdP")
					.friendlyName("AEATIdP").personType(PersonType.NATURAL_PERSON).required(false)
					.uniqueIdentifier(true).xmlType("http://www.w3.org/2001/XMLSchema", "AEATIdPType", "cl")
					.attributeValueMarshaller(new StringAttributeValueMarshaller()).build());
		}

		// De momento EIDAS siempre deshabilitado
		reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://es.minhafp.clave/EIDASIdP")
				.friendlyName("EIDASIdP").personType(PersonType.NATURAL_PERSON).required(false).uniqueIdentifier(true)
				.xmlType("http://www.w3.org/2001/XMLSchema", "EIDASIdP", "cl")
				.attributeValueMarshaller(new StringAttributeValueMarshaller()).build());

		// De momento CL@VE MOVIL siempre deshabilitado
		reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://es.minhafp.clave/CLVMOVILIdP")
				.friendlyName("CLVMOVILIdP").personType(PersonType.NATURAL_PERSON).required(false).uniqueIdentifier(true)
				.xmlType("http://www.w3.org/2001/XMLSchema", "CLVMOVILIdP", "cl")
				.attributeValueMarshaller(new StringAttributeValueMarshaller()).build());

		final String relayState = SecureRandomXmlIdGenerator.INSTANCE.generateIdentifier(8);

		reqAttrMapBuilder.putPrimaryValues(
				new AttributeDefinition.Builder<String>().nameUri("http://es.minhafp.clave/RelayState")
						.friendlyName("RelayState").personType(PersonType.NATURAL_PERSON).required(false)
						.uniqueIdentifier(true)
						.xmlType("http://eidas.europa.eu/attributes/naturalperson", "PersonIdentifierType",
								"eidas-natural")
						.attributeValueMarshaller(new StringAttributeValueMarshaller()).build(),
				relayState);

		// Parametros peticion
		final EidasAuthenticationRequestNoMetadata.Builder reqBuilder = new EidasAuthenticationRequestNoMetadata.Builder();
		reqBuilder.id(SAMLEngineUtils.generateNCName());
		reqBuilder.destination(config.getPepsUrl());
		String providerName = config.getProviderName(datosSesion.getSesion().getEntidad());
		if (StringUtils.isNotBlank(datosSesion.getSesion().getAplicacion())) {
			providerName += ";" + datosSesion.getSesion().getAplicacion();
		}
		reqBuilder.providerName(providerName);
		reqBuilder.requestedAttributes(reqAttrMapBuilder.build());

		final LevelOfAssurance level = ClaveLoginUtil.convertQaaToLevelOfAssurance(datosSesion.getSesion().getQaa());

		reqBuilder.levelOfAssurance(level.stringValue());
		reqBuilder.levelOfAssuranceComparison(LevelOfAssuranceComparison.fromString("minimum").stringValue());
		reqBuilder.nameIdFormat(SamlNameIdFormat.UNSPECIFIED.getNameIdFormat());
		reqBuilder.binding(EidasSamlBinding.POST.getName());
		reqBuilder.assertionConsumerServiceURL(
				config.getLoginCallbackClave() + "/" + datosSesion.getSesion().getIdSesion() + ".html");
		reqBuilder.forceAuth(datosSesion.getSesion().isForceAuth());
		reqBuilder.spApplication(datosSesion.getSesion().getAplicacion());

		// Generamos peticion SAML. Se firma la petición indicando los datos del
		// destino
		final ProtocolEngineNoMetadataI engine = getEngineSamlFactory(datosSesion.getSesion().getEntidad());

		final EidasAuthenticationRequestNoMetadata buildRequest = reqBuilder.build();
		IRequestMessageNoMetadata message = null;
		try {
			message = engine.generateRequestMessage(buildRequest, true);
		} catch (final EIDASSAMLEngineException e) {
			throw new GenerarPeticionClaveException(e);
		}
		final byte[] samlRequestXML = message.getMessageBytes();

		// Pasamos a B64 y retornamos
		final String samlRequestB64 = EidasStringUtil.encodeToBase64(samlRequestXML);
		try {
			log.debug(" Peticion generada [idSesion = " + datosSesion.getSesion().getIdSesion() + "]: "
					+ new String(samlRequestXML, "UTF-8"));
		} catch (final UnsupportedEncodingException e) {
			// No hacemos nada;
		}

		// Guardamos en sesion el id saml
		loginDao.establecerIdPeticionLogin(datosSesion.getSesion().getIdSesion(), relayState);

		// Devolvemos datos necesarios para invocar a Clave
		final PeticionClave peticionClave = new PeticionClave();
		peticionClave.setSamlRequestB64(samlRequestB64);
		peticionClave.setUrlClave(config.getPepsUrl());
		peticionClave.setIdioma(datosSesion.getSesion().getIdioma());
		peticionClave.setRelayState(relayState);
		return peticionClave;
	}

	/**
	 * Calcula accesos permitidos en la sesión.
	 *
	 * @param idps idps
	 * @param qaa  qaa
	 * @return accesos permitidos en la sesión.
	 */
	private AccesosPermitidos calcularAccesosPermitidos(final List<TypeIdp> idps, final Integer qaa) {

		final AccesosPermitidos res = new AccesosPermitidos();

		// Acceso anonimo
		// - Si acceso anonimo auto está habilitado y solo se establece anonimo, se
		// marca inicio automatico
		res.setAccesoAnonimo(ClaveLoginUtil.permiteAccesoAnonimo(idps));
		if (config.isAnonimoInicioAuto() && ClaveLoginUtil.permiteSoloAnonimo(idps)) {
			res.setAccesoAnonimoAuto(true);
		}

		// Acceso clave
		res.setAccesoClave(ClaveLoginUtil.permiteAccesoClave(idps));
		res.setAccesoClaveSimulado(config.isAccesoClaveSimulado());

		// Acceso ClientCert
		// - Solo si nivel qaa <= 2
		if (ClaveLoginUtil.permiteAccesoClientCert(idps) && !config.isAccesoClientCertDeshabilitado()
				&& qaa.intValue() <= 2) {
			res.setAccesoClientCert(true);
			res.setAccesoClientCertLink("link".equals(config.getClientCertVisualizacion()));
		}

		// Acceso usuario/password
		// - Solo si nivel qaa <= 1
		if (ClaveLoginUtil.permiteAccesoUsuarioPassword(idps) && !config.isAccesoUsuarioPasswordDeshabilitado()
				&& qaa.intValue() <= 1) {
			res.setAccesoUsuarioPassword(true);
		}

		return res;
	}

	/**
	 * Recupera datos sesión login.
	 *
	 * @param idSesion id sesión
	 * @return Datos sesión
	 */
	private DatosSesion recuperarDatosSesionLogin(final String idSesion) {
		final DatosSesionData sesionData = loginDao.obtenerDatosSesionLogin(idSesion);
		final DatosSesion datosSesion = new DatosSesion();
		datosSesion.setSesion(sesionData);
		datosSesion
				.setPersonalizacionEntidad(getPersonalizacionEntidad(sesionData.getEntidad(), sesionData.getIdioma()));
		datosSesion.setAccesosPermitidos(calcularAccesosPermitidos(sesionData.getIdps(), sesionData.getQaa()));
		return datosSesion;
	}

	@Override
	public PersonalizacionEntidad obtenerDatosPersonalizacionEntidad(String idSesion) {
		final DatosSesionData sesionData = loginDao.obtenerDatosSesionLogin(idSesion, false);
		return getPersonalizacionEntidad(sesionData.getEntidad(), sesionData.getIdioma());
	}

	/**
	 * Obtiene ip.
	 *
	 * @param headers Headers
	 * @return ip
	 */
	private String getClientIpAddress(final List<String> ipHeaders, final Map<String, String> headers,
			final String ipAddressFrom) {
		String res = null;
		for (final String header : ipHeaders) {
			final String ipHeader = headers.get(header);
			if (ipHeader != null && ipHeader.length() != 0 && !"unknown".equalsIgnoreCase(ipHeader)) {
				log.debug(" Recupera ip de header " + header + ": " + res);
				res = ipHeader;
				break;
			}
		}
		if (StringUtils.isEmpty(res)) {
			res = ipAddressFrom;
			log.debug(" No se encuentra ip en headers, se coge de request.getRemoteAddr():" + res);
		}
		return res;
	}

	/**
	 * Recupera certificado de headers.
	 *
	 * @param idSesion idSesion
	 * @param headers
	 * @param ipFrom
	 * @return
	 */
	private X509Certificate recuperarCertificadoHeader(final String idSesion, final Map<String, String> headers,
			final String ipFrom) {

		log.debug(" Recupera certificado header");

		// Comprobamos IP origen
		if (StringUtils.isNotBlank(config.getClientCertHeaderIpFrom())
				&& !StringUtils.equals(config.getClientCertHeaderIpFrom(), ipFrom)) {
			throw new ValidateClientCertException(
					"Ip origen " + ipFrom + " no concuerda con permitida " + config.getClientCertHeaderIpFrom(),
					idSesion);
		}

		// Obtiene certificado a partir header
		X509Certificate cer = null;
		String headerCert = headers.get(config.getClientCertHeaderName());
		try {
			if (StringUtils.isNotBlank(headerCert)) {
				// El cert viene sin saltos de líneas, no es formato PEM valido
				// Borramos cabecera y pie y pasamos de B64
				headerCert = StringUtils.remove(headerCert, "-----BEGIN CERTIFICATE----- ");
				headerCert = StringUtils.remove(headerCert, " -----END CERTIFICATE-----");
				headerCert = StringUtils.remove(headerCert, " ");
				final byte[] certBytes = Base64.getDecoder().decode(headerCert);

				final ByteArrayInputStream bis = new ByteArrayInputStream(certBytes);

				final CertificateFactory fact = CertificateFactory.getInstance("X.509");
				cer = (X509Certificate) fact.generateCertificate(bis);
			}
		} catch (final Exception ex) {
			throw new ValidateClientCertException("Ha ocurrido un error al extraer certificado : " + ex.getMessage(),
					ex, idSesion);
		}

		return cer;

	}

	/**
	 * Obtiene años entre 2 fechas.
	 *
	 * @param fechaInicio Fecha inicio.
	 * @param fechaFin    Fecha fin.
	 * @return años
	 */
	private int diferenciaAnyos(final Date fechaInicio, final Date fechaFin) {
		final LocalDate ldFechaInicio = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		final LocalDate ldFechaFin = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		final Period period = Period.between(ldFechaInicio, ldFechaInicio);
		final int diff = period.getYears();
		return diff;
	}

	/**
	 * Extraer datos autenticación Clave.
	 *
	 * @param datosSesion       datos sesión
	 * @param relayStateRequest relayStateRequest
	 * @param authnResponse     authnResponse
	 * @return datos autenticación Clave
	 */
	private DatosAutenticacion extractDatosAutenticacionClave(final DatosSesion datosSesion,
			final String relayStateRequest, final IAuthenticationResponseNoMetadata authnResponse) {
		// Extraemos IDP

		// Extraemos atributos
		final ImmutableMap<AttributeDefinition<?>, ImmutableSet<? extends AttributeValue<?>>> attrList = authnResponse
				.getAttributes().getAttributeMap();
		final UnmodifiableIterator<AttributeDefinition<?>> iterator = attrList.keySet().iterator();
		final Map<String, String> attrMap = new HashMap<>();
		while (iterator.hasNext()) {
			final AttributeDefinition<?> it = iterator.next();
			attrMap.put(it.getFriendlyName(), attrList.get(it).toString());
		}

		// FamilyName, FirstName, PersonIdentifier, FirstSurname, PartialAfirma,
		// RelayState]
		final TypeIdp idp = ClaveLoginUtil.convertIssuerToIdp(attrMap.get("SelectedIdP"));
		String nif = normalizarNif(ClaveLoginUtil.extraerDatoClave(attrMap.get("PersonIdentifier")));
		String nombre = this.normalizarTexto(ClaveLoginUtil.extraerDatoClave(attrMap.get("FirstName")));
		String apellidos = this.normalizarTexto(ClaveLoginUtil.extraerDatoClave(attrMap.get("FamilyName")));
		String apellido1 = this.normalizarTexto(ClaveLoginUtil.extraerDatoClave(attrMap.get("FirstSurname")));
		String apellido2 = null;
		final String afirmaResponse = attrMap.get("PartialAfirma");
		final String relayState = ClaveLoginUtil.extraerDatoClave(attrMap.get("RelayState"));
		final String levelAuth = authnResponse.getLevelOfAssurance();
		final Integer qaaAutenticacion = ClaveLoginUtil
				.convertLevelOfAssuranceToQaa(LevelOfAssurance.fromString(levelAuth));

		if (!StringUtils.equals(datosSesion.getSesion().getSamlIdPeticion(), relayState)
				|| !StringUtils.equals(datosSesion.getSesion().getSamlIdPeticion(), relayStateRequest)) {
			log.debug("La respuesta no se corresponde a la peticion SAML origen [idSesion = "
					+ datosSesion.getSesion().getIdSesion() + "]");
			throw new ValidateClaveException("La respuesta no se corresponde a la peticion SAML origen",
					datosSesion.getSesion().getIdSesion());
		}

		// Si es persona juridica gestiona apellidos
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
		DatosPersona representante = null;
		if (afirmaResponse != null) {
			final Map<String, String> infoCertificado = AFirmaUtil.extraerInfoCertificado(afirmaResponse,
					datosSesion.getSesion().getIdSesion());
			final String clasificacion = infoCertificado.get("clasificacion");
			if ("11".equals(clasificacion) || "12".equals(clasificacion)) {
				// Datos representante
				representante = new DatosPersona();
				representante.setNif(nif);
				representante.setNombre(nombre);
				representante.setApellidos(apellidos);
				representante.setApellido1(apellido1);
				representante.setApellido2(apellido2);

				// Datos persona juridica
				nif = normalizarNif(infoCertificado.get("NIF-CIF"));
				nombre = this.normalizarTexto(infoCertificado.get("razonSocial"));
				apellidos = null;
				apellido1 = null;
				apellido2 = null;
			}
		}

		final DatosPersona persona = new DatosPersona(nif, nombre, apellidos, apellido1, apellido2);

		final DatosAutenticacion datosAutenticacion = new DatosAutenticacion(datosSesion.getSesion().getIdSesion(),
				new Date(), qaaAutenticacion, idp, persona, representante, datosSesion.getSesion().getParamsApp());
		return datosAutenticacion;
	}

	/**
	 * Extrae datos autenticación del certificado.
	 *
	 * @param pIdSesion   id sesión
	 * @param certificado certificado
	 * @return datos autenticación
	 */
	private DatosAutenticacion extractDatosAutenticacionCertificado(final String pIdSesion,
			final X509Certificate certificado) {

		String nif = null;
		String nombre = null;
		String apellidos = null;
		String apellido1 = null;
		String apellido2 = null;
		DatosPersona representante = null;

		// Valida certificado contra plugin certificados
		ResultatValidacio infoCert = null;
		try {
			final ICertificatePlugin plgCert = (ICertificatePlugin) config
					.createPlugin("plugin.validacionCertificados");
			infoCert = plgCert.getInfoCertificate(certificado);
		} catch (final Exception ex) {
			throw new ValidateClientCertException(
					"Error al validar certificado contra plugin certificados: " + ex.getMessage(), ex, pIdSesion);
		}

		// Valida respuesta
		if (infoCert == null || infoCert.getResultatValidacioCodi() != ResultatValidacio.RESULTAT_VALIDACIO_OK) {
			throw new ValidateClientCertException(
					"Error al validar certificado: " + infoCert.getResultatValidacioDescripcio(), pIdSesion);
		}

		// Recoge datos segun tipo certificado
		switch (infoCert.getInformacioCertificat().getClassificacio()) {
		case 0:
		case 5:
			// Certificados persona fisica
			nif = normalizarNif(infoCert.getInformacioCertificat().getNifResponsable());
			nombre = infoCert.getInformacioCertificat().getNomResponsable();
			apellidos = infoCert.getInformacioCertificat().getLlinatgesResponsable();
			apellido1 = infoCert.getInformacioCertificat().getPrimerLlinatgeResponsable();
			apellido2 = infoCert.getInformacioCertificat().getSegonLlinatgeResponsable();
			break;
		case 1:
			// Certificados persona juridica (a extinguir)
			nif = normalizarNif(infoCert.getInformacioCertificat().getUnitatOrganitzativaNifCif());
			nombre = infoCert.getInformacioCertificat().getRaoSocial();
			break;
		case 11:
		case 12:
			// Certificados representacion
			// - Datos representante
			representante = new DatosPersona();
			representante.setNif(normalizarNif(infoCert.getInformacioCertificat().getNifResponsable()));
			representante.setNombre(infoCert.getInformacioCertificat().getNomResponsable());
			representante.setApellidos(infoCert.getInformacioCertificat().getLlinatgesResponsable());
			representante.setApellido1(infoCert.getInformacioCertificat().getPrimerLlinatgeResponsable());
			representante.setApellido2(infoCert.getInformacioCertificat().getSegonLlinatgeResponsable());
			// - Datos persona juridica
			nif = normalizarNif(infoCert.getInformacioCertificat().getUnitatOrganitzativaNifCif());
			nombre = infoCert.getInformacioCertificat().getRaoSocial();
			break;

		default:
			throw new ValidateClientCertException(
					"Tipo de certificado no soportado: " + infoCert.getInformacioCertificat().getClassificacio(),
					pIdSesion);
		}

		final DatosPersona autenticado = new DatosPersona(nif, nombre, apellidos, apellido1, apellido2);

		// TODO VER SI SE SABE CUANDO ES NIVEL 3 (DISPOSITIVO SEGURO) ¿POR CA?
		final DatosAutenticacion datosAutenticacion = new DatosAutenticacion(pIdSesion, new Date(), 2,
				TypeIdp.CLIENTCERT, autenticado, representante, null);
		return datosAutenticacion;
	}

	/**
	 * Extrae datos autenticación keycloak.
	 *
	 * @param idSesion       idSesion
	 * @param accessToken    accessToken
	 * @param keycloakConfig keycloakConfig
	 * @return datos autenticación
	 */
	private DatosAutenticacion extractDatosAutenticacionKeycloak(final String idSesion, final AccessToken accessToken,
			final ConfiguracionKeycloak keycloakConfig) {
		final String nombreCompleto = accessToken.getName();
		final String apellidos = StringUtils.defaultString(accessToken.getFamilyName());
		final String nombre = nombreCompleto.substring(0, nombreCompleto.length() - apellidos.length());
		final String nif = normalizarNif((String) accessToken.getOtherClaims().get(keycloakConfig.getAtributoNif()));
		final String apellido1 = (String) accessToken.getOtherClaims().get(keycloakConfig.getAtributoApellido1());
		final String apellido2 = (String) accessToken.getOtherClaims().get(keycloakConfig.getAtributoApellido2());

		// Verificamos datos obligatorios
		if (StringUtils.isBlank(nif)) {
			throw new ValidateUsuarioPasswordException("No se ha podido obtener el nif de los datos del usuario",
					idSesion);
		}
		// TODO VER SI SE HA REALIZADO BIEN EL DESGLOSE DE APELLIDOS

		final DatosPersona autenticado = new DatosPersona(nif, nombre, apellidos, apellido1, apellido2);
		// QAA Usuario/Password es bajo (1)
		final DatosAutenticacion datosAutenticacion = new DatosAutenticacion(idSesion, new Date(), 1,
				TypeIdp.USUARIO_PASSWORD, autenticado, null, null);
		return datosAutenticacion;
	}

	/**
	 * Genera evidencias autenticación.
	 *
	 * @param datosAutenticacion Datos autenticación
	 * @param headers            Headers
	 * @param ipAddressFrom      Ip (getRemoteAddress)
	 * @param evidenciasMetodo   Evidencias propias del método usado
	 */
	private EvidenciasAutenticacion generarEvidenciasAutenticacion(final DatosAutenticacion datosAutenticacion,
			final Map<String, String> headers, final String ipAddressFrom,
			final List<PropiedadAutenticacion> evidenciasMetodo) {

		// TODO MULTIIDIOMA

		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		final ConfiguracionAuditoria confAuditoria = config.getConfiguracionAuditoria();

		final List<PropiedadAutenticacion> listaPropiedades = new ArrayList<>();
		listaPropiedades.add(new PropiedadAutenticacion("Sesión autenticación", TypePropiedad.TEXTO,
				datosAutenticacion.getIdSesion(), true));
		listaPropiedades.add(new PropiedadAutenticacion("Fecha autenticación", TypePropiedad.FECHA,
				sdf.format(datosAutenticacion.getFechaTicket()), true));
		listaPropiedades.add(new PropiedadAutenticacion("Método autenticación", TypePropiedad.TEXTO,
				getMetodoAutenticacionEvidenciasAutenticacion(datosAutenticacion), true));
		listaPropiedades.add(new PropiedadAutenticacion("Nivel autenticación", TypePropiedad.TEXTO,
				getQaaEvidenciasAutenticacion(datosAutenticacion), true));
		if (confAuditoria.isIpAuditar()) {
			final String clientIpAddress = getClientIpAddress(confAuditoria.getIpHeaders(), headers, ipAddressFrom);
			listaPropiedades.add(new PropiedadAutenticacion("Ip acceso", TypePropiedad.TEXTO, clientIpAddress,
					confAuditoria.isIpMostrar()));
		}
		if (datosAutenticacion.getAutenticado() != null) {
			listaPropiedades.add(new PropiedadAutenticacion("Nombre", TypePropiedad.TEXTO,
					datosAutenticacion.getAutenticado().getNombre(), true));
			listaPropiedades.add(new PropiedadAutenticacion("Apellidos", TypePropiedad.TEXTO,
					datosAutenticacion.getAutenticado().getApellidos(), true));
			listaPropiedades.add(new PropiedadAutenticacion("NIF", TypePropiedad.TEXTO,
					datosAutenticacion.getAutenticado().getNif(), true));
		}
		if (datosAutenticacion.getRepresentante() != null) {
			listaPropiedades.add(new PropiedadAutenticacion("Nombre representante", TypePropiedad.TEXTO,
					datosAutenticacion.getRepresentante().getNombre(), true));
			listaPropiedades.add(new PropiedadAutenticacion("Apellidos representante", TypePropiedad.TEXTO,
					datosAutenticacion.getRepresentante().getApellidos(), true));
			listaPropiedades.add(new PropiedadAutenticacion("Nif representante", TypePropiedad.TEXTO,
					datosAutenticacion.getRepresentante().getNif(), true));
		}

		// Añadimos evidencias propias del metodo usado
		if (evidenciasMetodo != null) {
			for (final PropiedadAutenticacion p : evidenciasMetodo) {
				if (p.getTipo() == TypePropiedad.XML_B64) {
					p.setMostrar(confAuditoria.isXmlMostrar());
				}
				listaPropiedades.add(p);
			}
		}
		final EvidenciasLista evidenciasLista = new EvidenciasLista();
		evidenciasLista.setPropiedades(listaPropiedades);

		// Generamos JSON
		final String evidenciasJSON = JsonUtil.toJson(evidenciasLista);

		// Generamos HASH
		final String evidenciasHash = ConvertUtil.generateHash(evidenciasJSON, confAuditoria.getAlgoritmoHash());

		// Retornamos evidencias
		final EvidenciasAutenticacion evidencias = new EvidenciasAutenticacion();
		evidencias.setEvidenciasLista(evidenciasLista);
		evidencias.setEvidenciasJSON(evidenciasJSON);
		evidencias.setEvidenciasHash(evidenciasHash);
		evidencias.setAlgoritmoHash(confAuditoria.getAlgoritmoHash());

		return evidencias;
	}

	private String getMetodoAutenticacionEvidenciasAutenticacion(final DatosAutenticacion datosAutenticacion) {
		// TODO MULTIIDIOMA
		String res = "";
		switch (datosAutenticacion.getMetodoAutenticacion()) {
		case ANONIMO:
			res = "Anónimo";
			break;
		case CERTIFICADO:
			res = "Certificado digital";
			break;
		case CLIENTCERT:
			res = "Certificado digital";
			break;
		case CLAVE_PERMANENTE:
			res = "Cl@ve Permanente";
			break;
		case CLAVE_PIN:
			res = "Cl@ve Pin";
			break;
		case USUARIO_PASSWORD:
			res = "Usuario / Contraseña";
			break;
		}

		return res;
	}

	private String getQaaEvidenciasAutenticacion(final DatosAutenticacion datosAutenticacion) {
		// TODO MULTIIDIOMA
		String res = "";
		if (datosAutenticacion.getQaa() == null) {
			res = "Ninguno";
		} else {
			switch (datosAutenticacion.getQaa()) {
			case 1:
				res = "Bajo";
				break;
			case 2:
				res = "Sustancial";
				break;
			case 3:
				res = "Alto";
				break;
			}
		}
		return res;
	}

	/**
	 * Obtiene configuración keycloak.
	 *
	 * @param idSesion idSesion
	 * @return configuración
	 */
	private ConfiguracionKeycloak obtenerConfiguracionKeycloak(final String idSesion) {
		final ConfiguracionKeycloak keycloakConfig = config.getConfiguracionKeycloak();
		if (StringUtils.isEmpty(keycloakConfig.getConfiguracion())) {
			throw new ValidateUsuarioPasswordException("No existe en properties la configuración para Keycloak",
					idSesion);
		}
		if (StringUtils.isEmpty(keycloakConfig.getAtributoNif())) {
			throw new ValidateUsuarioPasswordException(
					"No existe en properties el atributo nif en la configuración para Keycloak", idSesion);
		}
		if (StringUtils.isEmpty(keycloakConfig.getAtributoApellido1())) {
			throw new ValidateUsuarioPasswordException(
					"No existe en properties el atributo apellido1 en la configuración para Keycloak", idSesion);
		}
		if (StringUtils.isEmpty(keycloakConfig.getAtributoApellido2())) {
			throw new ValidateUsuarioPasswordException(
					"No existe en properties el atributo apellido2 en la configuración para Keycloak", idSesion);
		}
		return keycloakConfig;
	}

	public SesionLogin loginByTicket(String ticket, boolean activa) {
		SesionLogin sl = loginDao.getSesionLoginByTicketModel(ticket, activa);
		return sl;
	}

	public void mergeDesglose(SesionLogin sl) {
		loginDao.update(sl);
	}

	@Override
	public String iniciarSesionTest(String iIdioma, String Ientidad, Integer Iqaa, String url, boolean forzarDesglose) {
		final String entidad = (Ientidad != null) ? Ientidad : "A04003003";
		final String urlCallback = url;
		final String urlCallbackError = url;

		final String idioma;
		if (iIdioma == null || iIdioma.isEmpty()) {
			idioma = "ca";
		} else {
			idioma = iIdioma;
		}
		final List<TypeIdp> idps = new ArrayList<>();
		idps.add(TypeIdp.CERTIFICADO);
		idps.add(TypeIdp.CLAVE_PERMANENTE);
		idps.add(TypeIdp.CLAVE_PIN);
		idps.add(TypeIdp.CLIENTCERT);
		final Integer qaa = (Iqaa != null) ? Iqaa : 1;
		final boolean iniClaAuto = false;
		final boolean forceAuth = false;
		final String aplicacion = "TESTAP";
		final boolean auditar = false;
		final Map<String, String> paramsApp = new HashMap<>();
		if (forzarDesglose) {
			paramsApp.put(DatosAutenticacion.PARAM_FORZAR_DESGLOSE, DatosAutenticacion.PARAM_FORZAR_DESGLOSE_VALOR);
		}

		final String idSesion = loginDao.crearSesionLogin(entidad, urlCallback, urlCallbackError, idioma, idps, qaa,
				iniClaAuto, forceAuth, aplicacion, auditar, paramsApp, true);
		log.debug(" Creada sesion clave:  [idSesion = " + idSesion + "] [idps = " + idps + "] [urlCallback = "
				+ urlCallback + "]");
		return idSesion;
	}

	@Override
	public TicketDesglose procesarRespuestaDesglose(DesgloseApellidos desglose) {
		final SesionLogin sesion = loginDao.getSesionLoginByTicketModel(desglose.getTicket(), false);
		final String nif;

		// El nif es el propio o el del representante.
		// IMPORTANTE: SE TIENE QUE COGER EL NIF DE LA SESION/TICKET, NO DE LO QUE NOS
		// VENGA SINO EL USUARIO PUEDE HACER TRIQUIÑUELAS
		boolean isRepresentante;
		if (sesion.getRepresentanteNombre() != null && !sesion.getRepresentanteNombre().isEmpty()) {
			nif = sesion.getRepresentanteNif();
			isRepresentante = true;
		} else {
			nif = sesion.getNif();
			isRepresentante = false;
		}

		DesgloseApellidos desgloseApellidos = desgloseDao.getByNif(nif);
		if (desgloseApellidos == null) {
			// Si no existe, crearlo
			desgloseApellidos = new DesgloseApellidos();
			desgloseApellidos.setNif(nif);
			desgloseApellidos.setNombre(desglose.getNombre());
			desgloseApellidos.setApellido1(desglose.getApellido1());
			desgloseApellidos.setApellido2(desglose.getApellido2());
			desgloseApellidos.setApellidoComp(desglose.getApellido1() + ' ' + desglose.getApellido2());
			desgloseApellidos.setNombreCompleto(
					desglose.getNombre() + ' ' + desglose.getApellido1() + ' ' + desglose.getApellido2());
			desgloseApellidos.setFechaCreacion(new Date());
			desgloseDao.add(desgloseApellidos);

		} else {
			// Si ya existe, solo actualizar
			desgloseApellidos.setNombre(desglose.getNombre());
			desgloseApellidos.setApellido1(desglose.getApellido1());
			desgloseApellidos.setApellido2(desglose.getApellido2());
			desgloseApellidos.setApellidoComp(desglose.getApellido1() + ' ' + desglose.getApellido2());
			desgloseApellidos.setNombreCompleto(
					desglose.getNombre() + ' ' + desglose.getApellido1() + ' ' + desglose.getApellido2());
			desgloseApellidos.setFechaActualizacion(new Date());
			desgloseDao.update(desgloseApellidos);
		}

		// IMPORTANTE, DEPENDIENDO DE SI ES REPRESENTANTE, SE ACTUALIZA UN NOMBRE U OTRO
		loginDao.updateNombre(sesion.getId(), isRepresentante, desglose.getNombre(), desglose.getApellido1(),
				desglose.getApellido2());

		TicketDesglose ticketDesglose = new TicketDesglose();
		ticketDesglose.setIdioma(sesion.getIdioma());
		ticketDesglose.setTicket(sesion.getTicket());
		ticketDesglose.setUrlCallback(sesion.getUrlCallback());
		ticketDesglose.setNif(nif);

		if (ticketDesglose.isForzarDesglose()) {
			PersonalizacionEntidad personalizacion = getPersonalizacionEntidad(sesion.getEntidad(), sesion.getIdioma());
			ticketDesglose.setPersonalizacion(personalizacion);
		}
		return ticketDesglose;
	}

	/**
	 * Normalizar nif/cif pasando a mayusculas, rellenando con 0 hasta tamaño 9 y
	 * quitando espacios,/,\ y -.
	 *
	 * @param nif
	 *                nif/cif
	 * @return nif/cif normalizado
	 */
	private String normalizarNif(final String nif) {
		String doc = null;
		if (nif != null) {
			// Quitamos espacios y otros caracteres
			doc = nif.toUpperCase();
			doc = doc.replaceAll("[\\/\\s\\-]", "");
			// Rellenamos con 0
			if (doc.length() > 1) {
				final String primerCaracter = doc.substring(0, 1);
				if (Pattern.matches("[^A-Z]", primerCaracter)) {
					// Es nif
					doc = StringUtils.leftPad(doc, 9, '0');
				} else {
					// es cif o nie
					final String letraInicio = doc.substring(0, 1);
					final String resto = doc.substring(1);

					doc = letraInicio + StringUtils.leftPad(resto, 8, '0');

				}
			} else {
				doc = nif;
			}

		}
		return doc;
	}


	private String normalizarTexto(final String texto) {
		String textoNormalizado = texto.replace("´", "'");
		textoNormalizado = StringUtils.replace(texto, "`", "'");
		textoNormalizado = StringUtils.replace(texto, "‘", "'");
		textoNormalizado = StringUtils.replace(texto, "’", "'");
		return textoNormalizado;
	}

}
