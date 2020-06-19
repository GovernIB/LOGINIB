package es.caib.loginib.core.api.exception;

/**
 *
 * Excepcion cuando no existe sesion.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class NoExisteSesionException extends ServiceRollbackException {

	/**
	 * Constructor NoExiseSesionException.
	 *
	 */
	public NoExisteSesionException() {
		super("No existe sesion activa");
	}

}
