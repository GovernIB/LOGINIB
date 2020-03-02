package es.caib.loginib.core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.springframework.stereotype.Component;

import es.caib.loginib.core.api.exception.ConfiguracionException;
import es.caib.loginib.core.api.exception.PluginErrorException;

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
		return propiedades.getProperty("clave.pepsUrl");
	}

	/**
	 * Método de acceso a pepsLogout.
	 *
	 * @return pepsLogout
	 */
	public String getPepsLogout() {
		return propiedades.getProperty("clave.pepsLogout");
	}

	/**
	 * Método de acceso a loginCallbackClave.
	 *
	 * @return returnUrlExterna
	 */
	public String getLoginCallbackClave() {
		return propiedades.getProperty("clave.loginCallbackClave");
	}

	/**
	 * Método de acceso a logoutCallbackClave.
	 *
	 * @return logoutCallbackClave
	 */
	public String getLogoutCallbackClave() {
		return propiedades.getProperty("clave.logoutCallbackClave");
	}

	/**
	 * Método de acceso a timeoutTicketAutenticacion.
	 *
	 * @return timeoutTicketAutenticacion
	 */
	public Long getTimeoutTicketAutenticacion() {
		return Long.parseLong(propiedades.getProperty("procesos.timeoutTicketAutenticacion"));
	}

	/**
	 * Obtiene timeout para completar proceso autenticacion (login o logout).
	 *
	 * @return timeout para completar proceso autenticacion
	 */
	public Long getTimeoutProcesoAutenticacion() {
		return Long.parseLong(propiedades.getProperty("procesos.timeoutProcesoAutenticacion"));
	}

	/**
	 * Método de acceso a accesoClaveDeshabilitado.
	 *
	 * @return accesoClaveDeshabilitado
	 */
	public boolean isAccesoClaveDeshabilitado() {
		return Boolean.parseBoolean(propiedades.getProperty("clave.deshabilitado"));
	}

	/**
	 * Método de acceso a accesoClientCertDeshabilitado.
	 *
	 * @return accesoClientCertDeshabilitado
	 */
	public boolean isAccesoClientCertDeshabilitado() {
		return Boolean.parseBoolean(propiedades.getProperty("clientCert.deshabilitado"));
	}

	/**
	 * Método de acceso a timeoutInicioAutenticacion.
	 *
	 * @return loginRedireccionClave
	 */
	public String getLoginRedireccionClave() {
		return propiedades.getProperty("clave.loginRedireccionClave");
	}

	/**
	 * Método de acceso a logoutRedireccionClave.
	 *
	 * @return logoutRedireccionClave
	 */
	public String getLogoutRedireccionClave() {
		return propiedades.getProperty("clave.logoutRedireccionClave");
	}

	/**
	 * Método de acceso a accesoClaveSimulado.
	 *
	 * @return accesoClaveSimulado
	 */
	public boolean isAccesoClaveSimulado() {
		return Boolean.parseBoolean(propiedades.getProperty("clave.simulado"));
	}

	/**
	 * Obtiene provider name.
	 *
	 * @param entidad
	 *                    entidad
	 * @return provider name
	 */
	public String getProviderName(final String entidad) {
		return propiedades.getProperty("clave." + entidad + ".providerName");
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
		return Long.parseLong(propiedades.getProperty("procesos.timeoutPurga"));
	}

	/**
	 * Método usado para ClientCert.
	 *
	 * @return dias para la purga definitiva
	 */
	public String getClientCertMetodo() {
		return propiedades.getProperty("clientCert.metodo");
	}

	/**
	 * Para HEADER el nombre del header.
	 *
	 * @return dias para la purga definitiva
	 */
	public String getClientCertHeaderName() {
		return propiedades.getProperty("clientCert.header.headerName");
	}

	/**
	 * Para HEADER si se valida IP del Apache.
	 *
	 * @return dias para la purga definitiva
	 */
	public String getClientCertHeaderIpFrom() {
		return propiedades.getProperty("clientCert.header.ipFrom");
	}

	/**
	 * Crea plugin.
	 *
	 * @param pluginPrefix
	 *                         Prefijo plugin
	 * @return plugin
	 */
	public IPlugin createPlugin(final String pluginPrefix) {

		IPlugin plg = null;

		try {

			final String prefijoGlobal = "es.caib.loginib";
			final String prefijoPropiedades = propiedades.getProperty(pluginPrefix + ".prefijoPropiedades");
			final String prefijoPropiedad = pluginPrefix + ".propiedad.";

			// Recuperamos propiedades plugin
			final Properties propsPlugin = new Properties();
			for (final String key : propiedades.stringPropertyNames()) {
				if (key.startsWith(prefijoPropiedad)) {
					final String propName = key.substring(prefijoPropiedad.length());
					final String propValue = propiedades.getProperty(key);
					propsPlugin.put(prefijoGlobal + "." + prefijoPropiedades + "." + propName, propValue);
				}
			}

			// Instanciamos plugin
			final String classnamePlugin = propiedades.getProperty(pluginPrefix + ".className");
			plg = (IPlugin) PluginsManager.instancePluginByClassName(classnamePlugin, prefijoGlobal + ".", propsPlugin);
			if (plg == null) {
				throw new PluginErrorException("No se ha podido instanciar plugin " + pluginPrefix + " con classname "
						+ classnamePlugin + ", PluginManager devuelve nulo.");
			}

			return plg;

		} catch (final Exception e) {
			throw new PluginErrorException("Error al instanciar plugin " + pluginPrefix, e);
		}

	}

}
