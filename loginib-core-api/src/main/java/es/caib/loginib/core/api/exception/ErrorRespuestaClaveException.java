package es.caib.loginib.core.api.exception;

/**
 *
 * Excepcion al interpretar respuesta Clave.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ErrorRespuestaClaveException extends ServiceRollbackException {

	private String idSesion;

	/**
	 * Constructor GenerarPeticionClaveException.
	 *
	 * @param cause
	 *            Causa
	 */
	public ErrorRespuestaClaveException(final Throwable cause, final String idSesion) {
		super("Error al interpretar respuesta Clave: " + cause.getMessage(), cause);
		this.setIdSesion(idSesion);
	}

	/**
	 * Constructor GenerarPeticionClaveException.
	 *
	 * @param cause
	 *            Causa
	 */
	public ErrorRespuestaClaveException(final String cause, final String idSesion) {
		super("Error al interpretar respuesta Clave: " + cause);
		this.setIdSesion(idSesion);
	}

	/**
	 * @return the idSesion
	 */
	public String getIdSesion() {
		return idSesion;
	}

	/**
	 * @param idSesion
	 *            the idSesion to set
	 */
	public void setIdSesion(final String idSesion) {
		this.idSesion = idSesion;
	}

}
