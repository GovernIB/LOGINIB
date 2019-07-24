package es.caib.loginib.core.api.model.login;

/**
 * Peticion clave logout.
 *
 * @author Indra
 *
 */
public final class PeticionClaveLogout {

	/** SAML Request en B64. */
	private String samlRequestB64;
	/** Url clave. */
	private String urlClave;

	/**
	 * @return the samlRequestB64
	 */
	public String getSamlRequestB64() {
		return samlRequestB64;
	}

	/**
	 * @param samlRequestB64 the samlRequestB64 to set
	 */
	public void setSamlRequestB64(final String samlRequestB64) {
		this.samlRequestB64 = samlRequestB64;
	}

	/**
	 * @return the urlClave
	 */
	public String getUrlClave() {
		return urlClave;
	}

	/**
	 * @param urlClave the urlClave to set
	 */
	public void setUrlClave(final String urlClave) {
		this.urlClave = urlClave;
	}

}
