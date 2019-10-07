package es.caib.loginib.core.api.exception;

/**
 *
 * Error parametro no valido.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ErrorParametroException extends ServiceRollbackException {

	/**
	 * Constructor.
	 *
	 * @param message
	 *                    mensaje
	 */
	public ErrorParametroException(final String message) {
		super(message);
	}

}
