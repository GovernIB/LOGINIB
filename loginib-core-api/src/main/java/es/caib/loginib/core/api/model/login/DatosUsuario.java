package es.caib.loginib.core.api.model.login;

import java.util.Date;

/**
 * Datos usuario.
 *
 * @author indra
 *
 */
public final class DatosUsuario {

	/** Fecha ticket. */
	private Date fechaTicket;

	/** NIF. */
	private String nif;

	/** Nombre. */
	private String nombre;

	/** Apellidos. */
	private String apellidos;

	/** Apellido 1. */
	private String apellido1;

	/** Apellido 2. */
	private String apellido2;

	/** Nivel autenticacion. */
	private String nivelAutenticacion;

	/** QAA. */
	private Integer qaa;

	/** Datos representante. */
	private DatosRepresentante representante;

	/**
	 * Gets the nif.
	 *
	 * @return the nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Sets the nif.
	 *
	 * @param pNif the new nif
	 */
	public void setNif(final String pNif) {
		nif = pNif;
	}

	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param pNombre the new nombre
	 */
	public void setNombre(final String pNombre) {
		nombre = pNombre;
	}

	/**
	 * Gets the apellidos.
	 *
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Sets the apellidos.
	 *
	 * @param pApellidos the new apellidos
	 */
	public void setApellidos(final String pApellidos) {
		apellidos = pApellidos;
	}

	/**
	 * Gets the nivel autenticacion.
	 *
	 * @return the nivel autenticacion
	 */
	public String getNivelAutenticacion() {
		return nivelAutenticacion;
	}

	/**
	 * Sets the nivel autenticacion.
	 *
	 * @param pNivelAutenticacion the new nivel autenticacion
	 */
	public void setNivelAutenticacion(final String pNivelAutenticacion) {
		nivelAutenticacion = pNivelAutenticacion;
	}

	/**
	 * Método de acceso a apellido1.
	 *
	 * @return apellido1
	 */
	public String getApellido1() {
		return apellido1;
	}

	/**
	 * Método para establecer apellido1.
	 *
	 * @param pApellido1 apellido1 a establecer
	 */
	public void setApellido1(final String pApellido1) {
		apellido1 = pApellido1;
	}

	/**
	 * Método de acceso a apellido2.
	 *
	 * @return apellido2
	 */
	public String getApellido2() {
		return apellido2;
	}

	/**
	 * Método para establecer apellido2.
	 *
	 * @param pApellido2 apellido2 a establecer
	 */
	public void setApellido2(final String pApellido2) {
		apellido2 = pApellido2;
	}

	/**
	 * Gets the fecha ticket.
	 *
	 * @return the fecha ticket
	 */
	public Date getFechaTicket() {
		return fechaTicket;
	}

	/**
	 * Sets the fecha ticket.
	 *
	 * @param fechaTicket the new fecha ticket
	 */
	public void setFechaTicket(final Date fechaTicket) {
		this.fechaTicket = fechaTicket;
	}

	/**
	 * Método de acceso a representante.
	 *
	 * @return representante
	 */
	public DatosRepresentante getRepresentante() {
		return representante;
	}

	/**
	 * Método para establecer representante.
	 *
	 * @param representante representante a establecer
	 */
	public void setRepresentante(final DatosRepresentante representante) {
		this.representante = representante;
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

}
