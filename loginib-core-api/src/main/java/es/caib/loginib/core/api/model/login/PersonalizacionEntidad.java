package es.caib.loginib.core.api.model.login;

/**
 * Customización entidad.
 *
 * @author Indra
 *
 */
public class PersonalizacionEntidad {

	/** CSS. */
	private String css;
	/** Favicon. */
	private String favicon;
	/** Titulo página navegador. */
	private String title;
	/** Título página. */
	private String titulo;
	/** Url logo. */
	private String logourl;
	/** Alt logo. */
	private String logoalt;

	/**
	 * Método de acceso a css.
	 * 
	 * @return css
	 */
	public String getCss() {
		return css;
	}

	/**
	 * Método para establecer css.
	 * 
	 * @param css
	 *                css a establecer
	 */
	public void setCss(final String css) {
		this.css = css;
	}

	/**
	 * Método de acceso a favicon.
	 * 
	 * @return favicon
	 */
	public String getFavicon() {
		return favicon;
	}

	/**
	 * Método para establecer favicon.
	 * 
	 * @param favicon
	 *                    favicon a establecer
	 */
	public void setFavicon(final String favicon) {
		this.favicon = favicon;
	}

	/**
	 * Método de acceso a title.
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Método para establecer title.
	 * 
	 * @param title
	 *                  title a establecer
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * Método de acceso a titulo.
	 * 
	 * @return titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Método para establecer titulo.
	 * 
	 * @param titulo
	 *                   titulo a establecer
	 */
	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Método de acceso a logourl.
	 * 
	 * @return logourl
	 */
	public String getLogourl() {
		return logourl;
	}

	/**
	 * Método para establecer logourl.
	 * 
	 * @param logourl
	 *                    logourl a establecer
	 */
	public void setLogourl(final String logourl) {
		this.logourl = logourl;
	}

	/**
	 * Método de acceso a logoalt.
	 * 
	 * @return logoalt
	 */
	public String getLogoalt() {
		return logoalt;
	}

	/**
	 * Método para establecer logoalt.
	 * 
	 * @param logoalt
	 *                    logoalt a establecer
	 */
	public void setLogoalt(final String logoalt) {
		this.logoalt = logoalt;
	}

}
