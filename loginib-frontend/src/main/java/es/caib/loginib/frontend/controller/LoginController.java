package es.caib.loginib.frontend.controller;

import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.caib.loginib.core.api.exception.NoDesgloseException;
import es.caib.loginib.core.api.exception.ServiceException;
import es.caib.loginib.core.api.exception.ValidateClaveException;
import es.caib.loginib.core.api.exception.ValidateLoginException;
import es.caib.loginib.core.api.model.login.DatosAutenticacion;
import es.caib.loginib.core.api.model.login.DatosPersona;
import es.caib.loginib.core.api.model.login.DatosSesion;
import es.caib.loginib.core.api.model.login.DesgloseApellidos;
import es.caib.loginib.core.api.model.login.PersonalizacionEntidad;
import es.caib.loginib.core.api.model.login.PeticionClave;
import es.caib.loginib.core.api.model.login.PeticionClaveLogout;
import es.caib.loginib.core.api.model.login.RespuestaClaveLogout;
import es.caib.loginib.core.api.model.login.RespuestaError;
import es.caib.loginib.core.api.model.login.SesionLogin;
import es.caib.loginib.core.api.model.login.SimularClave;
import es.caib.loginib.core.api.model.login.TicketClave;
import es.caib.loginib.core.api.model.login.TicketDesglose;
import es.caib.loginib.core.api.model.login.ValidacionUsuarioPassword;
import es.caib.loginib.core.api.model.login.types.TypeIdp;
import es.caib.loginib.core.api.service.DesgloseApellidosService;
import es.caib.loginib.core.api.service.LoginService;
import es.caib.loginib.frontend.model.DatosInicioSesionClave;
import es.caib.loginib.frontend.model.DatosLogoutClave;
import es.caib.loginib.frontend.model.DatosRetornoClave;
import es.caib.loginib.frontend.model.DatosSeleccionAutenticacion;
import es.caib.loginib.frontend.model.ErrorCodes;
import es.caib.loginib.frontend.model.ModuleConfig;

/**
 * Controller login.
 *
 * @author Indra
 *
 */
@Controller
public final class LoginController {

	/** Servicio Clave. */
	@Resource(name = "loginService")
	private LoginService loginService;

	/** Servicio Desglose. */
	@Resource(name = "desgloseService")
	private DesgloseApellidosService desgloseService;

	@Autowired
	private ModuleConfig moduleConfig;

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(LoginController.class);

	/** Detalle error. */
	private static final String ERROR_DETALLE = "ERROR_DETALLE";

	/** Sesion ID . **/
	private static final String ERROR_ID_SESION = "ERROR_ID_SESION";

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
	 * Muestra página con toda la información propia del usuario.
	 *
	 * @return página de info.
	 */
	@RequestMapping("/test.html")
	public ModelAndView test(@RequestParam(value = "idioma", required = false) final String idioma,
			@RequestParam(value = "entidad", required=false) final String entidad,
			@RequestParam(value = "qaa", required=false) final Integer qaa) {

		// Generamos el id sesion
		String idSesion = loginService.iniciarSesionTest(idioma, entidad, qaa, "desgloseCertificado.html", false);
		return new ModelAndView("redirect:seleccionAutenticacion.html?idSesion=" + idSesion);
	}

	/**
	 * Forzar el desglose en la autenticacion.
	 *
	 * @return página de desglose datos.
	 */
	@RequestMapping("/forzarDesglose.html")
	public ModelAndView forzarDesglose(@RequestParam(value = "idioma", required = false) final String idioma,
			@RequestParam(value = "entidad", required=false) final String entidad,
			@RequestParam(value = "qaa", required=false) final Integer qaa) {

		// Generamos el id sesion
		String idSesion = loginService.iniciarSesionTest(idioma, entidad, qaa,
				"retornoClaveSimulado/forzarDesgloseRetorno.html", true);
		return new ModelAndView("redirect:seleccionAutenticacion.html?idSesion=" + idSesion);
	}

