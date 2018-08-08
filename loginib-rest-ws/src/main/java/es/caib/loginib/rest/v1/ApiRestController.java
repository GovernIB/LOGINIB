package es.caib.loginib.rest.v1;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.caib.loginib.core.api.exception.ApiRestException;
import es.caib.loginib.core.api.model.login.DatosUsuario;
import es.caib.loginib.core.api.service.ClaveService;
import es.caib.loginib.core.api.util.ClaveLoginUtil;
import es.caib.loginib.rest.api.util.JsonException;
import es.caib.loginib.rest.api.util.JsonUtil;
import es.caib.loginib.rest.api.v1.RDatosAutenticacion;
import es.caib.loginib.rest.api.v1.RDatosRepresentante;
import es.caib.loginib.rest.api.v1.RLoginParams;
import es.caib.loginib.rest.api.v1.RLogoutParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Operaciones login.
 *
 * @author Indra
 *
 */
@RestController
@RequestMapping("/v1")
@Api(value = "v1", produces = "application/json")
public class ApiRestController {

    /** Servicio negocio. */
    @Autowired
    private ClaveService claveService;

    /**
     * Login.
     *
     * @return Url redirección Clave
     */
    @ApiOperation(value = "Login", notes = "Login", response = RLoginParams.class)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(
            @RequestParam(name = "parametros", required = false) String parametrosJSON) {

        // Decodificamos parametros
        RLoginParams pars = null;
        if (StringUtils.isNotBlank(parametrosJSON)) {
            try {
                pars = (RLoginParams) JsonUtil.fromJson(parametrosJSON,
                        RLoginParams.class);
            } catch (final JsonException e) {
                throw new ApiRestException(
                        "Error interpretando parametros en JSON", e);
            }
        }

        // Creamos sesion
        final String idSesion = claveService.iniciarLoginClave(
                pars.getEntidad(), pars.getUrlCallback(), pars.getIdioma(),
                ClaveLoginUtil
                        .convertToListIdps(pars.getMetodosAutenticacion()),
                pars.getQaa(), pars.isForzarAutenticacion());

        // Obtenemos url redireccion inicio sesion
        final String url = claveService
                .obtenerUrlRedireccionLoginClave(idSesion);

        return url;

    }

    /**
     * Recupera datos ticket.
     *
     * @param ticket
     *            ticket
     * @return datos ticket
     */
    @ApiOperation(value = "Obtiene datos autenticación a partir del ticket", notes = "Obtiene datos autenticación a partir del ticket", response = RDatosAutenticacion.class)
    @RequestMapping(value = "/ticket/{ticket}", method = RequestMethod.GET)
    public RDatosAutenticacion obtenerDatosTicket(
            @PathVariable("ticket") String ticket) {

        final DatosUsuario du = claveService.obtenerDatosAutenticacion(ticket);

        final RDatosAutenticacion datos = new RDatosAutenticacion();
        datos.setMetodoAutenticacion(du.getNivelAutenticacion());
        datos.setNif(du.getNif());
        datos.setNombre(du.getNombre());
        datos.setApellidos(du.getApellidos());
        datos.setApellido1(du.getApellido1());
        datos.setApellido2(du.getApellido2());

        if (du.getRepresentante() != null) {
            final RDatosRepresentante dr = new RDatosRepresentante();
            dr.setNif(du.getRepresentante().getNif());
            dr.setNombre(du.getRepresentante().getNombre());
            dr.setApellidos(du.getRepresentante().getApellidos());
            dr.setApellido1(du.getRepresentante().getApellido1());
            dr.setApellido2(du.getRepresentante().getApellido2());
            datos.setRepresentante(dr);
        }

        return datos;

    }

    /**
     * Logout.
     *
     * @return Url redirección Clave
     */
    @ApiOperation(value = "Logout", notes = "Logout", response = RLogoutParams.class)
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(
            @RequestParam(name = "parametros", required = false) String parametrosJSON) {

        // Decodificamos parametros
        RLogoutParams pars = null;
        if (StringUtils.isNotBlank(parametrosJSON)) {
            try {
                pars = (RLogoutParams) JsonUtil.fromJson(parametrosJSON,
                        RLogoutParams.class);
            } catch (final JsonException e) {
                throw new ApiRestException(
                        "Error interpretando parametros en JSON", e);
            }
        }

        // Creamos sesion
        final String idSesion = claveService.iniciarLogoutClave(
                pars.getEntidad(), pars.getUrlCallback(), pars.getIdioma());

        // Obtenemos url redireccion inicio sesion
        final String url = claveService
                .obtenerUrlRedireccionLogoutClave(idSesion);

        return url;

    }

}
