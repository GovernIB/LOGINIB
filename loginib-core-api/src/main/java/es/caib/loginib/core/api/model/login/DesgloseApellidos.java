package es.caib.loginib.core.api.model.login;

import java.util.Date;

/**
 * Dominio.
 *
 * @author Indra
 *
 */

public class DesgloseApellidos extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	private String nif;

	private String nombre;

	private String apellidoComp;

	private String apellido1;

	private String apellido2;

	private String nombreCompleto;

	private Date fechaCreacion;

	private Date fechaActualizacion;

	private String ticket;

	/**
	 * @return the nif
	 */
	public final String getNif() {
		return nif;
	}

	/**
	 * @param nif the nif to set
	 */
	public final void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 * @return the nombre
	 */
	public final String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellidoComp
	 */
	public final String getApellidoComp() {
		return apellidoComp;
	}

	/**
	 * @param apellidoComp the apellidoComp to set
	 */
	public final void setApellidoComp(String apellidoComp) {
		this.apellidoComp = apellidoComp;
	}

	/**
	 * @return the apellido1
	 */
	public final String getApellido1() {
		return apellido1;
	}

	/**
	 * @param apellido1 the apellido1 to set
	 */
	public final void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	/**
	 * @return the apellido2
	 */
	public final String getApellido2() {
		return apellido2;
	}

	/**
	 * @param apellido2 the apellido2 to set
	 */
	public final void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	/**
	 * @return the nombreCompleto
	 */
	public final String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * @param nombreCompleto the nombreCompleto to set
	 */
	public final void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * @return the fechaCreacion
	 */
	public final Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public final void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the fechaActualizacion
	 */
	public final Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public final void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	/**
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

}
