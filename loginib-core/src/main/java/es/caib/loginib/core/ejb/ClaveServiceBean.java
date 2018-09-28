package es.caib.loginib.core.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.loginib.core.api.model.login.DatosSesion;
import es.caib.loginib.core.api.model.login.DatosUsuario;
import es.caib.loginib.core.api.model.login.PeticionClave;
import es.caib.loginib.core.api.model.login.PeticionClaveLogout;
import es.caib.loginib.core.api.model.login.RespuestaClaveLogout;
import es.caib.loginib.core.api.model.login.TicketClave;
import es.caib.loginib.core.api.model.login.types.TypeIdp;
import es.caib.loginib.core.api.service.ClaveService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ClaveServiceBean implements ClaveService {

	@Autowired
	private ClaveService claveService;

	@Override
	public String iniciarLoginClave(final String entidad, final String urlCallback, final String idioma,
			final List<TypeIdp> idps, final int qaa, final boolean forceAuth, final String aplicacion) {
		return claveService.iniciarLoginClave(entidad, urlCallback, idioma, idps, qaa, forceAuth, aplicacion);
	}

	@Override
	public String iniciarLogoutClave(final String entidad, final String pUrlCallback, final String idioma,
			final String aplicacion) {
		return claveService.iniciarLogoutClave(entidad, pUrlCallback, idioma, aplicacion);
	}

	@Override
	public PeticionClave generarPeticionLoginClave(final String idSesion) {
		return claveService.generarPeticionLoginClave(idSesion);
	}

	@Override
	public TicketClave procesarRespuestaLoginClave(final String idSesion, final String samlResponseB64) {
		return claveService.procesarRespuestaLoginClave(idSesion, samlResponseB64);
	}

	@Override
	public boolean isAccesoClaveDeshabilitado() {
		return claveService.isAccesoClaveDeshabilitado();
	}

	@Override
	public boolean isAccesoClaveSimulado() {
		return claveService.isAccesoClaveSimulado();
	}

	@Override
	public DatosUsuario obtenerDatosAutenticacion(final String ticket) {
		return claveService.obtenerDatosAutenticacion(ticket);
	}

	@Override
	public void purgar() {
		claveService.purgar();
	}

	@Override
	public String obtenerUrlRedireccionLoginClave(final String idSesion) {
		return claveService.obtenerUrlRedireccionLoginClave(idSesion);
	}

	@Override
	public String obtenerUrlRedireccionLogoutClave(final String pIdSesion) {
		return claveService.obtenerUrlRedireccionLogoutClave(pIdSesion);
	}

	@Override
	public TicketClave simularRespuestaClave(final String pIdSesion, final TypeIdp pIdp, final String pNif,
			final String pNombre, final String pApellidos, final String pApellido1, final String pApellido2) {
		return claveService.simularRespuestaClave(pIdSesion, pIdp, pNif, pNombre, pApellidos, pApellido1, pApellido2);
	}

	@Override
	public PeticionClaveLogout generarPeticionLogoutClave(final String idSesion) {
		return claveService.generarPeticionLogoutClave(idSesion);
	}

	@Override
	public RespuestaClaveLogout procesarRespuestaLogoutClave(final String pIdSesion, final String pSamlResponseB64) {
		return claveService.procesarRespuestaLogoutClave(pIdSesion, pSamlResponseB64);
	}

	@Override
	public DatosSesion obtenerDatosSesionLogin(final String idSesion) {
		return claveService.obtenerDatosSesionLogin(idSesion);
	}

	@Override
	public TicketClave loginAnonimo(final String pIdSesion) {
		return claveService.loginAnonimo(pIdSesion);
	}

}
