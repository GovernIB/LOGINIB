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

	

	/** Saml id peticion.*/
	private String samlIdPeticion;

	/** Fecha ticket (nulo si no se ha iniciado sesion en clave). */
	private Date fechaTicket;

	/**
	 * @return the urlCallback
	 */
	public String getUrlCallback() {
		return urlCallback;
	}



	/**
	 * @param urlCallback the urlCallback to set
	 */
	public void setUrlCallback(String urlCallback) {
		this.urlCallback = urlCallback;
	}



	/**
	 * @return the samlIdPeticion
	 */
	public String getSamlIdPeticion() {
		return samlIdPeticion;
	}



	/**
	 * @param samlIdPeticion the samlIdPeticion to set
	 */
	public void setSamlIdPeticion(String samlIdPeticion) {
		this.samlIdPeticion = samlIdPeticion;
	}



	/**
	 * @return the fechaTicket
	 */
	public Date getFechaTicket() {
		return fechaTicket;
	}



	/**
	 * @param fechaTicket the fechaTicket to set
	 */
	public void setFechaTicket(Date fechaTicket) {
		this.fechaTicket = fechaTicket;
	}
	
	

}
