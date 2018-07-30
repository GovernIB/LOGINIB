package es.caib.loginib.core.ejb;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.loginib.core.api.ClaveService;
import es.caib.loginib.core.api.model.login.DatosUsuario;
import es.caib.loginib.core.api.model.login.PeticionClave;
import es.caib.loginib.core.api.model.login.PeticionClaveLogout;
import es.caib.loginib.core.api.model.login.RespuestaClaveLogout;
import es.caib.loginib.core.api.model.login.TicketClave;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ClaveServiceBean implements ClaveService {

    @Autowired
    private ClaveService claveService;

    @Override
    public String crearSesionClave(String urlCallback, String idioma,
            String idps, Integer qaa, boolean forceAuth) {
        return claveService.crearSesionClave(urlCallback, idioma, idps, qaa,
                forceAuth);
    }

    @Override
    public String crearLogoutSesionClave(String pUrlCallback, String idioma) {
        return claveService.crearLogoutSesionClave(pUrlCallback, idioma);
    }

    @Override
    public PeticionClave generarPeticionClave(String idSesion) {
        return claveService.generarPeticionClave(idSesion);
    }

    @Override
    public TicketClave procesarRespuestaClave(String idSesion,
            String samlResponseB64) {
        return claveService.procesarRespuestaClave(idSesion, samlResponseB64);
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
    public DatosUsuario obtenerDatosUsuarioAplicacionExterna(String ticket) {
        return claveService.obtenerDatosUsuarioAplicacionExterna(ticket);
    }

    @Override
    public void purgar() {
        claveService.purgar();
    }

    @Override
    public String obtenerUrlInicioExterna(String idSesion) {
        return claveService.obtenerUrlInicioExterna(idSesion);
    }

    @Override
    public String obtenerUrlLogoutExterna(String pIdSesion) {
        return claveService.obtenerUrlLogoutExterna(pIdSesion);
    }

    @Override
    public TicketClave simularRespuestaClave(String pIdSesion, String pIdp,
            String pNif, String pNombre, String pApellidos, String pApellido1,
            String pApellido2) {
        return claveService.simularRespuestaClave(pIdSesion, pIdp, pNif,
                pNombre, pApellidos, pApellido1, pApellido2);
    }

    @Override
    public PeticionClaveLogout generarPeticionLogout(String idSesion) {
        return claveService.generarPeticionLogout(idSesion);
    }

    @Override
    public RespuestaClaveLogout procesarRespuestaLogout(String pIdSesion,
            String pSamlResponseB64) {
        return claveService.procesarRespuestaLogout(pIdSesion,
                pSamlResponseB64);
    }

}
