package es.caib.loginib.frontend.model;

import es.caib.loginib.core.api.model.login.PersonalizacionEntidad;

/**
 * Datos para iniciar sesion en clave.
 *
 * @author Indra
 *
 */
public final class DatosSeleccionAutenticacion {

	/** Id sesion. */
	private String idSesion;

	/** Idioma. */
	private String idioma;

	/** Permite clave. */
	private boolean clave;

	/** Permite anonimo. */
	private boolean anonimo;

	/** Permite clientcert. */
	private boolean clientCert;

	/** Indica como se muestra client cert (opción o link). */
	private boolean clientCertSegundoPlano;

	/** Permite usuario password. */
	private boolean usuarioPassword;

	/** Customizacion entidad. */
	private PersonalizacionEntidad personalizacion;

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
	 * @param idSesion
	 *                     idSesion a establecer
	 */
	public void setIdSesion(final String idSesion) {
		this.idSesion = idSesion;
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
	 * @param idioma
	 *                   idioma a establecer
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Método de acceso a clave.
	 *
	 * @return clave
	 */
	public boolean isClave() {
		return clave;
	}

	/**
	 * Método para establecer clave.
	 *
	 * @param clave
	 *                  clave a establecer
	 */
	public void setClave(final boolean clave) {
		this.clave = clave;
	}

	/**
	 * Método de acceso a anonimo.
	 *
	 * @return anonimo
	 */
	public boolean isAnonimo() {
		return anonimo;
	}

	/**
	 * Método para establecer anonimo.
	 *
	 * @param anonimo
	 *                    anonimo a establecer
	 */
	public void setAnonimo(final boolean anonimo) {
		this.anonimo = anonimo;
	}

	/**
	 * Método de acceso a clientCert.
	 *
	 * @return clientCert
	 */
	public boolean isClientCert() {
		return clientCert;
	}

	/**
	 * Método para establecer clientCert.
	 *
	 * @param clientCert
	 *                       clientCert a establecer
	 */
	public void setClientCert(final boolean clientCert) {
		this.clientCert = clientCert;
	}

	/**
	 * @return the clientCertSegundoPlano
	 */
	public boolean isClientCertSegundoPlano() {
		return clientCertSegundoPlano;
	}

	/**
	 * @param clientCertSegundoPlano
	 *                                   the clientCertSegundoPlano to set
	 */
	public void setClientCertSegundoPlano(final boolean clientCertSegundoPlano) {
		this.clientCertSegundoPlano = clientCertSegundoPlano;
	}

	/**
	 * Método de acceso a usuarioPassword.
	 *
	 * @return usuarioPassword
	 */
	public boolean isUsuarioPassword() {
		return usuarioPassword;
	}

	/**
	 * Método para establecer usuarioPassword.
	 *
	 * @param usuarioPassword
	 *                            usuarioPassword a establecer
	 */
	public void setUsuarioPassword(final boolean usuarioPassword) {
		this.usuarioPassword = usuarioPassword;
	}

	/**
	 * Método de acceso a customizacion.
	 * 
	 * @return customizacion
	 */
	public PersonalizacionEntidad getPersonalizacion() {
		return personalizacion;
	}

	/**
	 * Método para establecer customizacion.
	 * 
	 * @param customizacion
	 *                          customizacion a establecer
	 */
	public void setPersonalizacion(final PersonalizacionEntidad customizacion) {
		this.personalizacion = customizacion;
	}

}
