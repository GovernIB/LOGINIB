package es.caib.loginib.frontend.model;

/**
 * Datos para retornar de logout clave.
 * 
 * @author Indra
 */
public final class DatosLogoutClave {

	/** Url callback login. */
	private String urlCallback;

	/** Logout. */
	private boolean logout;

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
	 * @param logout
	 *            the logout to set
	 */
	public void setLogout(boolean logout) {
		this.logout = logout;
	}

}
