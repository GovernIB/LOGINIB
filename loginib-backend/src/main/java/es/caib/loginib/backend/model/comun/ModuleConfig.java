/**
 *
 */
package es.caib.loginib.backend.model.comun;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.annotation.PostConstruct;

import es.caib.loginib.core.api.exception.ConfiguracionException;

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

	/** Inicio. */
	@PostConstruct
	public void init() {
		final String pathFileConfiguracion = System.getProperty("es.caib.loginib.properties.path");
		try (FileInputStream fis = new FileInputStream(pathFileConfiguracion);) {
			// Carga propiedades
			propiedades = new Properties();
			propiedades.load(new InputStreamReader(fis, Charset.forName("UTF-8")));
			// Obtiene directorio configuracion
			final File file = new File(pathFileConfiguracion);
			directorioConfiguracion = file.getAbsoluteFile().getParent() + "/";
		} catch (final IOException e) {
			throw new ConfiguracionException(e);
		}
	}

	/**
	 * @return the entorno
	 */
	public String getEntorno() {
		// return propiedades.getProperty("entorno");
		return entorno;
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
