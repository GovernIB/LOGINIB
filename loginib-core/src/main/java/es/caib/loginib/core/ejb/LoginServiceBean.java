package es.caib.loginib.core.ejb;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.loginib.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.loginib.core.api.model.login.DatosAutenticacion;
import es.caib.loginib.core.api.model.login.DatosPersona;
import es.caib.loginib.core.api.model.login.DatosSesion;
import es.caib.loginib.core.api.model.login.DesgloseApellidos;
import es.caib.loginib.core.api.model.login.EvidenciasAutenticacion;
import es.caib.loginib.core.api.model.login.PersonalizacionEntidad;
import es.caib.loginib.core.api.model.login.PeticionClave;
import es.caib.loginib.core.api.model.login.PeticionClaveLogout;
import es.caib.loginib.core.api.model.login.RespuestaClaveLogout;
import es.caib.loginib.core.api.model.login.SesionLogin;
import es.caib.loginib.core.api.model.login.TicketClave;
import es.caib.loginib.core.api.model.login.TicketDesglose;
import es.caib.loginib.core.api.model.login.ValidacionUsuarioPassword;
import es.caib.loginib.core.api.model.login.types.TypeIdp;
import es.caib.loginib.core.api.service.LoginService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class LoginServiceBean implements LoginService {

	@Autowired
	private LoginService loginService;

	// --------- FUNCIONES FRONTAL -----------------------------------

	@Override
	@PermitAll
	public PeticionClave generarPeticionLoginClave(final String idSesion) {
		return loginService.generarPeticionLoginClave(idSesion);
	}

	@Override
	@PermitAll
	public TicketClave procesarRespuestaLoginClave(final String idSesion, final String samlResponseB64,
			final String relayStateReq, final Map<String, String> headersRequest, final String ipAddressFrom) {
		return loginService.procesarRespuestaLoginClave(idSesion, samlResponseB64, relayStateReq, headersRequest,
				ipAddressFrom);
	}

	@Override
	@PermitAll
	public void purgar() {
		loginService.purgar();
	}

	@Override
	@PermitAll
	public TicketClave simularRespuestaClave(final String pIdSesion, final TypeIdp pIdp, final DatosPersona autenticado,
			final Map<String, String> headersRequest, final String ipAddressFrom) {
		return loginService.simularRespuestaClave(pIdSesion, pIdp, autenticado, headersRequest, ipAddressFrom);
	}

	@Override
	@PermitAll
	public PeticionClaveLogout generarPeticionLogoutClave(final String idSesion) {
		return loginService.generarPeticionLogoutClave(idSesion);
	}

	@Override
	@PermitAll
	public RespuestaClaveLogout procesarRespuestaLogoutClave(final String pIdSesion, final String pSamlResponseB64) {
		return loginService.procesarRespuestaLogoutClave(pIdSesion, pSamlResponseB64);
	}

	@Override
	@PermitAll
	public DatosSesion obtenerDatosSesionLogin(final String idSesion) {
		return loginService.obtenerDatosSesionLogin(idSesion);
	}

	@Override
	@PermitAll
	public TicketClave loginAnonimo(final String pIdSesion, final Map<String, String> headers,
			final String ipAddressFrom) {
		return loginService.loginAnonimo(pIdSesion, headers, ipAddressFrom);
	}

	@Override
	@PermitAll
	public TicketClave loginClientCert(final String pIdSesion, final Map<String, String> headers,
			final X509Certificate certificadoRequest, final String ipAddressFrom) {
		return loginService.loginClientCert(pIdSesion, headers, certificadoRequest, ipAddressFrom);
	}

	@Override
	@PermitAll
	public ValidacionUsuarioPassword loginUsuarioPassword(final String idSesion, final String usuario,
			final String password, final Map<String, String> headersRequest, final String ipAddressFrom) {
		return loginService.loginUsuarioPassword(idSesion, usuario, password, headersRequest, ipAddressFrom);
	}

	@Override
	@PermitAll
	public Map<String, String> obtenerMapeoErroresValidacion(final String key) {
		return loginService.obtenerMapeoErroresValidacion(key);
	}

	@Override
	@PermitAll
	public String iniciarSesionTest(String iIdioma,String url, boolean forzarDesglose) {
		return loginService.iniciarSesionTest(iIdioma, url, forzarDesglose);
	}

	@Override
	@PermitAll
	public PersonalizacionEntidad obtenerDatosPersonalizacionEntidad(String idSesion) {
		return loginService.obtenerDatosPersonalizacionEntidad(idSesion);
	}

	// --------- FUNCIONES API -----------------------------------

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.API })
	public String iniciarSesionLogin(final String entidad, final String urlCallback, final String urlCallbackError,
			final String idioma, final List<TypeIdp> idps, final Integer qaa, final boolean iniClaAuto,
			final boolean forceAuth, final String aplicacion, final boolean auditar,
			final Map<String, String> paramsApp) {
		return loginService.iniciarSesionLogin(entidad, urlCallback, urlCallbackError, idioma, idps, qaa, iniClaAuto,
				forceAuth, aplicacion, auditar, paramsApp);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.API })
	public String iniciarSesionLogout(final String entidad, final String pUrlCallback, final String idioma,
			final String aplicacion) {
		return loginService.iniciarSesionLogout(entidad, pUrlCallback, idioma, aplicacion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.API })
	public String obtenerUrlRedireccionLoginClave(final String idSesion, final String idioma) {
		return loginService.obtenerUrlRedireccionLoginClave(idSesion, idioma);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.API })
	public String obtenerUrlRedireccionLogoutClave(final String pIdSesion) {
		return loginService.obtenerUrlRedireccionLogoutClave(pIdSesion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.API })
	public DatosAutenticacion obtenerDatosAutenticacion(final String ticket) {
		return loginService.obtenerDatosAutenticacion(ticket);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.API })
	public EvidenciasAutenticacion obtenerEvidenciasSesionLogin(final String idSesion) {
		return loginService.obtenerEvidenciasSesionLogin(idSesion);
	}

	@Override
	@PermitAll
	public DatosAutenticacion obtenerDatosAutenticacionAll(final String ticket) {
		return loginService.obtenerDatosAutenticacion(ticket);
	}

	@Override
	@PermitAll
	public SesionLogin loginByTicket(String ticket, boolean activa) {
		return loginService.loginByTicket(ticket, activa);
	}

	@Override
	@PermitAll
	public void mergeDesglose(SesionLogin sl) {
		loginService.mergeDesglose(sl);
	}

	@Override
	@PermitAll
	public TicketDesglose procesarRespuestaDesglose(DesgloseApellidos desglose) {
		return loginService.procesarRespuestaDesglose(desglose);
	}


}
