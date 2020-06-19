package es.caib.loginib.core.api.exception;

/**
 *
 * Excepcion al interpretar respuesta Clave.
 *
 * Proporciona error de Clave al usuario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ValidateClaveException extends ValidateLoginException {

	/**
	 * Constructor.
	 *
	 * @param cause
	 *                  Causa
	 */
	public ValidateClaveException(final String errorMessage, final Throwable cause, final String idSesion) {
		super(errorMessage, cause, idSesion);
	}

	/**
	 * Constructor.
	 *
	 * @param cause
	 *                  Causa
	 */
	public ValidateClaveException(final String errorMessage, final String idSesion) {
		super(errorMessage, idSesion);
	}

}
