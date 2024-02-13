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
	 * Url Callback error.
	 */
	@ApiModelProperty(value = "Url Callback error", required = true)
	private String urlCallbackError;

	/**
	 * Metodos autenticacion clave (separados por ;). Valores:
	 * ANONIMO;CLAVE_CERTIFICADO;CLAVE_PIN;CLAVE_PERMANENTE;CLAVE_MOVIL;CLIENTCERT;USUARIO_PASSWORD.
	 */
	@ApiModelProperty(value = "Metodos autenticacion clave (separados por ;). Valores: ANONIMO;CLAVE_CERTIFICADO;CLAVE_PIN;CLAVE_PERMANENTE;CLAVE_MOVIL;CLIENTCERT;USUARIO_PASSWORD", required = true)
	private String metodosAutenticacion;

	/**
	 * Nivel autenticacion: 1 (Bajo), 2 (Medio) y 3 (Alto).
	 */
	@ApiModelProperty(value = "Nivel autenticacion: 1 (Bajo), 2 (Medio) y 3 (Alto).", required = false)
	private Integer qaa;

	/**
	 * Idioma.
	 */
	@ApiModelProperty(value = "Idioma", required = true)
	private String idioma;

	/**
	 * Iniciar Cl@ve automatico (para Carpeta Ciudadana) .
	 */
	@ApiModelProperty(value = "Iniciar Cl@ve automatico", required = true)
	private boolean inicioClaveAutomatico;
	/**
	 * Forzar autenticación (para Cl@ve).
	 */
	@ApiModelProperty(value = "Forzar autenticación", required = true)
	private boolean forzarAutenticacion;

	/**
	 * Identificador aplicación.
	 */
	@ApiModelProperty(value = "Aplicación", required = false)
	private String aplicacion;

	/**
	 * Indica si genera auditoría autenticación (con evidencias autenticación).
	 */
	@ApiModelProperty(value = "Indica si genera auditoría autenticación (con evidencias autenticación)", required = false)
	private boolean auditar;

	/**
	 * Parametros extras de la peticion
	 */
	@ApiModelProperty(value = "Parametros extras de la peticion", required = false)
	private RParamsApp paramsApp;
	/** Parametro para forzar el desglose **/
	public static String PARAM_FORZAR_DESGLOSE = "forzarDesglose";
	/** Valor para el parametro de forzar el desglose **/
	public static String PARAM_FORZAR_DESGLOSE_VALOR = "S";

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
	 * @param urlCallbackLogin urlCallbackLogin a establecer
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
	 * @param metodosAutenticacion metodosAutenticacion a establecer
	 */
	public void setMetodosAutenticacion(final String metodosAutenticacion) {
		this.metodosAutenticacion = metodosAutenticacion;
	}

	/**
	 * Método de acceso a qaa.
	 *
	 * @return qaa
	 */
	public Integer getQaa() {
		return qaa;
	}

	/**
	 * Método para establecer qaa.
	 *
	 * @param qaa qaa a establecer
	 */
	public void setQaa(final Integer qaa) {
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
	 * @param idioma idioma a establecer
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Método de acceso a inicioClaveAutomatico.
	 *
	 * @return inicioClaveAutomatico
	 */
	public boolean isInicioClaveAutomatico() {
		return inicioClaveAutomatico;
	}

	/**
	 * Método para establecer inicioClaveAutomatico.
	 *
	 * @param inicioClaveAutomatico inicioClaveAutomatico a establecer
	 */
	public void setInicioClaveAutomatico(final boolean inicioClaveAutomatico) {
		this.inicioClaveAutomatico = inicioClaveAutomatico;
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
	 * @param forzarAutenticacion forzarAutenticacion a establecer
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
	 * @param entidad entidad a establecer
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
	 * @param aplicacion the aplicacion to set
	 */
	public void setAplicacion(final String aplicacion) {
		this.aplicacion = aplicacion;
	}

	/**
	 * @return the urlCallbackError
	 */
	public String getUrlCallbackError() {
		return urlCallbackError;
	}

	/**
	 * @param urlCallbackError the urlCallbackError to set
	 */
	public void setUrlCallbackError(final String urlCallbackError) {
		this.urlCallbackError = urlCallbackError;
	}

	/**
	 * Método de acceso a auditar.
	 *
	 * @return auditar
	 */
	public boolean isAuditar() {
		return auditar;
	}

	/**
	 * Método para establecer auditar.
	 *
	 * @param auditar auditar a establecer
	 */
	public void setAuditar(final boolean auditar) {
		this.auditar = auditar;
	}

	public RParamsApp getParamsApp() {
		return paramsApp;
	}

	public void setParamsApp(final RParamsApp paramsApp) {
		this.paramsApp = paramsApp;
	}

}
