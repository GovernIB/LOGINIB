package es.caib.loginib.core.service.model;

/**
 * Configuración Keycloak.
 * 
 * @author Indra
 */
public class ConfiguracionKeycloak {

	/** Configuracion. */
	private String configuracion;

	/** Atributo nif. */
	private String atributoNif;

	/** Atributo apellido1. */
	private String atributoApellido1;

	/** Atributo apellido2. */
	private String atributoApellido2;

	/**
	 * Método de acceso a configuracion.
	 * 
	 * @return configuracion
	 */
	public String getConfiguracion() {
		return configuracion;
	}

	/**
	 * Método para establecer configuracion.
	 * 
	 * @param configuracion
	 *                          configuracion a establecer
	 */
	public void setConfiguracion(final String configuracion) {
		this.configuracion = configuracion;
	}

	/**
	 * Método de acceso a atributoNif.
	 * 
	 * @return atributoNif
	 */
	public String getAtributoNif() {
		return atributoNif;
	}

	/**
	 * Método para establecer atributoNif.
	 * 
	 * @param atributoNif
	 *                        atributoNif a establecer
	 */
	public void setAtributoNif(final String atributoNif) {
		this.atributoNif = atributoNif;
	}

	/**
	 * Método de acceso a atributoApellido1.
	 * 
	 * @return atributoApellido1
	 */
	public String getAtributoApellido1() {
		return atributoApellido1;
	}

	/**
	 * Método para establecer atributoApellido1.
	 * 
	 * @param atributoApellido1
	 *                              atributoApellido1 a establecer
	 */
	public void setAtributoApellido1(final String atributoApellido1) {
		this.atributoApellido1 = atributoApellido1;
	}

	/**
	 * Método de acceso a atributoApellido2.
	 * 
	 * @return atributoApellido2
	 */
	public String getAtributoApellido2() {
		return atributoApellido2;
	}

	/**
	 * Método para establecer atributoApellido2.
	 * 
	 * @param atributoApellido2
	 *                              atributoApellido2 a establecer
	 */
	public void setAtributoApellido2(final String atributoApellido2) {
		this.atributoApellido2 = atributoApellido2;
	}

}
