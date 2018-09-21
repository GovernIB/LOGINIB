package es.caib.loginib.frontend.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.caib.loginib.core.api.exception.ErrorRespuestaClaveException;
import es.caib.loginib.core.api.exception.ServiceException;
import es.caib.loginib.core.api.model.login.DatosSesion;
import es.caib.loginib.core.api.model.login.PeticionClave;
import es.caib.loginib.core.api.model.login.PeticionClaveLogout;
import es.caib.loginib.core.api.model.login.RespuestaClaveLogout;
import es.caib.loginib.core.api.model.login.SimularClave;
import es.caib.loginib.core.api.model.login.TicketClave;
import es.caib.loginib.core.api.model.login.types.TypeIdp;
import es.caib.loginib.core.api.service.ClaveService;
import es.caib.loginib.core.api.util.ClaveLoginUtil;
import es.caib.loginib.frontend.model.DatosInicioSesionClave;
import es.caib.loginib.frontend.model.DatosLogoutClave;
import es.caib.loginib.frontend.model.DatosRetornoClave;
import es.caib.loginib.frontend.model.DatosSeleccionAutenticacion;
import es.caib.loginib.frontend.model.ErrorCodes;

/**
 * Inicia sesion en clave.
 *
 * @author Indra
 *
 */
@Controller
public final class SesionClaveController {

    /** Servicio Clave. */
    @Resource(name = "claveService")
    private ClaveService claveService;

    /** Log. */
    private final Logger log = LoggerFactory
            .getLogger(SesionClaveController.class);

    /** Mensaje error particularizado usuario. */
    private static final String ERROR_MESSAGE_USER = "ERROR_MESSAGE_USER";

    /**
     * Muestra pagina de bienvenida.
     *
     * @return pagina bienvenida para test
     */
    @RequestMapping("/index.html")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    /**
     * Muestra página de selección de autenticación.
     *
     * @return página de selección de autenticación.
     */
    @RequestMapping("/seleccionAutenticacion.html")
    public ModelAndView seleccionAutenticacion(
            @RequestParam("idSesion") final String idSesion) {
        final DatosSesion sesion = claveService
                .obtenerDatosSesionLogin(idSesion);
        final DatosSeleccionAutenticacion datos = new DatosSeleccionAutenticacion();
        datos.setIdSesion(idSesion);
        datos.setIdioma(sesion.getIdioma());
        datos.setClave(ClaveLoginUtil.permiteAccesoClave(sesion.getIdps()));
        datos.setAnonimo(ClaveLoginUtil.permiteAccesoAnonimo(sesion.getIdps()));
        return new ModelAndView("seleccionAutenticacion", "datos", datos);
    }

    /**
     * Login anónimo.
     *
     * @return retorna página callback.
     */
    @RequestMapping("/loginAnonimo.html")
    public ModelAndView loginAnonimo(
            @RequestParam("idSesion") final String idSesion) {

        log.debug("Login anonimo");
        final TicketClave ticket = claveService.loginAnonimo(idSesion);

        // Retornamos aplicacion
        log.debug("Retornamos a aplicacion: ticket = " + ticket.getTicket());
        final DatosRetornoClave drc = new DatosRetornoClave();
        drc.setTicket(ticket.getTicket());
        drc.setUrlCallbackLogin(ticket.getUrlCallback());
        drc.setIdioma(ticket.getIdioma());
        return new ModelAndView("retornoClave", "datos", drc);

    }

