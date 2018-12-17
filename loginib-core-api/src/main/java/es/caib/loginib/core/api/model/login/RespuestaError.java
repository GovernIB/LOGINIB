package es.caib.loginib.core.api.model.login;

/**
 * Respuesta cuando se produce un error.
 *
 * @author Indra
 *
 */
public final class RespuestaError {

	/** Url callback. */
	private String urlCallback;

	/** Indica el mensaje de error. */
	private String mensaje;

	/**
	 * @return the urlCallback
	 */
	public String getUrlCallback() {
		return urlCallback;
	}

	/**
	 * @param urlCallback
	 *            the urlCallback to set
	 */
	public void setUrlCallback(final String urlCallback) {
		this.urlCallback = urlCallback;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(final String mensaje) {
		this.mensaje = mensaje;
	}

}
