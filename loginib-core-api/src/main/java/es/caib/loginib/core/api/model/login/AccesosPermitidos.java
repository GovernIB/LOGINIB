package es.caib.loginib.core.api.model.login;

/**
 * Indica accesos permitidos para la sesión.
 *
 * @author Indra
 *
 */
public class AccesosPermitidos {

	/**
	 * Acceso anónimo automático (si está habilitado acceso anónimo auto y no hay
	 * otro método).
	 */
	private boolean accesoAnonimoAuto;

	/**
	 * Acceso Anonimo.
	 */
	private boolean accesoAnonimo;

	/**
	 * Acceso Clave.
	 */
	private boolean accesoClave;

	/**
	 * Acceso Clave simulado (si está habilitado acceso clave y está simulado).
	 */
	private boolean accesoClaveSimulado;

	/**
	 * Acceso ClientCert.
	 */
	private boolean accesoClientCert;

	/**
	 * Acceso ClientCert mediante link (sino se mostrará como opción).
	 */
	private boolean accesoClientCertLink;

	/**
	 * Acceso Usuario/Password.
	 */
	private boolean accesoUsuarioPassword;

	/**
	 * Método de acceso a accesoAnonimoAuto.
	 *
	 * @return accesoAnonimoAuto
	 */
	public boolean isAccesoAnonimoAuto() {
		return accesoAnonimoAuto;
	}

	/**
	 * Método para establecer accesoAnonimoAuto.
	 *
	 * @param accesoAnonimoAuto
	 *                              accesoAnonimoAuto a establecer
	 */
	public void setAccesoAnonimoAuto(final boolean accesoAnonimoAuto) {
		this.accesoAnonimoAuto = accesoAnonimoAuto;
	}

	/**
	 * Método de acceso a accesoAnonimo.
	 *
	 * @return accesoAnonimo
	 */
	public boolean isAccesoAnonimo() {
		return accesoAnonimo;
	}

	/**
	 * Método para establecer accesoAnonimo.
	 *
	 * @param accesoAnonimo
	 *                          accesoAnonimo a establecer
	 */
	public void setAccesoAnonimo(final boolean accesoAnonimo) {
		this.accesoAnonimo = accesoAnonimo;
	}

	/**
	 * Método de acceso a accesoClave.
	 *
	 * @return accesoClave
	 */
	public boolean isAccesoClave() {
		return accesoClave;
	}

	/**
	 * Método para establecer accesoClave.
	 *
	 * @param accesoClave
	 *                        accesoClave a establecer
	 */
	public void setAccesoClave(final boolean accesoClave) {
		this.accesoClave = accesoClave;
	}

	/**
	 * Método de acceso a accesoClientCert.
	 *
	 * @return accesoClientCert
	 */
	public boolean isAccesoClientCert() {
		return accesoClientCert;
	}

	/**
	 * Método para establecer accesoClientCert.
	 *
	 * @param accesoClientCert
	 *                             accesoClientCert a establecer
	 */
	public void setAccesoClientCert(final boolean accesoClientCert) {
		this.accesoClientCert = accesoClientCert;
	}

	/**
	 * Método de acceso a accesoUsuarioPassword.
	 *
	 * @return accesoUsuarioPassword
	 */
	public boolean isAccesoUsuarioPassword() {
		return accesoUsuarioPassword;
	}

	/**
	 * Método para establecer accesoUsuarioPassword.
	 *
	 * @param accesoUsuarioPassword
	 *                                  accesoUsuarioPassword a establecer
	 */
	public void setAccesoUsuarioPassword(final boolean accesoUsuarioPassword) {
		this.accesoUsuarioPassword = accesoUsuarioPassword;
	}

	/**
	 * Método de acceso a accesoClaveSimulado.
	 *
	 * @return accesoClaveSimulado
	 */
	public boolean isAccesoClaveSimulado() {
		return accesoClaveSimulado;
	}

	/**
	 * Método para establecer accesoClaveSimulado.
	 *
	 * @param accesoClaveSimulado
	 *                                accesoClaveSimulado a establecer
	 */
	public void setAccesoClaveSimulado(final boolean accesoClaveSimulado) {
		this.accesoClaveSimulado = accesoClaveSimulado;
	}

	/**
	 * Método de acceso a accesoClientCertLink.
	 * 
	 * @return accesoClientCertLink
	 */
	public boolean isAccesoClientCertLink() {
		return accesoClientCertLink;
	}

	/**
	 * Método para establecer accesoClientCertLink.
	 * 
	 * @param accesoClientCertLink
	 *                                 accesoClientCertLink a establecer
	 */
	public void setAccesoClientCertLink(final boolean accesoClientCertLink) {
		this.accesoClientCertLink = accesoClientCertLink;
	}

}