    /**
     * Recibe peticion de inicio de sesion en Clave y redirige a Clave.
     *
     * @param idSesion
     *            idSesion
     * @return pagina que realiza la redireccion a Clave
     */
    @RequestMapping(value = "/redirigirLoginClave.html")
    public ModelAndView redirigirLoginClave(
            @RequestParam("idSesion") final String idSesion) {

        log.debug("Iniciar sesion firma");

        // Comprobamos si se ha deshabilitado
        if (claveService.isAccesoClaveDeshabilitado()) {
            // Mostramos pagina generica de error
            return new ModelAndView("redirect:/error.html?code="
                    + ErrorCodes.CLAVE_DESHABILITADO.toString());
        }

        // Vemos si simulamos clave
        if (claveService.isAccesoClaveSimulado()) {
            final SimularClave simularClave = new SimularClave();
            simularClave.setIdSesion(idSesion);
            return new ModelAndView("simularClave", "simularClave",
                    simularClave);
        }

        // Generamos request SAML
        final PeticionClave peticionClave = claveService
                .generarPeticionLoginClave(idSesion);

        // Iniciar sesion en clave
        // TODO CLAVE2 DatosInicioSesionClave no se puede eliminar y usar
        // directamente PeticionClave
        final DatosInicioSesionClave isc = new DatosInicioSesionClave();
        isc.setIdps(ClaveLoginUtil
                .traduceIdpListToIdpClave(peticionClave.getIdps()));
        isc.setSamlRequest(peticionClave.getSamlRequestB64());
        isc.setUrlClave(peticionClave.getUrlClave());
        isc.setRelayState(peticionClave.getRelayState());
        isc.setIdioma(peticionClave.getIdioma());

        log.debug("Redirigimos a clave");

        return new ModelAndView("inicioSesionClave", "datos", isc);

    }

    /**
     * Recibe peticion de logout de sesion en Clave y redirige a Clave.
     *
     * @param idSesion
     *            idSesion
     * @return pagina que realiza la redireccion a Clave
     */
    @RequestMapping(value = "/redirigirLogoutClave.html")
    public ModelAndView redirigirLogoutClave(
            @RequestParam("idSesion") final String idSesion) {

        log.debug("Iniciar logout sesion clave");

        // Comprobamos si se ha deshabilitado
        if (claveService.isAccesoClaveDeshabilitado()) {
            // Mostramos pagina generica de error
            return new ModelAndView("redirect:/error.html?code="
                    + ErrorCodes.CLAVE_DESHABILITADO.toString());
        }

        // Vemos si simulamos clave
        if (claveService.isAccesoClaveSimulado()) {
            // TODO PENDIENTE
            throw new RuntimeException("PENDIENTE LOGOUT PARA SIMULADO");
        }

        // Generamos request SAML
        final PeticionClaveLogout peticionClave = claveService
                .generarPeticionLogoutClave(idSesion);

        // Iniciar logout sesion en clave
        final DatosInicioSesionClave isc = new DatosInicioSesionClave();
        isc.setSamlRequest(peticionClave.getSamlRequestB64());
        isc.setUrlClave(peticionClave.getUrlClave());

        log.debug("Redirigimos a clave");

        return new ModelAndView("logoutSesionClave", "datos", isc);

    }

    /**
     * Retorno de Clave y vuelta a aplicacion externa.
     *
     * @param idSesion
     *            idSesion.
     * @param samlResponse
     *            samlResponse.
     * @return pagina que realiza la redireccion a aplicacion externa tras el
     *         login en Clave
     */
    @RequestMapping(value = "/retornoLoginClave/{idSesion}.html", method = RequestMethod.POST)
    public ModelAndView retornoLoginClave(
            @PathVariable("idSesion") final String idSesion,
            @RequestParam("SAMLResponse") final String samlResponse) {
        return retornoClave(idSesion, samlResponse);
    }

    /**
     * Retorno de logout Clave y vuelta a aplicacion externa.
     *
     * @param idSesion
     *            idSesion.
     * @param samlResponse
     *            samlResponse.
     * @return pagina que realiza la redireccion a aplicacion externa tras el
     *         login en Clave
     */
    @RequestMapping(value = "/retornoLogoutClave/{idSesion}.html", method = RequestMethod.POST)
    public ModelAndView retornoLogoutClave(
            @PathVariable("idSesion") final String idSesion,
            @RequestParam("samlResponseLogout") final String samlResponse) {

        final RespuestaClaveLogout resp = claveService
                .procesarRespuestaLogoutClave(idSesion, samlResponse);

        // Retornamos aplicacion
        log.debug("Retornamos a aplicacion: logout = " + resp.isLogout());
        final DatosLogoutClave drc = new DatosLogoutClave();
        drc.setLogout(resp.isLogout());
        drc.setUrlCallback(resp.getUrlCallback());
        return new ModelAndView("retornoLogoutClave", "datos", drc);

    }

