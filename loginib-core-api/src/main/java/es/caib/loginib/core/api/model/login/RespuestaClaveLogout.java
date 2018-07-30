package es.caib.loginib.core.api.model.login;

/**
 * Respuesta procesar clave.
 * 
 * @author Indra
 * 
 */
public final class RespuestaClaveLogout {

	 /** Url callback. */
	  private String urlCallback;

	  /** Indica si se ha realizado logout. */
	  private boolean logout;

	/**
	 * @return the urlCallback
	 */
	public String getUrlCallback() {
		return urlCallback;
	}

	/**
	 * @param urlCallback the urlCallback to set
	 */
	public void setUrlCallback(String urlCallback) {
		this.urlCallback = urlCallback;
	}

	/**
	 * @return the logout
	 */
	public boolean isLogout() {
		return logout;
	}

	/**
	 * @param logout the logout to set
	 */
	public void setLogout(boolean logout) {
		this.logout = logout;
	}	

}
