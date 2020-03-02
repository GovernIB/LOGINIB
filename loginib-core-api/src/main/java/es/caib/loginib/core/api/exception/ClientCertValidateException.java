package es.caib.loginib.core.api.exception;

/**
 *
 * Excepcion al validar certificado en método client cert.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ClientCertValidateException extends ServiceRollbackException {

	/**
	 * Constructor GenerarPeticionClaveException.
	 *
	 * @param cause
	 *                  Causa
	 */
	public ClientCertValidateException(final Throwable cause) {
		super("Error al validar certificado para ClientCert: " + cause.getMessage(), cause);
	}

	/**
	 * Constructor GenerarPeticionClaveException.
	 *
	 * @param cause
	 *                  Causa
	 */
	public ClientCertValidateException(final String cause) {
		super("Error al validar certificado para ClientCert: " + cause);
	}

	/**
	 * Constructor GenerarPeticionClaveException.
	 *
	 * @param cause
	 *                  Causa
	 */
	public ClientCertValidateException(final String cause, final Throwable exc) {
		super("Error al validar certificado para ClientCert: " + cause, exc);
	}

}