    /**
     * Simulamos acceso clave.
     *
     * @param idSesion
     *            idSesion
     * @param idp
     *            idp
     * @param nif
     *            nif
     * @param nombre
     *            nombre
     * @param apellidos
     *            apellidos
     * @return retorno aplicacion
     */
    @RequestMapping(value = "loginClaveSimulado.html", method = RequestMethod.POST)
    public ModelAndView loginClaveSimulado(
            @RequestParam("idSesion") final String idSesion,
            @RequestParam("idp") final String idp,
            @RequestParam("nif") final String nif,
            @RequestParam("nombre") final String nombre,
            @RequestParam("apellidos") final String apellidos,
            @RequestParam("apellido1") final String apellido1,
            @RequestParam("apellido2") final String apellido2) {

        log.debug("Retorno clave simulado: id sesion = " + idSesion);

        // Generamos ticket autenticacion
        final TicketClave ticket = claveService.simularRespuestaClave(idSesion,
                TypeIdp.fromString(idp), nif, nombre, apellidos, apellido1,
                apellido2);

        // Retornamos aplicacion
        log.debug("Retornamos a aplicacion: ticket = " + ticket.getTicket());
        final DatosRetornoClave drc = new DatosRetornoClave();
        drc.setTicket(ticket.getTicket());
        drc.setUrlCallbackLogin(ticket.getUrlCallback());
        drc.setIdioma(ticket.getIdioma());

        return new ModelAndView("retornoClave", "datos", drc);
    }

    /**
     * Retorno de Clave.
     *
     * @param idSesion
     *            idSesion.
     * @param samlResponse
     *            samlResponse.
     * @return pagina que realiza la redireccion a la aplicacion
     */
    private ModelAndView retornoClave(final String idSesion,
            final String samlResponse) {
        log.debug("Retorno clave: id sesion = " + idSesion);

        // Generamos ticket autenticacion
        final TicketClave ticket = claveService
                .procesarRespuestaLoginClave(idSesion, samlResponse);

        // Retornamos aplicacion
        log.debug("Retornamos a aplicacion: ticket = " + ticket.getTicket());
        final DatosRetornoClave drc = new DatosRetornoClave();
        drc.setTicket(ticket.getTicket());
        drc.setUrlCallbackLogin(ticket.getUrlCallback());
        drc.setIdioma(ticket.getIdioma());
        return new ModelAndView("retornoClave", "datos", drc);
    }

    /**
     * Muestra error.
     *
     * @param errorCode
     *            codigo error
     * @return pagina error
     */
    @RequestMapping("/error.html")
    public ModelAndView error(@RequestParam("code") final String errorCode,
            final HttpServletRequest request) {

        // Mostramos pagina generica de error
        ErrorCodes error = ErrorCodes.fromString(errorCode);
        String view = "errorGeneral";
        if (error == null) {
            error = ErrorCodes.ERROR_GENERAL;
        }
        String errorMsg = error.toString();

        // En caso de existir error particular, mostramos mensaje particular
        if (request.getSession().getAttribute(ERROR_MESSAGE_USER) != null) {
            view = "errorDetalle";
            errorMsg = (String) request.getSession()
                    .getAttribute(ERROR_MESSAGE_USER);
        }

        return new ModelAndView(view, "mensaje", errorMsg);

    }

    /**
     * Handler de excepciones de negocio.
     *
     * @param pex
     *            Excepción
     * @param request
     *            Request
     * @return Respuesta JSON indicando el mensaje producido
     */
    @ExceptionHandler({Exception.class})
    public ModelAndView handleServiceException(final Exception pex,
            final HttpServletRequest request) {

        // Si no es una excepcion de negocio, generamos log
        if (!(pex instanceof ServiceException)) {
            log.error("Excepcion en capa front: " + pex.getMessage(), pex);
        }

        // Si es una excepcion al procesar la respuesta de Clave, mostramos
        // detalle al usuario
        String errorMessage = null;
        if (pex instanceof ErrorRespuestaClaveException) {
            errorMessage = pex.getMessage();
        }
        request.getSession().setAttribute(ERROR_MESSAGE_USER, errorMessage);

        // Mostramos pagina generica de error
        return new ModelAndView("redirect:/error.html?code="
                + ErrorCodes.ERROR_GENERAL.toString());
    }

}
