package es.caib.loginib.core.api.exception;

@SuppressWarnings("serial")
public class HashException extends ServiceRollbackException {

	public HashException(final Throwable e) {
		super("Error generando HASH: " + e.getMessage(), e);
	}

}