	/**
	 * Forzar el desglose en la autenticacion.
	 *
	 * @return página de desglose datos.
	 */
	@RequestMapping("/retornoClaveSimulado/forzarDesgloseRetorno.html")
	public ModelAndView forzarDesgloseRetorno(@RequestParam(value = "ticket", required = false) final String ticket,
			@RequestParam(value = "idioma", required = false) final String idioma) {

		// Generamos el id sesion
		final DatosAutenticacion datos = loginService.obtenerDatosAutenticacionAll(ticket);
		final PersonalizacionEntidad personalizacion = this.loginService
				.obtenerDatosPersonalizacionEntidad(datos.getIdSesion());

		DesgloseApellidos desglose = desgloseService.loadDesglose(datos.getAutenticado().getNif());
		ModelMap map = new ModelMap();
		if (desglose != null) {
			// Si se fuerza y ya existe desglose, pasar los datos ya almacenados.
			map.addAttribute("nombre", desglose.getNombre());
			map.addAttribute("apellido1", desglose.getApellido1());
			map.addAttribute("apellido2", desglose.getApellido2());
			map.addAttribute("nif", datos.getAutenticado().getNif());
			map.addAttribute("nombreDef", datos.getAutenticado().getNombre());
			map.addAttribute("apellido1Def", datos.getAutenticado().getApellido1());
			map.addAttribute("apellido2Def", datos.getAutenticado().getApellido2());
			map.addAttribute("callback", "desgloseNombre.html");
			map.addAttribute("ticket", ticket);

		} else {
			map.addAttribute("nombre", datos.getAutenticado().getNombre());
			map.addAttribute("apellido1", datos.getAutenticado().getApellido1());
			map.addAttribute("apellido2", datos.getAutenticado().getApellido2());
			map.addAttribute("nif", datos.getAutenticado().getNif());
			map.addAttribute("nombreDef", datos.getAutenticado().getNombre());
			map.addAttribute("apellido1Def", datos.getAutenticado().getApellido1());
			map.addAttribute("apellido2Def", datos.getAutenticado().getApellido2());
			map.addAttribute("callback", "desgloseNombre.html");
			map.addAttribute("ticket", ticket);
		}
		map.addAttribute("personalizacion", personalizacion);

		ModelAndView model = new ModelAndView("desgloseApellidos", map);
		return model;
	}

	/**
	 * Muestra página de selección de autenticación.
	 *
	 * @return página de selección de autenticación.
	 */
	@RequestMapping("/seleccionAutenticacion.html")
	public ModelAndView seleccionAutenticacion(@RequestParam("idSesion") final String idSesion) {

		final DatosSesion sesion = loginService.obtenerDatosSesionLogin(idSesion);
		final DatosSeleccionAutenticacion datos = new DatosSeleccionAutenticacion();
		datos.setIdSesion(idSesion);
		datos.setIdioma(sesion.getSesion().getIdioma());
		datos.setClave(sesion.getAccesosPermitidos().isAccesoClave());
		datos.setAnonimo(sesion.getAccesosPermitidos().isAccesoAnonimo());
		datos.setClientCert(sesion.getAccesosPermitidos().isAccesoClientCert());
		datos.setUsuarioPassword(sesion.getAccesosPermitidos().isAccesoUsuarioPassword());
		datos.setClientCertSegundoPlano(sesion.getAccesosPermitidos().isAccesoClientCertLink());
		datos.setPersonalizacion(sesion.getPersonalizacionEntidad());
		datos.setVersion(moduleConfig.getVersion());
		if (moduleConfig.getCommitGit() != null && !moduleConfig.getCommitGit().isEmpty()
				&& !moduleConfig.getCommitGit().equals("${git.commit.id.abbrev}")) {
			datos.setCommit(moduleConfig.getCommitGit());
		} else {
			datos.setCommit(moduleConfig.getCommitSvn());
		}

		ModelAndView modelAndView = null;

		// Si esta activado anonimo auto redirigimos si solo existe idp anonimo
		if (sesion.getAccesosPermitidos().isAccesoAnonimoAuto()) {
			modelAndView = new ModelAndView("redirect:loginAnonimo.html?idSesion=" + idSesion);
		} else {
			// Si se indica por parametro inicio de Cl@ve automatico: se redirige a Cl@ve
			if (sesion.getSesion().isIniClaAuto()) {
				modelAndView = new ModelAndView("redirect:redirigirLoginClave.html?idSesion=" + idSesion);
			} else {
				modelAndView = new ModelAndView("seleccionAutenticacion", "datos", datos);
			}
		}
		return modelAndView;
	}

