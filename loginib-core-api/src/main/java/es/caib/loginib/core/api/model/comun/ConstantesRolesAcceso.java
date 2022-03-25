package es.caib.loginib.core.api.model.comun;

/**
 * @author Indra
 *
 */
public final class ConstantesRolesAcceso {

	private ConstantesRolesAcceso() {
		super();
	}

	public static final String SUPER_ADMIN = "LIB_BACK";

	public static final String API = "LIB_API";

	public static final String[] listaRoles() {
		final String[] rolesPrincipales = { SUPER_ADMIN };
		return rolesPrincipales;
	}

}
