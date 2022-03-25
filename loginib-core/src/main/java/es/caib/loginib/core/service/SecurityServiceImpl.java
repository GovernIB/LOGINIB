package es.caib.loginib.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.loginib.core.api.model.login.types.TypeRoleAcceso;
import es.caib.loginib.core.api.service.ContextService;
import es.caib.loginib.core.api.service.SecurityService;
import es.caib.loginib.core.interceptor.NegocioInterceptor;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private ContextService contextService;

	@Override
	@NegocioInterceptor
	public List<TypeRoleAcceso> getRoles() {
		return contextService.getRoles();
	}

	@Override
	@NegocioInterceptor
	public boolean isSuperAdministrador() {
		return contextService.getRoles().contains(TypeRoleAcceso.SUPER_ADMIN);
	}

	@Override
	@NegocioInterceptor
	public String getUsername() {
		return contextService.getUsername();
	}

}
