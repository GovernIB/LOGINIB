package es.caib.loginib.core.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
		if (idpAutenticado && (qaa <= 0 || qaa > 3)) {
			throw new ErrorParametroException("No se ha especificado un qaa valido");
		}

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

		// Atributos personales que se desean conocer
		final ImmutableAttributeMap.Builder reqAttrMapBuilder = new ImmutableAttributeMap.Builder();

		if (!datosSesion.getIdps().contains(TypeIdp.CERTIFICADO)) {
			reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://es.minhafp.clave/AFirmaIdP")
					.friendlyName("AFirmaIdP").personType(PersonType.NATURAL_PERSON).required(false)
					.uniqueIdentifier(true).xmlType("http://www.w3.org/2001/XMLSchema", "AFirmaIdPType", "cl")
					.attributeValueMarshaller(new StringAttributeValueMarshaller()).build());
		}
		if (!datosSesion.getIdps().contains(TypeIdp.CLAVE_PERMANENTE)) {
			reqAttrMapBuilder.put(new AttributeDefinition.Builder<String>().nameUri("http://es.minhafp.clave/GISSIdP")
					.friendlyName("GISSIdP").personType(PersonType.NATURAL_PERSON).required(false)
					.uniqueIdentifier(true).xmlType("http://www.w3.org/2001/XMLSchema", "GISSIdPType", "cl")
					.attributeValueMarshaller(new StringAttributeValueMarshaller()).build());
		}
		if (!datosSesion.getIdps().contains(TypeIdp.CLAVE_PIN)) {
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
		String providerName = config.getProviderName(datosSesion.getEntidad());
		if (StringUtils.isNotBlank(datosSesion.getAplicacion())) {
			providerName += ";" + datosSesion.getAplicacion();
		}
		reqBuilder.providerName(providerName);
		reqBuilder.requestedAttributes(reqAttrMapBuilder.build());

		final LevelOfAssurance level = ClaveLoginUtil.convertQaaToLevelOfAssurance(datosSesion.getQaa());

		reqBuilder.levelOfAssurance(level.stringValue());
		reqBuilder.levelOfAssuranceComparison(LevelOfAssuranceComparison.fromString("minimum").stringValue());
		reqBuilder.nameIdFormat(SamlNameIdFormat.UNSPECIFIED.getNameIdFormat());
		reqBuilder.binding(EidasSamlBinding.POST.getName());
		reqBuilder.assertionConsumerServiceURL(config.getLoginCallbackClave() + "/" + idSesion + ".html");
		reqBuilder.forceAuth(datosSesion.isForceAuth());
		reqBuilder.spApplication(datosSesion.getAplicacion());

		// Generamos peticion SAML. Se firma la petición indicando los datos del
		// destino
		final ProtocolEngineNoMetadataI engine = getEngineSamlFactory(datosSesion.getEntidad());

		final EidasAuthenticationRequestNoMetadata buildRequest = reqBuilder.build();
		IRequestMessageNoMetadata message = null;
		try {
			message = engine.generateRequestMessage(buildRequest);
		} catch (final EIDASSAMLEngineException e) {
			throw new GenerarPeticionClaveException(e);
		}
		final byte[] samlRequestXML = message.getMessageBytes();

		// Pasamos a B64 y retornamos
		final String samlRequestB64 = EidasStringUtil.encodeToBase64(samlRequestXML);
		try {
			log.debug(" Peticion generada [idSesion = " + idSesion + "]: " + new String(samlRequestXML, "UTF-8"));
		} catch (final UnsupportedEncodingException e) {
			// No hacemos nada;
		}

		// Guardamos en sesion el id saml
		claveDao.establecerSamlIdSesionLogin(idSesion, relayState);

		// Devolvemos datos necesarios para invocar a Clave
		final PeticionClave peticionClave = new PeticionClave();
		peticionClave.setSamlRequestB64(samlRequestB64);
		peticionClave.setUrlClave(config.getPepsUrl());
		peticionClave.setIdioma(datosSesion.getIdioma());
		peticionClave.setRelayState(relayState);
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
		final ProtocolEngineNoMetadataI engine = getEngineSamlFactory(datosSesion.getEntidad());

		// Primero se extraen los bytes de la respuesta recibida
		final byte[] decSamlTicket = EidasStringUtil.decodeBytesFromBase64(pSamlResponseB64);
		String samlXml = null;
		try {
			samlXml = new String(decSamlTicket, "UTF-8");
		} catch (final UnsupportedEncodingException ueo) {
			throw new ErrorRespuestaClaveException(ueo, pIdSesion);
		}

		// Se lee y se valida
		IAuthenticationResponseNoMetadata authnResponse;

		// En caso de error durante la validación se produce una excepción
		try {
			authnResponse = engine.unmarshallResponseAndValidate(decSamlTicket, new URI(config.getPepsUrl()).getHost(),
					0, 0, config.getLoginCallbackClave() + "/" + datosSesion.getIdSesion() + ".html");
		} catch (EIDASSAMLEngineException | URISyntaxException e) {
			throw new ErrorRespuestaClaveException(e, pIdSesion);
		}

		// Con el objeto obtenido se puede comprobar si es una autenticción
		// exitosa y final extraer los datos recibidos.
		if (authnResponse.isFailure()) {
			log.error("La respuesta indica que hay un error [idSesion = " + pIdSesion + "]: "
					+ authnResponse.getStatusMessage());
			throw new ErrorRespuestaClaveException("Saml Response is fail: " + authnResponse.getStatusMessage(),
					pIdSesion);
		}

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
		String nif = ClaveLoginUtil.extraerDatoClave(attrMap.get("PersonIdentifier"));
		String nombre = ClaveLoginUtil.extraerDatoClave(attrMap.get("FirstName"));
		String apellidos = ClaveLoginUtil.extraerDatoClave(attrMap.get("FamilyName"));
		String apellido1 = ClaveLoginUtil.extraerDatoClave(attrMap.get("FirstSurname"));
		String apellido2 = null;
		final String afirmaResponse = attrMap.get("PartialAfirma");
		final String relayState = ClaveLoginUtil.extraerDatoClave(attrMap.get("RelayState"));
		final String levelAuth = authnResponse.getLevelOfAssurance();
		final Integer qaaAutenticacion = ClaveLoginUtil
				.convertLevelOfAssuranceToQaa(LevelOfAssurance.fromString(levelAuth));

		if (!StringUtils.equals(datosSesion.getSamlIdPeticion(), relayState)
				|| !StringUtils.equals(datosSesion.getSamlIdPeticion(), relayStateRequest)) {
			log.debug("La respuesta no se corresponde a la peticion SAML origen [idSesion = " + pIdSesion + "]");
			throw new ErrorRespuestaClaveException("La respuesta no se corresponde a la peticion SAML origen",
					pIdSesion);
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
		DatosRepresentante representante = null;
		if (afirmaResponse != null) {
			final Map<String, String> infoCertificado = AFirmaUtil.extraerInfoCertificado(afirmaResponse);
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
		log.debug(" Datos obtenidos clave [idSesion = " + pIdSesion + "]: QAA= " + qaaAutenticacion + ", Nivel=" + idp
				+ ", Nif=" + nif + ", Nombre=" + nombre + " " + apellidos);

		final TicketClave respuesta = claveDao.generateTicketSesionLogin(pIdSesion, idp, qaaAutenticacion, nif, nombre,
				apellidos, apellido1, apellido2, representante);

		log.debug(" Ticket generado [idSesion = " + pIdSesion + "]: " + respuesta.getTicket());

		return respuesta;
	}

	@Override
	@NegocioInterceptor
	public TicketClave simularRespuestaClave(final String pIdSesion, final TypeIdp pIdp, final String pNif,
			final String pNombre, final String pApellidos, final String pApellido1, final String pApellido2) {
		return claveDao.generateTicketSesionLogin(pIdSesion, pIdp,
				ClaveLoginUtil.convertLevelOfAssuranceToQaa(LevelOfAssurance.SUBSTANTIAL), pNif, pNombre, pApellidos,
				pApellido1, pApellido2, null);
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
		return claveDao.generateTicketSesionLogin(pIdSesion, TypeIdp.ANONIMO, null, null, null, null, null, null, null);
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

		// Recupera datos logout
		final DatosLogoutSesion datosSesion = claveDao.obtenerDatosSesionLogout(idSesion);
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
		claveDao.establecerSamlIdSesionLogout(idSesion, idSaml);

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
		final DatosLogoutSesion datosSesion = claveDao.obtenerDatosSesionLogout(pIdSesion);
		final String urlCallback = datosSesion.getUrlCallback();
		final String samlIdPeticion = datosSesion.getSamlIdPeticion();

		// Primero se extraen los bytes de la respuesta recibida
		final byte[] decSamlTicket = EidasStringUtil.decodeBytesFromBase64(pSamlResponseB64);

		// Decodifica respuesta Clave
		final ProtocolEngineNoMetadataI engine = getEngineSamlFactory(datosSesion.getEntidad());
		LogoutResponse logoutReq = null;
		try {
			logoutReq = engine.unmarshallLogoutResponseAndValidate(decSamlTicket,
					new URI(config.getPepsLogout()).getHost(), 0, 0,
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

	/**
	 * Obtiene engine para saml de Clave.
	 *
	 * @param entidad
	 *                    entidad
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

}
