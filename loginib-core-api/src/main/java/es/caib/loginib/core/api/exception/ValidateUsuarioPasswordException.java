package es.caib.loginib.core.api.exception;

/**
 *
 * Excepcion al validar certificado en método client cert.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ValidateUsuarioPasswordException extends ValidateLoginException {

	/**
	 * Constructor.
	 *
	 * @param cause
	 *                  Causa
	 */
	public ValidateUsuarioPasswordException(final String errorMessage, final Throwable cause, final String idSesion) {
		super(errorMessage, cause, idSesion);
	}

	/**
	 * Constructor.
	 *
	 * @param cause
	 *                  Causa
	 */
	public ValidateUsuarioPasswordException(final String errorMessage, final String idSesion) {
		super(errorMessage, idSesion);
	}

}
