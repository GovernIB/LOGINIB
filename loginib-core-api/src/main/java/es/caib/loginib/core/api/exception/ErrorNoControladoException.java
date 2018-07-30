package es.caib.loginib.core.api.exception;

/**
 * 
 * Capturamos en una excepción de negocio las excepcion runtime no controladas
 * (no heredan de ServicioRollbackException).
 * 
 * @author Indra
 * 
 */
@SuppressWarnings("serial")
public final class ErrorNoControladoException extends ServiceRollbackException {

	@Override
	public final String getCodigoError() {	
		return "9999";
	}

	@Override
	public boolean isNegocioException() {
		return false;
	}

	/**
	 * Constructor ErrorNoControladoException.
	 * 
	 * @param cause
	 *            Causa
	 */
	public ErrorNoControladoException(final Throwable cause) {
		super("Error no controlado en la capa de servicio: "
				+ cause.getMessage(), cause);
	}		

	/**
	 * Constructor.
	 * @param message mensaje
	 */
	public ErrorNoControladoException(String message) {
		super(message);
	}
	

}
