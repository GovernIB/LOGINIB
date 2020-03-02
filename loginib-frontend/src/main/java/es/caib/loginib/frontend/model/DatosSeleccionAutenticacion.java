package es.caib.loginib.frontend.model;

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

}
