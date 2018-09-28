package es.caib.loginib.core.api.exception;

/**
 *
 * Excepcion cuando Idp no es válido.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class IdpNoValido extends ServiceRollbackException {

    /**
     * Constructor IdpNoValido.
     *
     */
    public IdpNoValido(String idp) {
        super("Idp no válido: " + idp);
    }

}