	/**
	 * Login client-cert.
	 *
	 * @return client-cert
	 */
	@RequestMapping("/client-cert/login.html")
	public ModelAndView loginClientCert(@RequestParam("idSesion") final String idSesion,
			final HttpServletRequest request) {

		// Recupera datos de la request
		final String ipAddress = request.getRemoteAddr();
		final Map<String, String> headers = extractHeaders(request);
		final X509Certificate certificado = extractCertificate(request);

		// Realiza login
		final TicketClave ticket = loginService.loginClientCert(idSesion, headers, certificado, ipAddress);

		// Retornamos aplicacion
		log.debug("Retornamos a aplicacion: ticket = " + ticket.getTicket());
		final DatosRetornoClave drc = new DatosRetornoClave();
		drc.setTicket(ticket.getTicket());
		drc.setUrlCallbackLogin(ticket.getUrlCallback());
		drc.setIdioma(ticket.getIdioma());
		return new ModelAndView("retornoClave", "datos", drc);

	}

	/**
	 * Login anónimo.
	 *
	 * @return retorna página callback.
	 */
	@RequestMapping("/loginAnonimo.html")
	public ModelAndView loginAnonimo(@RequestParam("idSesion") final String idSesion,
			final HttpServletRequest request) {

		// Recupera datos de la request
		final String ipAddress = request.getRemoteAddr();
		final Map<String, String> headers = extractHeaders(request);

		log.debug("Login anonimo");
		final TicketClave ticket = loginService.loginAnonimo(idSesion, headers, ipAddress);

		// Retornamos aplicacion
		log.debug("Retornamos a aplicacion: ticket = " + ticket.getTicket());
		final DatosRetornoClave drc = new DatosRetornoClave();
		drc.setTicket(ticket.getTicket());
		drc.setUrlCallbackLogin(ticket.getUrlCallback());
		drc.setIdioma(ticket.getIdioma());
		return new ModelAndView("retornoClave", "datos", drc);

	}

	/**
	 * Redirige a página para hacer login por usuario/password.
	 *
	 * @param idSesion idSesion
	 * @return pagina que realiza la redireccion a Clave
	 */
	@RequestMapping(value = "/loginUsuarioPassword.html")
	public ModelAndView loginUsuarioPassword(@RequestParam("idSesion") final String idSesion,
			@RequestParam("usuario") final String usuario, @RequestParam("password") final String password,
			final HttpServletRequest request) {
		log.debug("Redirige a login usuario password");

		// Recupera datos de la request
		final String ipAddress = request.getRemoteAddr();
		final Map<String, String> headers = extractHeaders(request);

		final ValidacionUsuarioPassword validacion = loginService.loginUsuarioPassword(idSesion, usuario, password,
				headers, ipAddress);

		ModelAndView modelAndView = null;
		if (validacion.isUsuarioValido()) {
			// Retornamos aplicacion
			final TicketClave ticket = validacion.getTicket();
			log.debug("Retornamos a aplicacion: ticket = " + ticket.getTicket());
			final DatosRetornoClave drc = new DatosRetornoClave();
			drc.setTicket(ticket.getTicket());
			drc.setUrlCallbackLogin(ticket.getUrlCallback());
			drc.setIdioma(ticket.getIdioma());
			modelAndView = new ModelAndView("retornoClave", "datos", drc);
			return modelAndView;
		} else {
			// Mostramos pagina generica de error
			request.getSession().setAttribute(ERROR_ID_SESION, idSesion);
			modelAndView = new ModelAndView("redirect:/error.html?code="
					+ ErrorCodes.USUARIOPASSWORD_NO_VALIDO.toString() + "&reintentar=true");
		}
		return modelAndView;

	}

