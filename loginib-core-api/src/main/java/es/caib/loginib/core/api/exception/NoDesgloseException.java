package es.caib.loginib.core.api.exception;

/**
 *
 * Excepcion cuando no existe sesion.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class NoDesgloseException extends ServiceRollbackException {

	/**
	 * Constructor NoExiseSesionException.
	 *
	 */
	public NoDesgloseException() {
		super("No existe desglose de apellidos válido");
	}

	/**
	 * Constructor NoExiseSesionException.
	 *
	 */
	public NoDesgloseException(String texto) {
		super(texto);
	}

}