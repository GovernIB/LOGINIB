package es.caib.loginib.core.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
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
import eu.eidas.auth.commons.EidasStringUtil;
import eu.eidas.auth.commons.attribute.AttributeDefinition;
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
	public String iniciarLoginClave(final String entidad, final String pUrlCallback, final String idioma,
			final List<TypeIdp> idps, final int qaa, final boolean forceAuth, final String aplicacion) {
		log.debug(" Crea sesion clave: [idps = " + idps + "] [urlCallback = " + pUrlCallback + "]");
		final String idSesion = claveDao.crearSesionLogin(entidad, pUrlCallback, idioma, idps, qaa, forceAuth,
				aplicacion);
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

		// TODO CLAVE2 respuesta @firma?? qAA cambiado por levelOfAssurance?? spId??
		// - spApplication -> se indica que parametro desaparece (poner ID SIA en el
		// provider name), pero si hay metodo para establecer. Se consulta a CAID.
		// - spId / spSector-> parece que desaparece

		/*
		 * att = new PersonalAttribute(); att.setName("afirmaResponse");
		 * att.setIsRequired(false); pAttList.add(att);
		 */

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
		reqBuilder.providerName(config.getProviderName(datosSesion.getEntidad()));
		reqBuilder.requestedAttributes(reqAttrMapBuilder.build());

		// TODO CLAVE2 Nivel QAA cambiado por esto
		reqBuilder.levelOfAssurance(LevelOfAssurance.LOW.stringValue());
		reqBuilder.levelOfAssuranceComparison(LevelOfAssuranceComparison.fromString("minimum").stringValue());

		// urn:oasis:names:tc:SAML:2.0:nameid-format:persistent
		// SamlNameIdFormat.PERSISTENT
		// urn:oasis:names:tc:SAML:2.0:nameid-format:transient
		// SamlNameIdFormat.TRANSIENT
		// urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified
		// SamlNameIdFormat.UNSPECIFIED
		reqBuilder.nameIdFormat(SamlNameIdFormat.UNSPECIFIED.getNameIdFormat());
		reqBuilder.binding(EidasSamlBinding.EMPTY.getName());
		reqBuilder.assertionConsumerServiceURL(config.getLoginCallbackClave() + "/" + idSesion + ".html");
		reqBuilder.forceAuth(datosSesion.isForceAuth());
		reqBuilder.spApplication(config.getSpApplication(datosSesion.getEntidad()));

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
		final byte[] ticket = message.getMessageBytes();

		// Pasamos a B64 y retornamos
		final String samlRequestB64 = EidasStringUtil.encodeToBase64(ticket);
		// log.debug(" Peticion generada [idSesion = " + idSesion + "]: "
		// + samlRequestXML);

		// Extraemos saml id peticion y guardamos en sesion
		final String samlId = SamlUtil.extraerSamlId(samlRequestB64);
		if (StringUtils.isBlank(samlId)) {
			throw new GenerarPeticionClaveException(
					"No se ha podido extraer SamlId de la peticion [idSesion = " + idSesion + "]");
		}
		claveDao.establecerSamlIdSesionLogin(idSesion, samlId);

		// TODO CLAVE2 revisar params
		// request.setAttribute("samlRequest", samlRequest);
		// request.setAttribute("RelayState",
		// SecureRandomXmlIdGenerator.INSTANCE.generateIdentifier(8));
		// request.setAttribute("nodeServiceUrl", nodeServiceUrl);
		// request.setAttribute(EidasParameterKeys.BINDING.toString(),
		// getRedirectMethod());

		// Devolvemos datos necesarios para invocar a Clave
		final PeticionClave peticionClave = new PeticionClave();
		peticionClave.setSamlRequestB64(samlRequestB64);
		peticionClave.setUrlClave(config.getPepsUrl());
		peticionClave.setIdioma(datosSesion.getIdioma());

		// TODO CLAVE2 Ya no es necesario se fija en saml
		peticionClave.setIdps(ClaveLoginUtil.removeAnonimoFromIdps(datosSesion.getIdps()));
		// TODO CLAVE2 Guardar en BD para comparar a la vuelta? Tiene que ver
		// algo con el relay state que se pasa en los parametros antes?
		peticionClave.setRelayState(relayState);
		return peticionClave;
	}

	@Override
	@NegocioInterceptor
	public TicketClave procesarRespuestaLoginClave(final String pIdSesion, final String pSamlResponseB64) {

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
		// Se lee y se valida
		IAuthenticationResponseNoMetadata authnResponse;

		// En caso de error durante la validación se produce una excepción
		// TODO CLAVE2 faltaria validar respuesta, ¿se requiere remote host?
		try {
			authnResponse = engine.unmarshallResponseAndValidate(decSamlTicket, new URI(config.getPepsUrl()).getHost(),
					0, 0, datosSesion.getUrlCallback());
		} catch (EIDASSAMLEngineException | URISyntaxException e) {
			throw new ErrorRespuestaClaveException(e);
		}

		// En caso de que se haya recibido una respuesta cifrada, se puede
		// descifrar independientemente
		// TODO CLAVE2 en ejemplo viene comentado, en documentación no. No
		// existe un metodo.
		// if (SPUtil.isEncryptedSamlResponse(decSamlTicket)) {
		// final byte[] eidasTicketSAML =
		// engine.checkAndDecryptResponse(decSamlTicket);
		// final String samlUnencryptedResponseXML =
		// SPUtil.extractAssertionAsString(EidasStringUtil.toString(eidasTicketSAML));
		// }

		// Con el objeto obtenido se puede comprobar si es una autenticción
		// exitosa y final extraer los datos recibidos.
		if (authnResponse.isFailure()) {
			log.debug("La respuesta indica que hay un error [idSesion = " + pIdSesion + "]: "
					+ authnResponse.getStatusMessage());
			throw new ErrorRespuestaClaveException("Saml Response is fail: " + authnResponse.getStatusMessage());
		}

		// Verificamos que la peticion se corresponde a la sesion
		if (!StringUtils.equals(datosSesion.getSamlIdPeticion(), authnResponse.getInResponseToId())) {
			log.debug("La respuesta no se corresponde a la peticion SAML origen [idSesion = " + pIdSesion + "]");
			throw new ErrorRespuestaClaveException("La respuesta no se corresponde a la peticion SAML origen");
		}

		// Obtenemos atributos
		// TODO CLAVE2 Pendiente de saber como obtenerlo, ponemos fijo para
		// pruebas
		// final String idpClave =
		// authnResponse.getAssertions().get(0).getIssuer().getValue();
		String idpClave = idpClave = "aFirma";
		log.debug(" Idp retornado de Clave [idSesion = " + pIdSesion + "]: " + idpClave);

		final TypeIdp idp = ClaveLoginUtil.traduceIdpClaveToIdp(idpClave);
		if (idp == null) {
			throw new ErrorRespuestaClaveException(
					"Idp retornado no contemplado [idSesion = " + pIdSesion + "]: " + idpClave);
		}

		// TODO CLAVE2 Pendiente de saber como se obtienen los atributos
		authnResponse.getAttributes();

		final String nifClave = null;
		String nombre = null;
		String apellidos = null;
		String apellido1 = null;
		String apellido2 = null;
		final String afirmaResponse = null;

		// final ArrayList<PersonalAttribute> attrList = new
		// ArrayList<PersonalAttribute>(
		// authnResponse.getPersonalAttributeList().values());
		// for (final PersonalAttribute pa : attrList) {
		// log.debug(pa.getName() + " = " + pa.getValue().get(0));
		// if ("eIdentifier".equals(pa.getName())) {
		// nifClave = pa.getValue().get(0);
		// log.debug("eIdentifier -> NIF: " + nifClave);
		// } else if ("givenName".equals(pa.getName())) {
		// nombre = pa.getValue().get(0);
		// log.debug("givenName -> Nombre: " + nombre);
		// } else if ("surname".equals(pa.getName())) {
		// apellidos = pa.getValue().get(0);
		// log.debug("surname -> Apellidos: " + apellidos);
		// } else if ("inheritedFamilyName".equals(pa.getName())) {
		// apellido1 = pa.getValue().get(0);
		// log.debug("inheritedFamilyName -> Apellido1: " + apellido1);
		// } else if ("afirmaResponse".equals(pa.getName())) {
		// afirmaResponse = pa.getValue().get(0);
		// }
		// }

		String nif = ClaveLoginUtil.extraerNif(nifClave);
		if (nif == null) {
			log.debug("La respuesta devuelve un nif no valido [idSesion = " + pIdSesion + "]");
			throw new ErrorRespuestaClaveException("La respuesta devuelve un nif no valido: " + nifClave);
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
		claveDao.purgaTicketSesionLogin(config.getTimeoutProcesoAutenticacion(),
				config.getTimeoutTicketAutenticacion());
		// Procesos de logout
		claveDao.purgaTicketSesionLogout(config.getTimeoutProcesoAutenticacion(),
				config.getTimeoutTicketAutenticacion());

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
	public String obtenerUrlRedireccionLoginClave(final String pIdSesion) {
		try {
			return config.getLoginRedireccionClave() + "?idSesion=" + URLEncoder.encode(pIdSesion, "UTF-8");
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

		return null;

		// log.debug(" Generar peticion logout... ");
		//
		// // TODO DAO PARA SESION LOGOUT (DatosSesionLogout)
		// final DatosLogoutSesion datosSesion = claveDao
		// .obtenerDatosSesionLogout(idSesion);
		// if (datosSesion.getFechaTicket() != null) {
		// throw new GenerarPeticionClaveException(
		// "sesion ya ha sido autenticada en clave");
		// }
		//
		// // Parametros peticion
		// final STORKLogoutRequest req = new STORKLogoutRequest();
		// req.setDestination(config.getPepsLogout());
		// req.setSpProvidedId(config.getProviderName(datosSesion.getEntidad()));
		// req.setIssuer(
		// config.getLogoutCallbackClave() + "/" + idSesion + ".html");
		//
		// // Generamos peticion SAML
		// final STORKSAMLEngine engine = STORKSAMLEngine
		// .getInstance(datosSesion.getEntidad());
		// if (engine == null) {
		// throw new GenerarPeticionClaveException(
		// "Error creando engine STORK. Revise localizacion fichero
		// SignModule_<entidad>.xml (segun SamlEngine.xml)");
		// }
		// STORKLogoutRequest logoutReq = null;
		// try {
		// logoutReq = engine.generateSTORKLogoutRequest(req);
		// } catch (final STORKSAMLEngineException e) {
		// throw new GenerarPeticionClaveException(e);
		// }
		//
		// // Pasamos a B64 y retornamos
		// final byte[] token = logoutReq.getTokenSaml();
		// final String samlRequestB64 = PEPSUtil.encodeSAMLToken(token);
		// final String samlRequestXML = new String(token);
		// log.debug(" Peticion generada: " + samlRequestXML);
		//
		// // TODO Probablemente sea necesario como extraer el saml id para el
		// // logout
		// // Extraemos saml id peticion y guardamos en sesion
		// final String samlId = SamlUtil.extraerSamlIdLogout(samlRequestB64);
		// if (StringUtils.isBlank(samlId)) {
		// throw new GenerarPeticionClaveException(
		// "No se ha podido extraer SamlId de la peticion");
		// }
		// claveDao.establecerSamlIdSesionLogout(idSesion, samlId);
		//
		// // Devolvemos datos necesarios para invocar a Clave
		// final PeticionClaveLogout peticionClave = new PeticionClaveLogout();
		// peticionClave.setSamlRequestB64(samlRequestB64);
		// peticionClave.setUrlClave(config.getPepsLogout());
		// return peticionClave;

	}

	@Override
	@NegocioInterceptor
	public RespuestaClaveLogout procesarRespuestaLogoutClave(final String pIdSesion, final String pSamlResponseB64) {

		return null;

		// log.debug(" Procesando respuesta clave logout");
		// final DatosLogoutSesion datosSesion = claveDao
		// .obtenerDatosSesionLogout(pIdSesion);
		// final String urlCallback = datosSesion.getUrlCallback();
		// final String samlIdPeticion = datosSesion.getSamlIdPeticion();
		//
		// // Decodifica respuesta Clave
		// final byte[] decSamlToken =
		// PEPSUtil.decodeSAMLToken(pSamlResponseB64);
		// final STORKSAMLEngine engine = STORKSAMLEngine
		// .getInstance(datosSesion.getEntidad());
		// if (engine == null) {
		// throw new GenerarPeticionClaveException(
		// "Error creando engine STORK. Revise localizacion fichero
		// SignModule_<entidad>.xml (segun SamlEngine.xml)");
		// }
		// STORKLogoutResponse logoutReq = null;
		// try {
		// logoutReq = engine.validateSTORKLogoutResponse(decSamlToken, null);
		// } catch (final STORKSAMLEngineException e) {
		// throw new ErrorRespuestaClaveException(e);
		// }
		//
		// // Verificamos si hay error al interpretar la respuesta
		// // TODO VER QUE PASA SI NO HAY NINGUNA SESION ACTIVA EN CLAVE
		// RespuestaClaveLogout respuesta = null;
		// if (logoutReq.isFail()) {
		// respuesta = new RespuestaClaveLogout();
		// respuesta.setLogout(false);
		// respuesta.setUrlCallback(urlCallback);
		// } else {
		// // Verificamos que la peticion se corresponde a la sesion
		// if (!StringUtils.equals(samlIdPeticion,
		// logoutReq.getInResponseTo())) {
		// throw new ErrorRespuestaClaveException(
		// "La respuesta no se corresponde a la peticion SAML origen");
		// }
		//
		// respuesta = new RespuestaClaveLogout();
		// respuesta.setLogout(true);
		// respuesta.setUrlCallback(urlCallback);
		//
		// }
		//
		// log.debug("Logout realizado: " + respuesta.isLogout());
		//
		// return respuesta;

	}

	/**
	 * Obtiene engine para saml de Clave.
	 *
	 * @param entidad
	 *            entidad
	 * @return engine
	 */
	private ProtocolEngineNoMetadataI getEngineSamlFactory(final String entidad) {
		// TODO CLAVE2 En clave2 se cachea
		try {
			final ProtocolEngineConfigurationFactoryNoMetadata protocolEngineConfigurationFactory = new ProtocolEngineConfigurationFactoryNoMetadata(
					entidad + "_SamlEngine.xml", null, System.getProperty("es.caib.loginib.clave2conf.path"));
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
