package es.caib.loginib.core.api.model.login;

import java.util.Date;

/**
 * Datos sesion.
 *
 * @author Indra
 *
 */
public final class DatosLogoutSesion {

	/** Url callback. */
	private String urlCallback;

	/** Entidad. */
	private String entidad;

	/** Saml id peticion. */
	private String samlIdPeticion;

	/** Fecha ticket (nulo si no se ha iniciado sesion en clave). */
	private Date fechaTicket;

	/** Identificador aplicacion. */
	private String aplicacion;

	/**
	 * @return the urlCallback
	 */
	public String getUrlCallback() {
		return urlCallback;
	}

	/**
	 * @param urlCallback
	 *            the urlCallback to set
	 */
	public void setUrlCallback(final String urlCallback) {
		this.urlCallback = urlCallback;
	}

	/**
	 * @return the samlIdPeticion
	 */
	public String getSamlIdPeticion() {
		return samlIdPeticion;
	}

	/**
	 * @param samlIdPeticion
	 *            the samlIdPeticion to set
	 */
	public void setSamlIdPeticion(final String samlIdPeticion) {
		this.samlIdPeticion = samlIdPeticion;
	}

	/**
	 * @return the fechaTicket
	 */
	public Date getFechaTicket() {
		return fechaTicket;
	}

	/**
	 * @param fechaTicket
	 *            the fechaTicket to set
	 */
	public void setFechaTicket(final Date fechaTicket) {
		this.fechaTicket = fechaTicket;
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
