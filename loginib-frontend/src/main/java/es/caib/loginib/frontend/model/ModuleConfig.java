/**
 *
 */
package es.caib.loginib.frontend.model;

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
	/** Properties. */
	private Properties propiedades;

	/** Directorio configuracion. */
	private String directorioConfiguracion;

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
		return entorno;
	}

	/**
	 * @param entorno the entorno to set
	 */
	public void setEntorno(final String entorno) {
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
	 * Comprueba si hay que omitir los desgloses.
	 *
	 * @return omitirDesglose
	 */
	public boolean getOmitirDesglose() {
		return Boolean.parseBoolean(propiedades.getProperty("desglose.omitirDesglose"));
	}

}
