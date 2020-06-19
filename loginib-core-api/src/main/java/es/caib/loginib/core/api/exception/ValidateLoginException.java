package es.caib.loginib.core.api.exception;

/**
 *
 * Excepcion al validar metodo.
 *
 * Proporciona error de Clave al usuario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public abstract class ValidateLoginException extends ServiceRollbackException {

	/**
	 * Id sesion.
	 */
	private String idSesion;

	/**
	 * Error clave.
	 */
	private String mensajeError;

	/**
	 * Método de acceso a idSesion.
	 *
	 * @return idSesion
	 */
	public String getIdSesion() {
		return idSesion;
	}

	/**
	 * Método para establecer idSesion.
	 *
	 * @param idSesion
	 *                     idSesion a establecer
	 */
	public void setIdSesion(final String idSesion) {
		this.idSesion = idSesion;
	}

	/**
	 * Método de acceso a mensajeError.
	 *
	 * @return mensajeError
	 */
	public String getMensajeError() {
		return mensajeError;
	}

	/**
	 * Método para establecer mensajeError.
	 *
	 * @param mensajeError
	 *                         mensajeError a establecer
	 */
	public void setMensajeError(final String mensajeError) {
		this.mensajeError = mensajeError;
	}

	/**
	 * Constructor GenerarPeticionClaveException.
	 *
	 * @param cause
	 *                  Causa
	 */
	public ValidateLoginException(final String errorMessage, final Throwable cause, final String idSesion) {
		super(errorMessage, cause);
		this.setIdSesion(idSesion);
		this.setMensajeError(errorMessage);
	}

	/**
	 * Constructor GenerarPeticionClaveException.
	 *
	 * @param cause
	 *                  Causa
	 */
	public ValidateLoginException(final String errorMessage, final String idSesion) {
		super(errorMessage);
		this.setIdSesion(idSesion);
		this.setMensajeError(errorMessage);
	}

}
