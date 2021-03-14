package es.caib.loginib.core.api.model.login;

import java.util.Date;
import java.util.List;
import java.util.Map;

import es.caib.loginib.core.api.model.login.types.TypeIdp;

/**
 * Datos sesion (almacenados).
 *
 * @author Indra
 *
 */
public final class DatosSesionData {

	/** Id sesion. */
	private String idSesion;

	/** Idps solicitados en la sesión. */
	private List<TypeIdp> idps;

	/** Entidad. */
	private String entidad;

	/** Fecha inicio sesion. */
	private Date fechaInicioSesion;

	/** Fecha ticket (nulo si no se ha iniciado sesion en clave). */
	private Date fechaTicket;

	/** Idioma. */
	private String idioma;

	/** QAA solicitado. */
	private Integer qaa;

	/** QAA autenticacion. */
	private Integer qaaAutenticacion;

	/** Inicia Cl@ve Automatico. */
	private boolean iniClaAuto;

	/** Force auth. */
	private boolean forceAuth;

	/** Saml id peticion. */
	private String samlIdPeticion;

	/** Url callback. */
	private String urlCallback;

	/** Url callback en caso de error. */
	private String urlCallbackError;

	/** Aplicacion. **/
	private String aplicacion;

	/** Auditar. */
	private boolean auditar;

	/** Params app **/
	private Map<String, String> paramsApp;

	/**
	 * Gets the fecha.
	 *
	 * @return the fecha
	 */
	public Date getFechaInicioSesion() {
		return fechaInicioSesion;
	}

	/**
	 * Sets the fecha.
	 *
	 * @param pFecha the fecha to set
	 */
	public void setFechaInicioSesion(final Date pFecha) {
		fechaInicioSesion = pFecha;
	}

	/**
	 * Gets the idioma.
	 *
	 * @return the idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Sets the idioma.
	 *
	 * @param pIdioma the idioma to set
	 */
	public void setIdioma(final String pIdioma) {
		idioma = pIdioma;
	}

	/**
	 * Gets the fecha ticket.
	 *
	 * @return the fechaTicket
	 */
	public Date getFechaTicket() {
		return fechaTicket;
	}

	/**
	 * Sets the fecha ticket.
	 *
	 * @param pFechaTicket the fechaTicket to set
	 */
	public void setFechaTicket(final Date pFechaTicket) {
		fechaTicket = pFechaTicket;
	}

	/**
	 * @return the qaa
	 */
	public Integer getQaa() {
		return qaa;
	}

	/**
	 * @param qaa the qaa to set
	 */
	public void setQaa(final Integer qaa) {
		this.qaa = qaa;
	}

	/**
	 * Checks if is inicia Cl@ve Automatico.
	 *
	 * @return true, if is force auth
	 */
	public boolean isIniClaAuto() {
		return iniClaAuto;
	}

	/**
	 * Sets the inicia Cl@ve Automatico.
	 *
	 * @param iniClaAuto the new inicia Cl@ve Automatico
	 */
	public void setIniClaAuto(final boolean iniClaAuto) {
		this.iniClaAuto = iniClaAuto;
	}

	/**
	 * Checks if is force auth.
	 *
	 * @return true, if is force auth
	 */
	public boolean isForceAuth() {
		return forceAuth;
	}

	/**
	 * Sets the force auth.
	 *
	 * @param forceAuth the new force auth
	 */
	public void setForceAuth(final boolean forceAuth) {
		this.forceAuth = forceAuth;
	}

	public String getSamlIdPeticion() {
		return samlIdPeticion;
	}

	public void setSamlIdPeticion(final String samlIdPeticion) {
		this.samlIdPeticion = samlIdPeticion;
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
	 * Método de acceso a idps.
	 *
	 * @return idps
	 */
	public List<TypeIdp> getIdps() {
		return idps;
	}

	/**
	 * Método para establecer idps.
	 *
	 * @param idps idps a establecer
	 */
	public void setIdps(final List<TypeIdp> idps) {
		this.idps = idps;
	}

	/**
	 * Método de acceso a urlCallback.
	 *
	 * @return urlCallback
	 */
	public String getUrlCallback() {
		return urlCallback;
	}

	/**
	 * Método para establecer urlCallback.
	 *
	 * @param urlCallback urlCallback a establecer
	 */
	public void setUrlCallback(final String urlCallback) {
		this.urlCallback = urlCallback;
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
	 * Método de acceso a qaaAutenticacion.
	 *
	 * @return qaaAutenticacion
	 */
	public Integer getQaaAutenticacion() {
		return qaaAutenticacion;
	}

	/**
	 * Método para establecer qaaAutenticacion.
	 *
	 * @param qaaAutenticacion qaaAutenticacion a establecer
	 */
	public void setQaaAutenticacion(final Integer qaaAutenticacion) {
		this.qaaAutenticacion = qaaAutenticacion;
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

	public Map<String, String> getParamsApp() {
		return paramsApp;
	}

	public void setParamsApp(final Map<String, String> paramsApp) {
		this.paramsApp = paramsApp;
	}

}
