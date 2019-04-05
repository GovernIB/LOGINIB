package es.caib.loginib.frontend.model;

/**
 * Datos para iniciar sesion en clave.
 *
 * @author Indra
 *
 */
public final class DatosInicioSesionClave {

	/** Url clave. */
	private String urlClave;

	/** Peticion SAML de autenticacion. */
	private String samlRequest;

	/** Idioma. */
	private String idioma;

	/** Idps. */
	private String idps;

	/**
	 * Obtiene url clave.
	 *
	 * @return the urlClave
	 */
	public String getUrlClave() {
		return urlClave;
	}

	/**
	 * Establece url clave.
	 *
	 * @param urlClave
	 *            the urlClave to set
	 */
	public void setUrlClave(final String urlClave) {
		this.urlClave = urlClave;
	}

	/**
	 * Obtiene saml request.
	 *
	 * @return the samlRequest
	 */
	public String getSamlRequest() {
		return samlRequest;
	}

	/**
	 * Establece saml request.
	 *
	 * @param samlRequest
	 *            the samlRequest to set
	 */
	public void setSamlRequest(final String samlRequest) {
		this.samlRequest = samlRequest;
	}

	/**
	 * Obtiene idioma.
	 *
	 * @return the idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Establece idioma.
	 *
	 * @param idioma
	 *            the idioma to set
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Método de acceso a idps.
	 * 
	 * @return idps
	 */
	public String getIdps() {
		return idps;
	}

	/**
	 * Método para establecer idps.
	 * 
	 * @param idps
	 *            idps a establecer
	 */
	public void setIdps(String idps) {
		this.idps = idps;
	}

}
