package es.caib.loginib.core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import es.caib.loginib.core.api.exception.ConfiguracionException;

/**
 *
 * Acceso a propiedades aplicacion.
 *
 * @author Indra
 *
 */
@Component
public final class ModuleConfig {

	/** Properties. */
	private Properties propiedades;

	/** Directorio configuracion. */
	private String directorioConfiguracion;

	/** Inicio. */
	@PostConstruct
	public void init() {
		final String pathFileConfiguracion = System.getProperty("es.caib.loginib.properties.path");
		try (FileInputStream fis = new FileInputStream(pathFileConfiguracion);) {
			// Carga propiedades
			propiedades = new Properties();
			propiedades.load(fis);
			// Obtiene directorio configuracion
			final File file = new File(pathFileConfiguracion);
			directorioConfiguracion = file.getAbsoluteFile().getParent() + "/";
		} catch (final IOException e) {
			throw new ConfiguracionException(e);
		}
	}

	/**
	 * Método de acceso a pepsUrl.
	 *
	 * @return pepsUrl
	 */
	public String getPepsUrl() {
		return propiedades.getProperty("pepsUrl");
	}

	/**
	 * Método de acceso a pepsLogout.
	 *
	 * @return pepsLogout
	 */
	public String getPepsLogout() {
		return propiedades.getProperty("pepsLogout");
	}

	/**
	 * Método de acceso a loginCallbackClave.
	 *
	 * @return returnUrlExterna
	 */
	public String getLoginCallbackClave() {
		return propiedades.getProperty("loginCallbackClave");
	}

	/**
	 * Método de acceso a logoutCallbackClave.
	 *
	 * @return logoutCallbackClave
	 */
	public String getLogoutCallbackClave() {
		return propiedades.getProperty("logoutCallbackClave");
	}

	/**
	 * Método de acceso a timeoutTicketAutenticacion.
	 *
	 * @return timeoutTicketAutenticacion
	 */
	public Long getTimeoutTicketAutenticacion() {
		return Long.parseLong(propiedades.getProperty("timeoutTicketAutenticacion"));
	}

	/**
	 * Obtiene timeout para completar proceso autenticacion (login o logout).
	 *
	 * @return timeout para completar proceso autenticacion
	 */
	public Long getTimeoutProcesoAutenticacion() {
		return Long.parseLong(propiedades.getProperty("timeoutProcesoAutenticacion"));
	}

	/**
	 * Método de acceso a accesoClaveDeshabilitado.
	 *
	 * @return accesoClaveDeshabilitado
	 */
	public boolean isAccesoClaveDeshabilitado() {
		return Boolean.parseBoolean(propiedades.getProperty("deshabilitado"));
	}

	/**
	 * Método de acceso a timeoutInicioAutenticacion.
	 *
	 * @return loginRedireccionClave
	 */
	public String getLoginRedireccionClave() {
		return propiedades.getProperty("loginRedireccionClave");
	}

	/**
	 * Método de acceso a logoutRedireccionClave.
	 *
	 * @return logoutRedireccionClave
	 */
	public String getLogoutRedireccionClave() {
		return propiedades.getProperty("logoutRedireccionClave");
	}

	/**
	 * Método de acceso a accesoClaveSimulado.
	 *
	 * @return accesoClaveSimulado
	 */
	public boolean isAccesoClaveSimulado() {
		return Boolean.parseBoolean(propiedades.getProperty("simulado"));
	}

	/**
	 * Obtiene provider name.
	 *
	 * @param entidad
	 *                    entidad
	 * @return provider name
	 */
	public String getProviderName(final String entidad) {
		return propiedades.getProperty(entidad + ".providerName");
	}

	/**
	 * Obtiene ruta directorio configuracion
	 *
	 * @return directorio configuracion
	 */
	public String getDirectorioConfiguracion() {
		return directorioConfiguracion;
	}

	/**
	 * Método de devuelve el Timeout purga definitiva (dias).
	 *
	 * @return dias para la purga definitiva
	 */
	public Long getTimeoutPurga() {
		return Long.parseLong(propiedades.getProperty("timeoutPurga"));
	}

}
