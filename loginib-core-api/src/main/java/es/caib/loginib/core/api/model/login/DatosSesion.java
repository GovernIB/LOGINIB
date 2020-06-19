package es.caib.loginib.core.api.model.login;

/**
 * Datos sesion.
 *
 * @author Indra
 *
 */
public final class DatosSesion {

	/** Id sesion. */
	private DatosSesionData sesion;

	/** Accesos permitidos. */
	private AccesosPermitidos accesosPermitidos;

	/** Customizacion entidad. */
	private PersonalizacionEntidad personalizacionEntidad;

	/**
	 * Método de acceso a sesion.
	 * 
	 * @return sesion
	 */
	public DatosSesionData getSesion() {
		return sesion;
	}

	/**
	 * Método para establecer sesion.
	 * 
	 * @param sesion
	 *                   sesion a establecer
	 */
	public void setSesion(final DatosSesionData sesion) {
		this.sesion = sesion;
	}

	/**
	 * Método de acceso a accesosPermitidos.
	 * 
	 * @return accesosPermitidos
	 */
	public AccesosPermitidos getAccesosPermitidos() {
		return accesosPermitidos;
	}

	/**
	 * Método para establecer accesosPermitidos.
	 * 
	 * @param accesosPermitidos
	 *                              accesosPermitidos a establecer
	 */
	public void setAccesosPermitidos(final AccesosPermitidos accesosPermitidos) {
		this.accesosPermitidos = accesosPermitidos;
	}

	/**
	 * Método de acceso a personalizacionEntidad.
	 * 
	 * @return personalizacionEntidad
	 */
	public PersonalizacionEntidad getPersonalizacionEntidad() {
		return personalizacionEntidad;
	}

	/**
	 * Método para establecer personalizacionEntidad.
	 * 
	 * @param personalizacionEntidad
	 *                                   personalizacionEntidad a establecer
	 */
	public void setPersonalizacionEntidad(final PersonalizacionEntidad personalizacionEntidad) {
		this.personalizacionEntidad = personalizacionEntidad;
	}

}
