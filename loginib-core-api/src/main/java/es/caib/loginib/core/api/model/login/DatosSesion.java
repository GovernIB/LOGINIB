package es.caib.loginib.core.api.model.login;

import java.util.Date;

/**
 * Datos sesion.
 * 
 * @author Indra
 * 
 */
public final class DatosSesion {

	/** Idps. */
	private String idps;

	/** Fecha inicio sesion. */
	private Date fechaInicioSesion;

	/** Fecha ticket (nulo si no se ha iniciado sesion en clave). */
	private Date fechaTicket;

	/** Idioma. */
	private String idioma;

	/** QAA. */
	private Integer qaa;

	/** Force auth. */
	private boolean forceAuth;

	/** Saml id peticion.*/
	private String samlIdPeticion;
	
	/**
	 * Gets the idps.
	 * 
	 * @return the idps
	 */
	public String getIdps() {
		return idps;
	}

	/**
	 * Sets the idps.
	 * 
	 * @param pIdps
	 *            the idps to set
	 */
	public void setIdps(final String pIdps) {
		idps = pIdps;
	}

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
	 * @param pFecha
	 *            the fecha to set
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
	 * @param pIdioma
	 *            the idioma to set
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
	 * @param pFechaTicket
	 *            the fechaTicket to set
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
	 * @param qaa
	 *            the qaa to set
	 */
	public void setQaa(Integer qaa) {
		this.qaa = qaa;
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
	 * @param forceAuth
	 *            the new force auth
	 */
	public void setForceAuth(boolean forceAuth) {
		this.forceAuth = forceAuth;
	}

	public String getSamlIdPeticion() {
		return samlIdPeticion;
	}

	public void setSamlIdPeticion(String samlIdPeticion) {
		this.samlIdPeticion = samlIdPeticion;
	}

}
