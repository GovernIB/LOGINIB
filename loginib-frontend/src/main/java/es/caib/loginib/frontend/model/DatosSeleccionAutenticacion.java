package es.caib.loginib.frontend.model;

/**
 * Datos para iniciar sesion en clave.
 *
 * @author Indra
 *
 */
public final class DatosSeleccionAutenticacion {

	/** Id sesion. */
	private String idSesion;

	/** Idioma. */
	private String idioma;

	/** Permite clave. */
	private boolean clave;

	/** Permite anonimo. */
	private boolean anonimo;

	/** Permite clientcert. */
	private boolean clientCert;

	/** estilos adicionales por entidad */
	private String css;
	private String favicon;
	private String title;
	private String titulo;
	private String logourl;
	private String logoalt;
	private boolean clientCertSegundoPlano;

	/**
	 * @return the favicon
	 */
	public final String getFavicon() {
		return favicon;
	}

	/**
	 * @param favicon the favicon to set
	 */
	public final void setFavicon(String favicon) {
		this.favicon = favicon;
	}

	/**
	 * @return the title
	 */
	public final String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public final void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the titulo
	 */
	public final String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public final void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the logourl
	 */
	public final String getLogourl() {
		return logourl;
	}

	/**
	 * @param logourl the logourl to set
	 */
	public final void setLogourl(String logourl) {
		this.logourl = logourl;
	}

	/**
	 * @return the logoalt
	 */
	public final String getLogoalt() {
		return logoalt;
	}

	/**
	 * @param logoalt the logoalt to set
	 */
	public final void setLogoalt(String logoalt) {
		this.logoalt = logoalt;
	}

	/**
	 * Método de acceso a idSesion.
	 *
	 * @return idSesion
	 */
	public String getIdSesion() {
		return idSesion;
	}

	/**
	 * Método para establecer idSesion.
	 *
	 * @param idSesion idSesion a establecer
	 */
	public void setIdSesion(final String idSesion) {
		this.idSesion = idSesion;
	}

	/**
	 * Método de acceso a idioma.
	 *
	 * @return idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Método para establecer idioma.
	 *
	 * @param idioma idioma a establecer
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Método de acceso a clave.
	 *
	 * @return clave
	 */
	public boolean isClave() {
		return clave;
	}

	/**
	 * Método para establecer clave.
	 *
	 * @param clave clave a establecer
	 */
	public void setClave(final boolean clave) {
		this.clave = clave;
	}

	/**
	 * Método de acceso a anonimo.
	 *
	 * @return anonimo
	 */
	public boolean isAnonimo() {
		return anonimo;
	}

	/**
	 * Método para establecer anonimo.
	 *
	 * @param anonimo anonimo a establecer
	 */
	public void setAnonimo(final boolean anonimo) {
		this.anonimo = anonimo;
	}

	/**
	 * Método de acceso a clientCert.
	 *
	 * @return clientCert
	 */
	public boolean isClientCert() {
		return clientCert;
	}

	/**
	 * Método para establecer clientCert.
	 *
	 * @param clientCert clientCert a establecer
	 */
	public void setClientCert(final boolean clientCert) {
		this.clientCert = clientCert;
	}

	/**
	 * @return the css
	 */
	public String getCss() {
		return css;
	}

	/**
	 * @param css the css to set
	 */
	public void setCss(String css) {
		this.css = css;
	}

	/**
	 * @return the clientCertSegundoPlano
	 */
	public boolean isClientCertSegundoPlano() {
		return clientCertSegundoPlano;
	}

	/**
	 * @param clientCertSegundoPlano the clientCertSegundoPlano to set
	 */
	public void setClientCertSegundoPlano(boolean clientCertSegundoPlano) {
		this.clientCertSegundoPlano = clientCertSegundoPlano;
	}

}
