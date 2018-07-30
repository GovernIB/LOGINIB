package es.caib.loginib.core.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.loginib.core.api.ClaveService;
import es.caib.loginib.core.api.exception.ErrorNoControladoException;
import es.caib.loginib.core.api.exception.login.ErrorRespuestaClaveException;
import es.caib.loginib.core.api.exception.login.GenerarPeticionClaveException;
import es.caib.loginib.core.api.exception.login.TicketNoValidoException;
import es.caib.loginib.core.api.model.comun.ConstantesNumero;
import es.caib.loginib.core.api.model.login.DatosLogoutSesion;
import es.caib.loginib.core.api.model.login.DatosSesion;
import es.caib.loginib.core.api.model.login.DatosUsuario;
import es.caib.loginib.core.api.model.login.PeticionClave;
import es.caib.loginib.core.api.model.login.PeticionClaveLogout;
import es.caib.loginib.core.api.model.login.RespuestaClaveLogout;
import es.caib.loginib.core.api.model.login.TicketClave;
import es.caib.loginib.core.interceptor.NegocioInterceptor;
import es.caib.loginib.core.service.repository.dao.ClaveDao;
import es.caib.loginib.core.service.util.AFirmaUtil;
import es.caib.loginib.core.service.util.ClaveLoginUtil;
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
    private final org.slf4j.Logger log = LoggerFactory
            .getLogger(ClaveServiceImpl.class);

    /** Fichero configuracion Clave. */
    private static final String SP_CONF = "SP";

    /** Configuracion. */
    @Resource(name = "negocioModuleConfig")
    private ModuleConfig config;

    /** Dao. */
    @Resource(name = "claveDao")
    private ClaveDao claveDao;

    /**
     * @see es.caib.loginib.core.ClaveService#crearSesionClave(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.Integer)
     */
    @Override
    @NegocioInterceptor
    public String crearSesionClave(final String pUrlCallback,
            final String idioma, final String idps, final Integer qaa,
            final boolean forceAuth) {
        log.debug(" Crea sesion clave: [idps = " + idps + "] [urlCallback = "
                + pUrlCallback + "]");

        // Validamos IDPs
        if (!ClaveLoginUtil.validarIDPs(idps)) {
            throw new GenerarPeticionClaveException(
                    "No se ha indicado lista de IDPs válidos: " + idps);
        }

        final String idSesion = claveDao.crearSesion(pUrlCallback, idioma, idps,
                qaa, forceAuth);
        log.debug(
                " Creada sesion clave:  [idSesion = " + idSesion + "] [idps = "
                        + idps + "] [urlCallback = " + pUrlCallback + "]");
        return idSesion;
    }

    @Override
    @NegocioInterceptor
    public String crearLogoutSesionClave(final String pUrlCallback,
            final String idioma) {
        log.debug(" Crea logout sesion clave...");

        // TODO DAO SESION LOGOUT
        final String idSesion = claveDao.crearLogutSesion(pUrlCallback, idioma);
        // final String idSesion = "PENDIENTE";
        log.debug(" Creada logout sesion clave: " + idSesion);
        return idSesion;
    }

    /**
     * @see es.caib.loginib.core.ClaveService#generarPeticionClave(java.lang.String)
     */
    @Override
    @NegocioInterceptor
    public PeticionClave generarPeticionClave(final String idSesion) {

        log.debug(" Generar peticion clave: [idSesion = " + idSesion + "]");

        // Obtener datos sesion
        final DatosSesion datosSesion = claveDao.obtenerDatosSesion(idSesion);
        if (datosSesion.getFechaTicket() != null) {
            throw new GenerarPeticionClaveException(
                    "Sesion ya ha sido autenticada en clave [idSesion = "
                            + idSesion + "]");
        }
        final Date ahora = new Date();
        if (ahora.getTime() - datosSesion.getFechaInicioSesion()
                .getTime() > (config.getTimeoutSesionExterna()
                        * ConstantesNumero.N1000)) {
            throw new GenerarPeticionClaveException(
                    "Sesion ha expirado [idSesion = " + idSesion + "]");
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
        authnRequest.setProviderName(config.getProviderName());
        authnRequest.setForceAuthN(datosSesion.isForceAuth());

        if (datosSesion.getQaa() == null) {
            authnRequest.setQaa(Integer.parseInt(config.getQaa()));
        } else {
            authnRequest.setQaa(datosSesion.getQaa());
        }

        authnRequest.setPersonalAttributeList(pAttList);
        authnRequest.setAssertionConsumerServiceURL(
                (config.getReturnUrlExterna()) + "/" + idSesion + ".html");
        authnRequest.setSpSector(config.getSpSector());
        authnRequest.setSpApplication(config.getSpApplication());
        authnRequest.setSPID(config.getSpId());

        // Generamos peticion SAML
        final STORKSAMLEngine engine = STORKSAMLEngine.getInstance(SP_CONF);
        try {
            authnRequest = engine.generateSTORKAuthnRequest(authnRequest);
        } catch (final STORKSAMLEngineException e) {
            throw new GenerarPeticionClaveException(e);
        }

        // Pasamos a B64 y retornamos
        final byte[] token = authnRequest.getTokenSaml();
        final String samlRequestB64 = PEPSUtil.encodeSAMLToken(token);
        final String samlRequestXML = new String(token);
        log.debug(" Peticion generada [idSesion = " + idSesion + "]: "
                + samlRequestXML);

        // Extraemos saml id peticion y guardamos en sesion
        final String samlId = SamlUtil.extraerSamlId(samlRequestB64);
        if (StringUtils.isBlank(samlId)) {
            throw new GenerarPeticionClaveException(
                    "No se ha podido extraer SamlId de la peticion [idSesion = "
                            + idSesion + "]");
        }
        claveDao.establecerSamlIdPeticion(idSesion, samlId);

        // Devolvemos datos necesarios para invocar a Clave
        final PeticionClave peticionClave = new PeticionClave();
        peticionClave.setSamlRequestB64(samlRequestB64);
        peticionClave.setUrlClave(config.getPepsUrl());
        peticionClave.setIdioma(datosSesion.getIdioma());
        peticionClave.setIdps(datosSesion.getIdps());
        return peticionClave;
    }

    /**
     * @see es.caib.loginib.core.ClaveService#procesarRespuestaClave(java.lang.String,
     *      java.lang.String)
     */
    @Override
    @NegocioInterceptor
    public TicketClave procesarRespuestaClave(final String pIdSesion,
            final String pSamlResponseB64) {

        log.debug(" Procesando respuesta clave [idSesion = " + pIdSesion + "]");

        // Decodifica respuesta Clave
        STORKAuthnResponse authnResponse = null;
        // final IPersonalAttributeList personalAttributeList = null;
        final byte[] decSamlToken = PEPSUtil.decodeSAMLToken(pSamlResponseB64);
        final STORKSAMLEngine engine = STORKSAMLEngine.getInstance(SP_CONF);
        try {
            authnResponse = engine.validateSTORKAuthnResponse(decSamlToken, "");
        } catch (final STORKSAMLEngineException e) {
            throw new ErrorRespuestaClaveException(e);
        }

        // Verificamos si hay error al interpretar la respuesta
        if (authnResponse.isFail()) {
            log.debug("La respuesta indica que hay un error [idSesion = "
                    + pIdSesion + "]: " + authnResponse.getMessage());
            throw new ErrorRespuestaClaveException(
                    "La respuesta indica que hay un error : "
                            + authnResponse.getMessage());
        }

        // Verificamos que la peticion se corresponde a la sesion
        final DatosSesion datosSesion = claveDao.obtenerDatosSesion(pIdSesion);
        if (!StringUtils.equals(datosSesion.getSamlIdPeticion(),
                authnResponse.getInResponseTo())) {
            log.debug(
                    "La respuesta no se corresponde a la peticion SAML origen [idSesion = "
                            + pIdSesion + "]");
            throw new ErrorRespuestaClaveException(
                    "La respuesta no se corresponde a la peticion SAML origen");
        }

        // Obtenemos atributos
        final String idpClave = authnResponse.getAssertions().get(0).getIssuer()
                .getValue();
        log.debug(" Idp retornado de Clave [idSesion = " + pIdSesion + "]: "
                + idpClave);

        final String idp = ClaveLoginUtil.convertirAnteriorIDP(idpClave)
                .toUpperCase();
        log.debug(" Idp a devolver [idSesion = " + pIdSesion + "]: " + idp);

        if (!ClaveLoginUtil.validarIDP(idp)) {
            throw new ErrorRespuestaClaveException(
                    "Idp retornado no contemplado [idSesion = " + pIdSesion
                            + "]: " + idpClave);
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
            log.debug("La respuesta devuelve un nif no valido [idSesion = "
                    + pIdSesion + "]");
            throw new ErrorRespuestaClaveException(
                    "La respuesta devuelve un nif no valido: " + nifClave);
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
        // persona
        // juridica
        if (afirmaResponse != null) {
            final Map<String, String> infoCertificado = AFirmaUtil
                    .extraerInfoCertificado(afirmaResponse);
            final String clasificacion = infoCertificado.get("clasificacion");
            if ("11".equals(clasificacion) || "12".equals(clasificacion)) {
                nif = infoCertificado.get("NIF-CIF");
                nombre = infoCertificado.get("razonSocial");
                apellidos = null;
                apellido1 = null;
                apellido2 = null;
            }
        }

        // Almacenar en tabla y generar ticket sesion (OTP)
        log.debug(" Datos obtenidos clave [idSesion = " + pIdSesion
                + "]: Nivel=" + idp + ", Nif=" + nif + ", Nombre=" + nombre
                + " " + apellidos);

        final TicketClave respuesta = claveDao.generateTicket(pIdSesion, idp,
                nif, nombre, apellidos, apellido1, apellido2);

        log.debug(" Ticket generado [idSesion = " + pIdSesion + "]: "
                + respuesta.getTicket());

        return respuesta;
    }

    /**
     * @see es.caib.loginib.core.ClaveService#simularRespuestaClave(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @NegocioInterceptor
    public TicketClave simularRespuestaClave(final String pIdSesion,
            final String pIdp, final String pNif, final String pNombre,
            final String pApellidos, final String pApellido1,
            final String pApellido2) {

        // if (!isAccesoClaveSimulado()) {
        // throw new TicketNoValidoException("No se puede simular sesion.");
        // }

        return claveDao.generateTicket(pIdSesion, pIdp, pNif, pNombre,
                pApellidos, pApellido1, pApellido2);
    }

    /**
     * @see es.caib.loginib.core.ClaveService#isAccesoClaveDeshabilitado()
     */
    @Override
    @NegocioInterceptor
    public boolean isAccesoClaveDeshabilitado() {
        return config.isAccesoClaveDeshabilitado();
    }

    /**
     * @see es.caib.loginib.core.ClaveService#purgar()
     */
    @Override
    @NegocioInterceptor
    public void purgar() {
        // Procesos de login
        claveDao.purgaTicketSesionExterna(config.getTimeoutSesionExterna(),
                config.getTimeoutTicketExterna());
        // Procesos de logout
        claveDao.purgaTicketLogoutSesionExterna(
                config.getTimeoutSesionExterna(),
                config.getTimeoutTicketExterna());

    }

    /**
     * @see es.caib.loginib.core.ClaveService#obtenerDatosUsuarioAplicacionExterna(java.lang.String)
     */
    @Override
    @NegocioInterceptor
    public DatosUsuario obtenerDatosUsuarioAplicacionExterna(
            final String pTicket) {

        final DatosUsuario t = claveDao.consumirTicketSesionExterna(pTicket);

        // No existe ticket
        if (t == null || t.getFechaTicket() == null) {
            throw new TicketNoValidoException("No existe ticket");
        }

        // Ticket caducado
        if (t.getFechaTicket() != null && (new Date()).getTime() - t
                .getFechaTicket().getTime() > (config.getTimeoutTicketExterna()
                        * ConstantesNumero.N1000)) {
            throw new TicketNoValidoException("Ticket caducado");
        }

        // Devuelve datos usuario
        return t;
    }

    /**
     * @see es.caib.loginib.core.ClaveService#obtenerUrlInicioExterna(java.lang.String)
     */
    @Override
    @NegocioInterceptor
    public String obtenerUrlInicioExterna(final String pIdSesion) {
        try {
            return config.getInicioUrlExterna() + "?idSesion="
                    + URLEncoder.encode(pIdSesion, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            throw new ErrorNoControladoException(e);
        }
    }

    @Override
    @NegocioInterceptor
    public String obtenerUrlLogoutExterna(final String pIdSesion) {
        try {
            return config.getInicioLogoutExterna() + "?idSesion="
                    + URLEncoder.encode(pIdSesion, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            throw new ErrorNoControladoException(e);
        }
    }

    /**
     * @see es.caib.loginib.core.ClaveService#isAccesoClaveSimulado()
     */
    @Override
    @NegocioInterceptor
    public boolean isAccesoClaveSimulado() {
        return config.isAccesoClaveSimulado();
    }

    /**
     * @see es.caib.loginib.core.ClaveService#generarPeticioLogout()
     */
    @Override
    @NegocioInterceptor
    public PeticionClaveLogout generarPeticionLogout(final String idSesion) {
        log.debug(" Generar peticion logout... ");

        // TODO DAO PARA SESION LOGOUT (DatosSesionLogout)
        final DatosLogoutSesion datosSesion = claveDao
                .obtenerDatosSesionLogout(idSesion);
        if (datosSesion.getFechaTicket() != null) {
            throw new GenerarPeticionClaveException(
                    "sesion ya ha sido autenticada en clave");
        }

        // Parametros peticion
        final STORKLogoutRequest req = new STORKLogoutRequest();
        req.setDestination(config.getPepsLogout());
        req.setSpProvidedId(config.getProviderName());
        req.setIssuer(
                config.getReturnLogoutUrlExterna() + "/" + idSesion + ".html");

        // Generamos peticion SAML
        final STORKSAMLEngine engine = STORKSAMLEngine.getInstance(SP_CONF);
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
            throw new GenerarPeticionClaveException(
                    "No se ha podido extraer SamlId de la peticion");
        }
        claveDao.establecerSamlIdLogoutPeticion(idSesion, samlId);

        // Devolvemos datos necesarios para invocar a Clave
        final PeticionClaveLogout peticionClave = new PeticionClaveLogout();
        peticionClave.setSamlRequestB64(samlRequestB64);
        peticionClave.setUrlClave(config.getPepsLogout());
        return peticionClave;
    }

    /**
     * @see es.caib.loginib.core.ClaveService#procesarRespuestaLogout()
     */
    @Override
    @NegocioInterceptor
    public RespuestaClaveLogout procesarRespuestaLogout(final String pIdSesion,
            final String pSamlResponseB64) {

        log.debug(" Procesando respuesta clave logout");

        // TODO DAO PARA SESION LOGOUT (DatosSesionLogout)
        final DatosLogoutSesion datosSesion = claveDao
                .obtenerDatosSesionLogout(pIdSesion);
        // TODO Recoger de DAO
        final String urlCallback = datosSesion.getUrlCallback();
        final String samlIdPeticion = datosSesion.getSamlIdPeticion();

        // Decodifica respuesta Clave
        final byte[] decSamlToken = PEPSUtil.decodeSAMLToken(pSamlResponseB64);
        final STORKSAMLEngine engine = STORKSAMLEngine.getInstance(SP_CONF);
        STORKLogoutResponse logoutReq = null;
        try {
            logoutReq = engine.validateSTORKLogoutResponse(decSamlToken, null);
        } catch (final STORKSAMLEngineException e) {
            throw new ErrorRespuestaClaveException(e);
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
            if (!StringUtils.equals(samlIdPeticion,
                    logoutReq.getInResponseTo())) {
                throw new ErrorRespuestaClaveException(
                        "La respuesta no se corresponde a la peticion SAML origen");
            }

            respuesta = new RespuestaClaveLogout();
            respuesta.setLogout(true);
            respuesta.setUrlCallback(urlCallback);

        }

        log.debug("Logout realizado: " + respuesta.isLogout());

        return respuesta;

    }

}
