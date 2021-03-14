package es.caib.loginib.rest.v1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.caib.loginib.core.api.model.login.DatosAutenticacion;
import es.caib.loginib.core.api.model.login.EvidenciasAutenticacion;
import es.caib.loginib.core.api.model.login.PropiedadAutenticacion;
import es.caib.loginib.core.api.service.LoginService;
import es.caib.loginib.core.api.util.ClaveLoginUtil;
import es.caib.loginib.rest.api.v1.RDatosAutenticacion;
import es.caib.loginib.rest.api.v1.RDatosRepresentante;
import es.caib.loginib.rest.api.v1.REvidenciasAutenticacion;
import es.caib.loginib.rest.api.v1.RLoginParams;
import es.caib.loginib.rest.api.v1.RLogoutParams;
import es.caib.loginib.rest.api.v1.RParamApp;
import es.caib.loginib.rest.api.v1.RParamsApp;
import es.caib.loginib.rest.api.v1.RPropiedad;
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
	private LoginService loginService;

	/**
	 * Login.
	 *
	 * @return Url redirección Clave
	 */
	@ApiOperation(value = "Login", notes = "Login")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody(required = false) final RLoginParams parametros) {

		final Map<String, String> params = getParams(parametros.getParamsApp());
		// Creamos sesion
		final String idSesion = loginService.iniciarSesionLogin(parametros.getEntidad(), parametros.getUrlCallback(),
				parametros.getUrlCallbackError(), parametros.getIdioma(),
				ClaveLoginUtil.convertToListIdps(parametros.getMetodosAutenticacion()), parametros.getQaa(),
				parametros.isInicioClaveAutomatico(), parametros.isForzarAutenticacion(), parametros.getAplicacion(),
				parametros.isAuditar(), params);

		// Obtenemos url redireccion inicio sesion
		final String url = loginService.obtenerUrlRedireccionLoginClave(idSesion, parametros.getIdioma());

		return url;

	}

	/**
	 * Convierte el RParamsApp a Map.
	 *
	 * @param paramsApp
	 * @return
	 */
	private Map<String, String> getParams(final RParamsApp paramsApp) {
		Map<String, String> params = null;
		if (paramsApp != null && paramsApp.getParametros() != null && !paramsApp.getParametros().isEmpty()) {
			params = new LinkedHashMap<>();
			for (final RParamApp param : paramsApp.getParametros()) {
				params.put(param.getPropiedad(), param.getValor());
			}
		}
		return params;
	}

	/**
	 * Recupera datos ticket.
	 *
	 * @param ticket ticket
	 * @return datos ticket
	 */
	@ApiOperation(value = "Obtiene datos autenticación a partir del ticket", notes = "Obtiene datos autenticación a partir del ticket", response = RDatosAutenticacion.class)
	@RequestMapping(value = "/ticket/{ticket}", method = RequestMethod.GET)
	public RDatosAutenticacion obtenerDatosTicket(@PathVariable("ticket") final String ticket) {
		final DatosAutenticacion du = loginService.obtenerDatosAutenticacion(ticket);
		final RDatosAutenticacion datos = new RDatosAutenticacion();
		datos.setIdSesion(du.getIdSesion());
		datos.setParamsApp(du.getParamsApp());
		if (du.getMetodoAutenticacion() != null) {
			datos.setMetodoAutenticacion(du.getMetodoAutenticacion().toString());
		}
		if (du.getQaa() != null) {
			datos.setQaa(du.getQaa().toString());
		}
		if (du.getAutenticado() != null) {
			datos.setNif(du.getAutenticado().getNif());
			datos.setNombre(du.getAutenticado().getNombre());
			datos.setApellidos(du.getAutenticado().getApellidos());
			datos.setApellido1(du.getAutenticado().getApellido1());
			datos.setApellido2(du.getAutenticado().getApellido2());
		}
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
	@ApiOperation(value = "Realiza logout en Cl@ve", notes = "Realiza logout en Cl@ve")
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(@RequestBody(required = false) final RLogoutParams parametros) {

		// Creamos sesion
		final String idSesion = loginService.iniciarSesionLogout(parametros.getEntidad(), parametros.getUrlCallback(),
				parametros.getIdioma(), parametros.getAplicacion());

		// Obtenemos url redireccion inicio sesion
		final String url = loginService.obtenerUrlRedireccionLogoutClave(idSesion);

		return url;

	}

	/**
	 * Obtener evidencias autenticación.
	 *
	 * @return obtiene evidencias autenticación-
	 */
	@ApiOperation(value = "Obtener evidencias autenticación", notes = "Obtener evidencias autenticación", response = REvidenciasAutenticacion.class)
	@RequestMapping(value = "/evidencias/{idSesion}", method = RequestMethod.GET)
	public REvidenciasAutenticacion obtenerEvidenciasAutenticacion(@PathVariable("idSesion") final String idSesion) {
		// Consulta evidencias
		final EvidenciasAutenticacion evidencias = loginService.obtenerEvidenciasSesionLogin(idSesion);
		// Retorna evidencias
		final REvidenciasAutenticacion datos = new REvidenciasAutenticacion();
		if (evidencias != null && evidencias.getEvidenciasLista() != null
				&& evidencias.getEvidenciasLista().getPropiedades() != null
				&& evidencias.getEvidenciasLista().getPropiedades().size() > 0) {
			final List<RPropiedad> rEvidencias = new ArrayList<>();
			for (final PropiedadAutenticacion p : evidencias.getEvidenciasLista().getPropiedades()) {
				final RPropiedad rp = new RPropiedad();
				rp.setPropiedad(p.getPropiedad());
				rp.setTipo(p.getTipo().toString());
				rp.setValor(p.getValor());
				rp.setMostrar(p.isMostrar());
				rEvidencias.add(rp);
			}
			datos.setEvidencias(rEvidencias);
			datos.setHuellaElectronica(evidencias.getEvidenciasHash());
		}
		return datos;
	}

}