	/**
	 * Recibe peticion de inicio de sesion en Clave y redirige a Clave.
	 *
	 * @param idSesion idSesion
	 * @return pagina que realiza la redireccion a Clave
	 */
	@RequestMapping(value = "/redirigirLoginClave.html")
	public ModelAndView redirigirLoginClave(@RequestParam("idSesion") final String idSesion) {

		log.debug("Iniciar sesion clave");

		// Generamos request SAML
		final PeticionClave peticionClave = loginService.generarPeticionLoginClave(idSesion);

		ModelAndView modelAndView = null;
		if (peticionClave.isSimulado()) {
			log.debug("Redirigimos a clave (simulado)");
			final SimularClave simularClave = new SimularClave();
			simularClave.setIdSesion(idSesion);
			modelAndView = new ModelAndView("simularClave", "simularClave", simularClave);
		} else {
			// Iniciar sesion en clave
			log.debug("Redirigimos a clave");
			final DatosInicioSesionClave isc = new DatosInicioSesionClave();
			isc.setSamlRequest(peticionClave.getSamlRequestB64());
			isc.setUrlClave(peticionClave.getUrlClave());
			isc.setRelayState(peticionClave.getRelayState());
			isc.setIdioma(peticionClave.getIdioma());
			modelAndView = new ModelAndView("inicioSesionClave", "datos", isc);
		}
		return modelAndView;

	}

