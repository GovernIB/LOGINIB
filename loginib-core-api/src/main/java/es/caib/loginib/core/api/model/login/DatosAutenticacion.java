package es.caib.loginib.core.api.model.login;

import java.util.Date;
import java.util.Map;

import es.caib.loginib.core.api.model.login.types.TypeIdp;

/**
 * Datos usuario.
 *
 * @author indra
 *
 */
public final class DatosAutenticacion {

	/** Id sesión. */
	private String idSesion;

	/** Fecha ticket. */
	private Date fechaTicket;

	/** QAA. */
	private Integer qaa;

	/** Nivel autenticacion. */
	private TypeIdp metodoAutenticacion;

	/** Persona / Entidad autenticada. */
	private DatosPersona autenticado;

	/** Datos representante. */
	private DatosPersona representante;

	/** Params app **/
	private Map<String, String> paramsApp;
	public static String PARAM_FORZAR_DESGLOSE = "forzarDesglose";
	public static String PARAM_FORZAR_DESGLOSE_VALOR = "S";

	/**
	 * Constructor.
	 *
	 * @param idSesion
	 * @param fechaTicket
	 * @param qaa
	 * @param metodoAutenticacion
	 * @param autenticado
	 * @param representante
	 */
	public DatosAutenticacion(final String idSesion, final Date fechaTicket, final Integer qaa,
			final TypeIdp metodoAutenticacion, final DatosPersona autenticado, final DatosPersona representante,
			final Map<String, String> params) {
		super();
		this.idSesion = idSesion;
		this.fechaTicket = fechaTicket;
		this.qaa = qaa;
		this.metodoAutenticacion = metodoAutenticacion;
		this.autenticado = autenticado;
		this.representante = representante;
		this.paramsApp = params;
	}

	/** Muestra en un string los datos de autenticacion. */
	public String print() {
		return "QAA= " + getQaa() + ", Metodo=" + getMetodoAutenticacion().toString() + ", Nif="
				+ getAutenticado().print() + (getRepresentante() != null ? getRepresentante().print() : "");
	}

	/**
	 * Método de acceso a fechaTicket.
	 *
	 * @return fechaTicket
	 */
	public Date getFechaTicket() {
		return fechaTicket;
	}

	/**
	 * Método para establecer fechaTicket.
	 *
	 * @param fechaTicket fechaTicket a establecer
	 */
	public void setFechaTicket(final Date fechaTicket) {
		this.fechaTicket = fechaTicket;
	}

	/**
	 * Método de acceso a autenticacion.
	 *
	 * @return autenticacion
	 */
	public DatosPersona getAutenticado() {
		return autenticado;
	}

	/**
	 * Método para establecer autenticacion.
	 *
	 * @param autenticacion autenticacion a establecer
	 */
	public void setAutenticado(final DatosPersona autenticacion) {
		this.autenticado = autenticacion;
	}

	/**
	 * Método de acceso a metodoAutenticacion.
	 *
	 * @return metodoAutenticacion
	 */
	public TypeIdp getMetodoAutenticacion() {
		return metodoAutenticacion;
	}

	/**
	 * Método para establecer metodoAutenticacion.
	 *
	 * @param metodoAutenticacion metodoAutenticacion a establecer
	 */
	public void setMetodoAutenticacion(final TypeIdp metodoAutenticacion) {
		this.metodoAutenticacion = metodoAutenticacion;
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
	 * Método de acceso a representante.
	 *
	 * @return representante
	 */
	public DatosPersona getRepresentante() {
		return representante;
	}

	/**
	 * Método para establecer representante.
	 *
	 * @param representante representante a establecer
	 */
	public void setRepresentante(final DatosPersona representante) {
		this.representante = representante;
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

	public Map<String, String> getParamsApp() {
		return paramsApp;
	}

	public void setParamsApp(final Map<String, String> paramsApp) {
		this.paramsApp = paramsApp;
	}

}
