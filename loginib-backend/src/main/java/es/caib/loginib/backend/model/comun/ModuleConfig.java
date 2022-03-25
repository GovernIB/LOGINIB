/**
 *
 */
package es.caib.loginib.backend.model.comun;

import java.util.Properties;

/**
 * @author Indra
 *
 */
public final class ModuleConfig {
	/** Directorio configuracion. */
	private String directorioConfiguracion;

	/** Properties. */
	private Properties propiedades;

	/** Entorno. **/
	private String entorno;

	/** Version. **/
	private String version;

	/** Commit svn. **/
	private String commitSvn;

	/** Commit git. **/
	private String commitGit;

	/**
	 * @return the entorno
	 */
	public String getEntorno() {
		// return propiedades.getProperty("entorno");
		return this.entorno;
	}

	/**
	 * @param entorno the entorno to set
	 */
	public void setEntorno(final String entorno) {
		// propiedades.setProperty("entorno", entorno);
		this.entorno = entorno;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(final String version) {
		this.version = version;
	}

	public String getCommitSvn() {
		return commitSvn;
	}

	public void setCommitSvn(final String commitSvn) {
		this.commitSvn = commitSvn;
	}

	public String getCommitGit() {
		return commitGit;
	}

	public void setCommitGit(final String commitGit) {
		this.commitGit = commitGit;
	}

}