	/**
	 * Recibe peticion de logout de sesion en Clave y redirige a Clave.
	 *
	 * @param idSesion idSesion
	 * @return pagina que realiza la redireccion a Clave
	 */
	@RequestMapping(value = "/redirigirLogoutClave.html")
	public ModelAndView redirigirLogoutClave(@RequestParam("idSesion") final String idSesion) {

		log.debug("Iniciar logout sesion clave");

		// Generamos request SAML
		final PeticionClaveLogout peticionClave = loginService.generarPeticionLogoutClave(idSesion);

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
	 * @param idSesion     idSesion.
	 * @param samlResponse samlResponse.
	 * @return pagina que realiza la redireccion a aplicacion externa tras el login
	 *         en Clave
	 */
	@RequestMapping(value = "/retornoLoginClave/{idSesion}.html", method = RequestMethod.POST)
	public ModelAndView retornoLoginClave(@PathVariable("idSesion") final String idSesion,
			@RequestParam("SAMLResponse") final String samlResponse,
			@RequestParam("RelayState") final String relayState, final HttpServletRequest request) {

		log.debug("Retorno clave: id sesion = " + idSesion);
		moduleConfig.init();
		// Recupera datos de la request
		final String ipAddress = request.getRemoteAddr();
		final Map<String, String> headers = extractHeaders(request);

		final TicketClave ticket;
		try {
			ticket = loginService.procesarRespuestaLoginClave(idSesion, samlResponse, relayState, headers, ipAddress);
		} catch (NoDesgloseException e) {
			// Mostramos pagina generica de error
			log.debug("Error en retorno clave por no desglose correcto", e);
			request.getSession().setAttribute(ERROR_ID_SESION, idSesion);
			if (e.getCause().getClass() == NoDesgloseException.class) {
				return new ModelAndView(
						"redirect:/error.html?code=" + ErrorCodes.DESGLOSE_APELLIDOS_INCORRECTO.toString());
			} else {
				return new ModelAndView("redirect:/error.html?code=" + ErrorCodes.ERROR_GENERAL.toString());
			}
		}
		if (ticket.isTest()) {

			// Cuando viene de test.html
			return desgloseCertificado(ticket.getTicket(), ticket.getNif(), ticket.getPersonalizacion(), samlResponse);

		} else {

			if (ticket.isYaDesglosado()) {

				log.debug("Retornamos a aplicacion: ticket = " + ticket.getTicket());
				final DatosRetornoClave drc = new DatosRetornoClave();
				drc.setTicket(ticket.getTicket());
				drc.setUrlCallbackLogin(ticket.getUrlCallback());
				drc.setIdioma(ticket.getIdioma());
				return new ModelAndView("retornoClave", "datos", drc);

			} else {

				return cargarDatos(ticket);

			}

		}
	}

	/**
	 * Carga los datos dependiendo de si es representante o no.
	 *
	 * @param ticket
	 * @param datos
	 * @return
	 */
	private ModelAndView cargarDatos(TicketClave ticket) {
		ModelMap map = new ModelMap();

		if (ticket.isRepresentante()) {

			map.addAttribute("nif", ticket.getPersonaRepresentante());
			map.addAttribute("representante", "S");
			map.addAttribute("nombre", ticket.getPersonaRepresentante().getNombre());
			map.addAttribute("razonSocialDef", ticket.getPersonaAutenticado().getNombre());
			map.addAttribute("apellido1", ticket.getPersonaRepresentante().getApellido1());
			map.addAttribute("apellido2", ticket.getPersonaRepresentante().getApellido2());
			map.addAttribute("nombreDef", ticket.getPersonaRepresentante().getNombre());
			map.addAttribute("apellido1Def", ticket.getPersonaRepresentante().getApellido1());
			map.addAttribute("apellido2Def", ticket.getPersonaRepresentante().getApellido2());
			map.addAttribute("idioma", ticket.getIdioma());
			map.addAttribute("callback", "desgloseNombre.html");

		} else {

			map.addAttribute("nif", ticket.getNif());
			map.addAttribute("representante", "N");
			map.addAttribute("nombre", ticket.getPersonaAutenticado().getNombre());
			map.addAttribute("apellido1", ticket.getPersonaAutenticado().getApellido1());
			map.addAttribute("apellido2", ticket.getPersonaAutenticado().getApellido2());
			map.addAttribute("nombreDef", ticket.getPersonaAutenticado().getNombre());
			map.addAttribute("apellido1Def", ticket.getPersonaAutenticado().getApellido1());
			map.addAttribute("apellido2Def", ticket.getPersonaAutenticado().getApellido2());
			map.addAttribute("idioma", ticket.getIdioma());
			map.addAttribute("callback", "desgloseNombre.html");
		}
		map.addAttribute("ticket", ticket.getTicket());
		map.addAttribute("personalizacion", ticket.getPersonalizacion());
		return new ModelAndView(ticket.getUrlDesglose(), map);
	}

	private ModelAndView desgloseCertificado(String ticket, String nif, PersonalizacionEntidad personalizacion,
			String samlResponse) {
		// aqui manda a la vista con la info
		SesionLogin sesion = loginService.loginByTicket(ticket, true);
		String idp = sesion.getIdp();
		String nombre = sesion.getNombre();
		String apellidos = sesion.getApellidos();
		String apellido1 = sesion.getApellido1();
		String apellido2 = sesion.getApellido2();
		String representanteNombre = sesion.getRepresentanteNombre();
		String representanteApellidos = sesion.getRepresentanteApellidos();
		String representanteApellido1 = sesion.getRepresentanteApellido1();
		String representanteApellido2 = sesion.getRepresentanteApellido2();
		String representanteNif = sesion.getRepresentanteNif();
		Integer qaaPeticion = sesion.getQaa();
		Integer qaaAutenticacion = sesion.getQaaAutenticacion();
		DesgloseApellidos desglose = desgloseService.loadDesglose(nif);
		String isDesglose = (desglose == null) ? "N" : "S";
		String isRepresentante = (sesion.getRepresentanteNif() == null || sesion.getRepresentanteNif().isEmpty()) ? "N"
				: "S";
		ModelMap map = new ModelMap();
		map.addAttribute("idp", idp);
		map.addAttribute("nif", nif);
		map.addAttribute("nombre", nombre);
		map.addAttribute("apellidos", apellidos);
		map.addAttribute("apellido1", apellido1);
		map.addAttribute("apellido2", apellido2);
		map.addAttribute("representanteNombre", representanteNombre);
		map.addAttribute("representanteApellidos", representanteApellidos);
		map.addAttribute("representanteApellido1", representanteApellido1);
		map.addAttribute("representanteApellido2", representanteApellido2);
		map.addAttribute("representanteNif", representanteNif);
		/*QAA enviado por LIB a Cl@ve*/
		map.addAttribute("qaaPeticion", qaaPeticion);
		/*QAA que retorna Cl@ve en función del método de autenticación seleccionado por el usuario*/
		map.addAttribute("qaaAutenticacion", qaaAutenticacion);
		map.addAttribute("isDesglose", isDesglose);
		map.addAttribute("isRepresentante", isRepresentante);
		map.addAttribute("personalizacion", personalizacion);

		String loginIBnombre, loginIBapellido1, loginIBapellido2, loginIBfechaCreacion, loginIBFechaMod;
		if (desglose == null) {
			loginIBnombre = "";
			loginIBapellido1 = null;
			loginIBapellido2 = null;
			loginIBfechaCreacion = null;
			loginIBFechaMod = null;
		} else {
			loginIBnombre = desglose.getNombre();
			loginIBapellido1 = desglose.getApellido1();
			loginIBapellido2 = desglose.getApellido2();
			loginIBfechaCreacion = desglose.getFechaCreacion() == null ? "" : desglose.getFechaCreacion().toGMTString();
			loginIBFechaMod = desglose.getFechaActualizacion() == null ? ""
					: desglose.getFechaActualizacion().toGMTString();
		}
		map.addAttribute("loginIBnombre", loginIBnombre);
		map.addAttribute("loginIBapellido1", loginIBapellido1);
		map.addAttribute("loginIBapellido2", loginIBapellido2);
		map.addAttribute("loginIBfechaCreacion", loginIBfechaCreacion);
		map.addAttribute("loginIBFechaMod", loginIBFechaMod);
		map.addAttribute("datosB64", getDatosB64(nif, nombre, apellidos, apellido1, apellido2, idp, qaaAutenticacion, isDesglose,  sesion.getSamlRequestB64(), samlResponse, loginIBnombre, loginIBapellido1, loginIBapellido2, loginIBfechaCreacion, loginIBFechaMod, representanteNombre, representanteApellidos, representanteApellido1, representanteApellido2, representanteNif));
		return new ModelAndView("desgloseCertificado", map);
	}

	private Object getDatosB64(String nif, String nombre, String apellidos, String apellido1, String apellido2,
			String idp, Integer qaa, String isDesglose, String samlRequest, String samlResponse, String loginIBnombre,
			String loginIBapellido1, String loginIBapellido2, String loginIBfechaCreacion, String loginIBFechaMod,
			String representanteNombre, String representanteApellidos, String representanteApellido1,
			String representanteApellido2, String representanteNif) {
		StringBuilder retorno = new StringBuilder();
		retorno.append(" { \n");
		retorno.append("  \"nif\": \"" + nif + "\", \n");
		retorno.append("  \"nombre\": \"" + nombre + "\", \n");
		retorno.append("  \"apellidos\": \"" + apellidos + "\", \n");
		retorno.append("  \"apellido1\": \"" + apellido1 + "\", \n");
		retorno.append("  \"apellido2\": \"" + apellido2 + "\", \n");

		retorno.append("  \"nifRepresentante\": \"" + representanteNif + "\", \n");
		retorno.append("  \"nombreRepresentante\": \"" + representanteNombre + "\", \n");
		retorno.append("  \"apellidosRepresentante\": \"" + representanteApellidos + "\", \n");
		retorno.append("  \"apellido1Representante\": \"" + representanteApellido1 + "\", \n");
		retorno.append("  \"apellido2Representante\": \"" + representanteApellido2 + "\", \n");

		retorno.append("  \"nivelAutenticacion\": \"" + idp + "\", \n");
		// retorno.append(" \"clasificacionCertificado\": 0, ");
		retorno.append("  \"qaaSeleccionado\": " + qaa + ", \n");
		retorno.append("  \"desgloseApellidosClave\": \"" + isDesglose + "\", \n");
		retorno.append("  \"loginIBnombre\": \"" + loginIBnombre + "\", \n");
		retorno.append("  \"loginIBapellido1\": \"" + loginIBapellido1 + "\", \n");
		retorno.append("  \"loginIBapellido2\": \"" + loginIBapellido2 + "\", \n");
		retorno.append("  \"loginIBfechaCreacion\": \"" + loginIBfechaCreacion + "\", \n");
		retorno.append("  \"loginIBFechaMod\": \"" + loginIBFechaMod + "\", \n");
		retorno.append("  \"samlRequest\": \"" + samlRequest + "\",  \n");
		retorno.append("  \"samlResponse\": \"" + samlResponse + "\", \n");
		retorno.append(" } ");

		return Base64.getEncoder().encodeToString(retorno.toString().getBytes());
	}

	/**
	 * Retorno de logout Clave y vuelta a aplicacion externa.
	 *
	 * @param idSesion     idSesion.
	 * @param samlResponse samlResponse.
	 * @return pagina que realiza la redireccion a aplicacion externa tras el login
	 *         en Clave
	 */
	@RequestMapping(value = "/retornoLogoutClave/{idSesion}.html", method = RequestMethod.POST)
	public ModelAndView retornoLogoutClave(@PathVariable("idSesion") final String idSesion,
			@RequestParam("logoutResponse") final String samlResponse) {

		final RespuestaClaveLogout resp = loginService.procesarRespuestaLogoutClave(idSesion, samlResponse);

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
	 * @param idSesion  idSesion
	 * @param idp       idp
	 * @param nif       nif
	 * @param nombre    nombre
	 * @param apellidos apellidos
	 * @return retorno aplicacion
	 */
	@RequestMapping(value = "loginClaveSimulado.html", method = RequestMethod.POST)
	public ModelAndView loginClaveSimulado(@RequestParam("idSesion") final String idSesion,
			@RequestParam("idp") final String idp, @RequestParam("nif") final String nif,
			@RequestParam("nombre") final String nombre, @RequestParam("apellidos") final String apellidos,
			@RequestParam("apellido1") final String apellido1, @RequestParam("apellido2") final String apellido2,
			final HttpServletRequest request) {

		log.debug("Retorno clave simulado: id sesion = " + idSesion);

		// Recupera datos de la request
		final String ipAddress = request.getRemoteAddr();
		final Map<String, String> headers = extractHeaders(request);

		// Generamos ticket autenticacion
		final TicketClave ticket;
		try {
			ticket = loginService.simularRespuestaClave(idSesion, TypeIdp.fromString(idp),
					new DatosPersona(nif, nombre, apellidos, apellido1, apellido2), headers, ipAddress);
		} catch (Exception e) {

			log.debug("Error en clave simulado por no desglose correcto", e);
			request.getSession().setAttribute(ERROR_ID_SESION, idSesion);

			if (e.getCause().getClass() == NoDesgloseException.class) {
				return new ModelAndView(
						"redirect:/error.html?code=" + ErrorCodes.DESGLOSE_APELLIDOS_INCORRECTO.toString());
			} else {
				return new ModelAndView("redirect:/error.html?code=" + ErrorCodes.ERROR_GENERAL.toString());
			}

		}
		// Retornamos aplicacion
		log.debug("Retornamos a aplicacion: ticket = " + ticket.getTicket());
		final DatosRetornoClave drc = new DatosRetornoClave();
		drc.setTicket(ticket.getTicket());
		drc.setUrlCallbackLogin(ticket.getUrlCallback());
		drc.setIdioma(ticket.getIdioma());

		if (ticket.isTest()) {
			return desgloseCertificado(ticket.getTicket(), ticket.getNif(), ticket.getPersonalizacion(), "");
		} else {
			return new ModelAndView("retornoClave", "datos", drc);
		}
	}

	/**
	 * Muestra error.
	 *
	 * @param errorCode codigo error
	 * @return pagina error
	 */
	@RequestMapping("/error.html")
	public ModelAndView error(@RequestParam("code") final String errorCode,
			@RequestParam(value = "reintentar", required = false) final String reintentar,
			final HttpServletRequest request) {

		PersonalizacionEntidad personalizacion = null;
		String urlCallbackError = null;
		String idioma = "es";
		String mensajeErrorGeneral = null;
		String detalleError = null;

		// Parametros pasados por sesion: Id sesion y detalle error
		final String idSesion = (String) request.getSession().getAttribute(ERROR_ID_SESION);
		detalleError = (String) request.getSession().getAttribute(ERROR_DETALLE);
		request.getSession().removeAttribute(ERROR_ID_SESION);
		request.getSession().removeAttribute(ERROR_DETALLE);

		// Cargamos sesion (particularizacion entidad y url callback)
		if (idSesion != null) {
			final DatosSesion datosSesion = this.loginService.obtenerDatosSesionLogin(idSesion);
			idioma = datosSesion.getSesion().getIdioma();
			personalizacion = datosSesion.getPersonalizacionEntidad();
			urlCallbackError = datosSesion.getSesion().getUrlCallbackError();
		}

		// Reintentar autenticacion
		if (idSesion != null && "true".equals(reintentar)) {
			urlCallbackError = "seleccionAutenticacion.html?idSesion=" + idSesion;
		}

		// Código error
		ErrorCodes error = ErrorCodes.fromString(errorCode);
		if (error == null) {
			error = ErrorCodes.ERROR_GENERAL;
		}
		mensajeErrorGeneral = error.toString();

		// Retornamos respuesta
		final RespuestaError respuesta = new RespuestaError();
		respuesta.setIdioma(idioma);
		respuesta.setMensajeErrorGeneral(mensajeErrorGeneral);
		respuesta.setMensajeErrorDetalle(detalleError);
		respuesta.setUrlCallback(urlCallbackError);
		respuesta.setPersonalizacion(personalizacion);
		return new ModelAndView("errorDetalle", "datos", respuesta);

	}

	/**
	 * Handler de excepciones de negocio.
	 *
	 * @param pex     Excepción
	 * @param request Request
	 * @return Respuesta JSON indicando el mensaje producido
	 */
	@ExceptionHandler({ Exception.class })
	public ModelAndView handleServiceException(final Exception pex, final HttpServletRequest request) {

		Exception ex = pex;

		if (pex instanceof EJBException && pex.getCause() instanceof ServiceException) {
			ex = (Exception) pex.getCause();
		} else if (!(pex instanceof ServiceException)) {
			log.error("Excepcion en capa front: " + pex.getMessage(), pex);
		}

		// Si es una excepcion al procesar la respuesta de Clave, mostramos
		// detalle al usuario
		ErrorCodes errorCode = ErrorCodes.ERROR_GENERAL;
		String errorMessage = null;
		String idSesion = null;
		if (ex instanceof ValidateLoginException) {

			errorCode = ErrorCodes.ERROR_LOGIN;

			errorMessage = ((ValidateLoginException) ex).getMensajeError();
			idSesion = ((ValidateLoginException) ex).getIdSesion();

			// Detectamos errores particularizados
			// - Clave
			if (ex instanceof ValidateClaveException) {
				final Map<String, String> mapErrores = loginService.obtenerMapeoErroresValidacion("clave");
				String errorCodeStr = null;
				for (final String mapErrorKey : mapErrores.keySet()) {
					if (errorMessage.contains(mapErrores.get(mapErrorKey))) {
						errorCodeStr = mapErrorKey;
						break;
					}
				}
				if (errorCodeStr != null && ErrorCodes.fromEnum(errorCodeStr) != null) {
					errorCode = ErrorCodes.fromEnum(errorCodeStr);
				}
			}

			// Si particularizamos error, no mostramos error original
			/*
			 * if (errorCode != ErrorCodes.ERROR_LOGIN) { errorMessage = null; }
			 */

		}

		request.getSession().setAttribute(ERROR_DETALLE, errorMessage);
		request.getSession().setAttribute(ERROR_ID_SESION, idSesion);

		// Mostramos pagina de error
		return new ModelAndView("redirect:/error.html?code=" + errorCode.toString());
	}

	/**
	 * Extrae headers de la request.
	 *
	 * @param request request
	 * @return headers
	 */
	private Map<String, String> extractHeaders(final HttpServletRequest request) {
		final Map<String, String> headers = new HashMap<>();
		final Enumeration<String> headerEnum = request.getHeaderNames();
		while (headerEnum.hasMoreElements()) {
			final String headerName = headerEnum.nextElement();
			headers.put(headerName, request.getHeader(headerName));
		}
		return headers;
	}

	/**
	 * Extrae certificado de la request.
	 *
	 * @param request request
	 * @return certificado
	 */
	private X509Certificate extractCertificate(final HttpServletRequest request) {
		X509Certificate certificado = null;
		final X509Certificate[] certs = (X509Certificate[]) request
				.getAttribute("javax.servlet.request.X509Certificate");
		if (certs != null && certs.length > 0) {
			certificado = certs[0];
		}
		return certificado;
	}

	@RequestMapping(value = { "/retornoLoginClave/desgloseNombre.html",
			"/retornoClaveSimulado/desgloseNombre.html" }, method = RequestMethod.POST)
	public ModelAndView desgloseNombre(DesgloseApellidos desglose) {

		// Procesamos la respuesta del desglose
		final TicketDesglose ticket = loginService.procesarRespuestaDesglose(desglose);

		if (ticket.isForzarDesglose()) {
			return desgloseCertificado(ticket.getTicket(), ticket.getNif(), ticket.getPersonalizacion(), "");
		} else {

			// Mandar hacia el callback
			final DatosRetornoClave drc = new DatosRetornoClave();
			drc.setTicket(ticket.getTicket());
			drc.setUrlCallbackLogin(ticket.getUrlCallback());
			drc.setIdioma(ticket.getIdioma());
			return new ModelAndView("retornoClave", "datos", drc);
		}
	}

}
