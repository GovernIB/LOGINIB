package es.caib.loginib.rest.api.v1;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Peticion iniciar sesion.
 *
 * @author indra
 *
 */
@ApiModel(description = "Datos para iniciar proceso de inicio de sesión")
public final class RLoginParams {

	/**
	 * Código Entidad.
	 */
	@ApiModelProperty(value = "Código Entidad (DIR3)", required = true)
	private String entidad;

	/**
	 * Url Callback login.
	 */
	@ApiModelProperty(value = "Url Callback login", required = true)
	private String urlCallback;

	/**
	 * Metodos autenticacion clave (separados por ;). Valores:
	 * ANONIMO;CERTIFICADO;CLAVE_PIN;CLAVE_PERMANENTE.
	 */
	@ApiModelProperty(value = "Metodos autenticacion clave (separados por ;). Valores: ANONIMO;CERTIFICADO;CLAVE_PIN;CLAVE_PERMANENTE.", required = true)
	private String metodosAutenticacion;

	/**
	 * Nivel autenticacion: 1 (Bajo), 2 (Medio) y 3 (Alto).
	 */
	@ApiModelProperty(value = "Nivel autenticacion: 1 (Bajo), 2 (Medio) y 3 (Alto).", required = true)
	private int qaa;

	/**
	 * Idioma.
	 */
	@ApiModelProperty(value = "Idioma", required = true)
	private String idioma;

	/**
	 * Forzar autenticación.
	 */
	@ApiModelProperty(value = "Forzar autenticación", required = true)
	private boolean forzarAutenticacion;

	/**
	 * Identificador aplicacion.
	 */
	@ApiModelProperty(value = "Aplicacion", required = false)
	private String aplicacion;

	/**
	 * Método de acceso a urlCallbackLogin.
	 *
	 * @return urlCallbackLogin
	 */
	public String getUrlCallback() {
		return urlCallback;
	}

	/**
	 * Método para establecer urlCallbackLogin.
	 *
	 * @param urlCallbackLogin
	 *            urlCallbackLogin a establecer
	 */
	public void setUrlCallback(final String urlCallbackLogin) {
		this.urlCallback = urlCallbackLogin;
	}

	/**
	 * Método de acceso a metodosAutenticacion.
	 *
	 * @return metodosAutenticacion
	 */
	public String getMetodosAutenticacion() {
		return metodosAutenticacion;
	}

	/**
	 * Método para establecer metodosAutenticacion.
	 *
	 * @param metodosAutenticacion
	 *            metodosAutenticacion a establecer
	 */
	public void setMetodosAutenticacion(final String metodosAutenticacion) {
		this.metodosAutenticacion = metodosAutenticacion;
	}

	/**
	 * Método de acceso a qaa.
	 *
	 * @return qaa
	 */
	public int getQaa() {
		return qaa;
	}

	/**
	 * Método para establecer qaa.
	 *
	 * @param qaa
	 *            qaa a establecer
	 */
	public void setQaa(final int qaa) {
		this.qaa = qaa;
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
	 *            idioma a establecer
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Método de acceso a forzarAutenticacion.
	 *
	 * @return forzarAutenticacion
	 */
	public boolean isForzarAutenticacion() {
		return forzarAutenticacion;
	}

	/**
	 * Método para establecer forzarAutenticacion.
	 *
	 * @param forzarAutenticacion
	 *            forzarAutenticacion a establecer
	 */
	public void setForzarAutenticacion(final boolean forzarAutenticacion) {
		this.forzarAutenticacion = forzarAutenticacion;
	}

	/**
	 * Método de acceso a entidad.
	 *
	 * @return entidad
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * Método para establecer entidad.
	 *
	 * @param entidad
	 *            entidad a establecer
	 */
	public void setEntidad(final String entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the aplicacion
	 */
	public String getAplicacion() {
		return aplicacion;
	}

	/**
	 * @param aplicacion
	 *            the aplicacion to set
	 */
	public void setAplicacion(final String aplicacion) {
		this.aplicacion = aplicacion;
	}

}
