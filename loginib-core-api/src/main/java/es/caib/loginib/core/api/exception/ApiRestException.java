package es.caib.loginib.core.api.exception;

/**
 *
 * Excepcion generada en api rest.
 *
 */
@SuppressWarnings("serial")
public class ApiRestException extends RuntimeException {

    /**
     * Genera excepción API Rest .
     *
     * @param message
     *            Mensaje
     */
    public ApiRestException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message
     *            Mensaje
     * @param excep
     *            Excepcion origen
     */
    public ApiRestException(final String message, final Throwable excep) {
        super(message, excep);
    }

}
