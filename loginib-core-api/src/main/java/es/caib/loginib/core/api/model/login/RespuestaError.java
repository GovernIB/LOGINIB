package es.caib.loginib.core.api.model.login;

/**
 * Respuesta cuando se produce un error.
 *
 * @author Indra
 *
 */
public final class RespuestaError {

	/** Idioma. */
	private String idioma = "es";

	/** Url callback. */
	private String urlCallback;

	/** Indica el mensaje de error general (label resources). */
	private String mensajeErrorGeneral;

	/** Indica el detalle del error. */
	private String mensajeErrorDetalle;

	/** Personalizacion. */
	private PersonalizacionEntidad personalizacion;

	/**
	 * @return the urlCallback
	 */
	public String getUrlCallback() {
		return urlCallback;
	}

	/**
	 * @param urlCallback
	 *                        the urlCallback to set
	 */
	public void setUrlCallback(final String urlCallback) {
		this.urlCallback = urlCallback;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensajeErrorGeneral() {
		return mensajeErrorGeneral;
	}

	/**
	 * @param mensaje
	 *                    the mensaje to set
	 */
	public void setMensajeErrorGeneral(final String mensaje) {
		this.mensajeErrorGeneral = mensaje;
	}

	/**
	 * Método de acceso a personalizacion.
	 *
	 * @return personalizacion
	 */
	public PersonalizacionEntidad getPersonalizacion() {
		return personalizacion;
	}

	/**
	 * Método para establecer personalizacion.
	 *
	 * @param personalizacion
	 *                            personalizacion a establecer
	 */
	public void setPersonalizacion(final PersonalizacionEntidad personalizacion) {
		this.personalizacion = personalizacion;
	}

	/**
	 * Método de acceso a mensajeErrorPersonalizado.
	 *
	 * @return mensajeErrorPersonalizado
	 */
	public String getMensajeErrorDetalle() {
		return mensajeErrorDetalle;
	}

	/**
	 * Método para establecer mensajeErrorPersonalizado.
	 *
	 * @param mensajeErrorPersonalizado
	 *                                      mensajeErrorPersonalizado a establecer
	 */
	public void setMensajeErrorDetalle(final String mensajeErrorPersonalizado) {
		this.mensajeErrorDetalle = mensajeErrorPersonalizado;
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
	 * @param idioma
	 *                   idioma a establecer
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

}
