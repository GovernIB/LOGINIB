package es.caib.loginib.core.api.exception;

/**
 *
 * Excepcion cuando no existe sesion.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DesgloseDesactivadoException extends ServiceRollbackException {

	/**
	 * Constructor NoExiseSesionException.
	 *
	 */
	public DesgloseDesactivadoException() {
		super("Se han desactivado los desgloses");
	}

}