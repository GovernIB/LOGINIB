package es.caib.loginib.frontend.model;

/**
 * Datos para iniciar logout sesion en clave.
 * 
 * @author Indra
 * 
 */
public final class DatosInicioLogoutSesionClave {

	/** Url clave. */
	private String urlClave;

	/** Peticion SAML de autenticacion. */
	private String samlRequest;

	/**
	 * @return the urlClave
	 */
	public String getUrlClave() {
		return urlClave;
	}

	/**
	 * @param urlClave
	 *            the urlClave to set
	 */
	public void setUrlClave(String urlClave) {
		this.urlClave = urlClave;
	}

	/**
	 * @return the samlRequest
	 */
	public String getSamlRequest() {
		return samlRequest;
	}

	/**
	 * @param samlRequest
	 *            the samlRequest to set
	 */
	public void setSamlRequest(String samlRequest) {
		this.samlRequest = samlRequest;
	}

}
