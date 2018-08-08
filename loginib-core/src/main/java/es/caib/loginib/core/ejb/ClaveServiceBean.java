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
    public String iniciarLoginClave(String entidad, String urlCallback,
            String idioma, List<TypeIdp> idps, int qaa, boolean forceAuth) {
        return claveService.iniciarLoginClave(entidad, urlCallback, idioma,
                idps, qaa, forceAuth);
    }

    @Override
    public String iniciarLogoutClave(String entidad, String pUrlCallback,
            String idioma) {
        return claveService.iniciarLogoutClave(entidad, pUrlCallback, idioma);
    }

    @Override
    public PeticionClave generarPeticionLoginClave(String idSesion) {
        return claveService.generarPeticionLoginClave(idSesion);
    }

    @Override
    public TicketClave procesarRespuestaLoginClave(String idSesion,
            String samlResponseB64) {
        return claveService.procesarRespuestaLoginClave(idSesion,
                samlResponseB64);
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
    public DatosUsuario obtenerDatosAutenticacion(String ticket) {
        return claveService.obtenerDatosAutenticacion(ticket);
    }

    @Override
    public void purgar() {
        claveService.purgar();
    }

    @Override
    public String obtenerUrlRedireccionLoginClave(String idSesion) {
        return claveService.obtenerUrlRedireccionLoginClave(idSesion);
    }

    @Override
    public String obtenerUrlRedireccionLogoutClave(String pIdSesion) {
        return claveService.obtenerUrlRedireccionLogoutClave(pIdSesion);
    }

    @Override
    public TicketClave simularRespuestaClave(String pIdSesion, TypeIdp pIdp,
            String pNif, String pNombre, String pApellidos, String pApellido1,
            String pApellido2) {
        return claveService.simularRespuestaClave(pIdSesion, pIdp, pNif,
                pNombre, pApellidos, pApellido1, pApellido2);
    }

    @Override
    public PeticionClaveLogout generarPeticionLogoutClave(String idSesion) {
        return claveService.generarPeticionLogoutClave(idSesion);
    }

    @Override
    public RespuestaClaveLogout procesarRespuestaLogoutClave(String pIdSesion,
            String pSamlResponseB64) {
        return claveService.procesarRespuestaLogoutClave(pIdSesion,
                pSamlResponseB64);
    }

    @Override
    public DatosSesion obtenerDatosSesionLogin(String idSesion) {
        return claveService.obtenerDatosSesionLogin(idSesion);
    }

    @Override
    public TicketClave loginAnonimo(String pIdSesion) {
        return claveService.loginAnonimo(pIdSesion);
    }

}
