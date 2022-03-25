package es.caib.loginib.core.ejb;

import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.loginib.core.api.model.login.DesgloseApellidos;

import es.caib.loginib.core.api.service.DesgloseApellidosService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class DesgloseApellidosServiceBean implements DesgloseApellidosService {

	@Autowired
	private DesgloseApellidosService desgloseService;

	// --------- FUNCIONES FRONTAL -----------------------------------

	@Override
	@PermitAll
	public DesgloseApellidos loadDesglose(final String nif) {
		return desgloseService.loadDesglose(nif);
	}

	@Override
	@PermitAll
	public String addDesglose(DesgloseApellidos da) {
		return desgloseService.addDesglose(da);
	}

	@Override
	@PermitAll
	public void updateDesglose(DesgloseApellidos da) {
		desgloseService.updateDesglose(da);
	}

	@Override
	@PermitAll
	public boolean removeDesglose(String nif) {
		return desgloseService.removeDesglose(nif);
	}

	@Override
	@PermitAll
	public List<DesgloseApellidos> listDesglose(String filtro, Date fechaD, Date fechaH) {
		return desgloseService.listDesglose(filtro, fechaD, fechaH);
	}

	@Override
	@PermitAll
	public void clonar(String nif, String nuevoIdentificador) {
		desgloseService.clonar(nif, nuevoIdentificador);
	}
}
