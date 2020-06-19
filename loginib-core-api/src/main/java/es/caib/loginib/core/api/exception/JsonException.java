package es.caib.loginib.core.api.exception;

@SuppressWarnings("serial")
public class JsonException extends ServiceRollbackException {

	public JsonException(final Throwable e) {
		super("Error convirtiendo JSON: " + e.getMessage(), e);
	}

}
