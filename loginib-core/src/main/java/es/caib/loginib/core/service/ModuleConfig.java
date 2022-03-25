package es.caib.loginib.core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.springframework.stereotype.Component;

import es.caib.loginib.core.api.exception.ConfiguracionException;
import es.caib.loginib.core.api.exception.PluginErrorException;
import es.caib.loginib.core.api.model.login.types.TypeClientCert;
import es.caib.loginib.core.api.model.login.types.TypeHash;
import es.caib.loginib.core.service.model.ConfiguracionAuditoria;
import es.caib.loginib.core.service.model.ConfiguracionKeycloak;
import es.caib.loginib.core.service.model.ConfiguracionProcesos;

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
			propiedades.load(new InputStreamReader(fis, Charset.forName("UTF-8")));
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

	public boolean isOmitirDesglose() {
		String omitir =propiedades.getProperty("desglose.omitirDesglose");
		boolean retorno;
		if (omitir == null || omitir.isEmpty()) {
			retorno = false;
		} else {
			retorno = Boolean.parseBoolean(omitir);
		}
		return retorno;
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
	 * Método de acceso a configuracion procesos.
	 *
	 * @return configuracion procesos
	 */
	public ConfiguracionProcesos getConfiguracionProcesos() {
		final ConfiguracionProcesos conf = new ConfiguracionProcesos();
		conf.setTimeoutSesionAutenticacion(
				Integer.parseInt(propiedades.getProperty("procesos.timeoutSesionAutenticacion")));
		conf.setTimeoutTicketAutenticacion(
				Integer.parseInt(propiedades.getProperty("procesos.timeoutTicketAutenticacion")));
		conf.setTimeoutPurgaAuditadasFinalizadas(
				Integer.parseInt(propiedades.getProperty("procesos.timeoutPurga.auditadasFinalizadas")));
		conf.setTimeoutPurgaGeneral(Integer.parseInt(propiedades.getProperty("procesos.timeoutPurga.general")));
		return conf;
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
	 * Método usado para ClientCert.
	 *
	 * @return método
	 */
	public TypeClientCert getClientCertMetodo() {
		return TypeClientCert.fromString(propiedades.getProperty("clientCert.metodo"));
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

	/* ********************************/
	/* PROPIEDADES DE PERSONALIZACIÓN */
	/* ********************************/

	/**
	 * Retorna la propiedad por entidad e idioma correspondiente
	 *
	 * <p>
	 * Valores possibles para propiedad
	 * <ul>
	 * <li>title = titulo del navegador</li>
	 * <li>titulo = titulo en el html</li>
	 * <li>logo.alt = descripción del logotipo (etiqueta alt)</li>
	 * </ul>
	 * </p>
	 *
	 * @param entidad
	 *                      entidad
	 * @param propiedad
	 *                      propiedad
	 * @param idioma
	 *                      idioma
	 * @return
	 */
	public String getPropiedadPersonalizacion(final String entidad, final String propiedad, final String idioma) {
		String res = "";
		final String idiomaDefecto = propiedades.getProperty("personalizacion." + entidad + ".idioma.defecto");
		res = propiedades.getProperty("personalizacion." + entidad + "." + propiedad + "." + idioma);
		if (StringUtils.isEmpty(res)) {
			res = propiedades.getProperty("personalizacion." + entidad + "." + propiedad + "." + idiomaDefecto);
		}
		return res;
	}

	/**
	 * Retorna la propiedad por entidad correspondiente
	 *
	 * @param entidad
	 *                      entidad
	 * @param propiedad
	 *                      propiedad
	 * @return
	 */
	public String getPropiedadPersonalizacion(final String entidad, final String propiedad) {
		return propiedades.getProperty("personalizacion." + entidad + "." + propiedad);
	}

	/**
	 * Indica si el acceso a ClientCert se realiza mediante otra opción de
	 * autenticación (option) o enlace aparte (link)
	 *
	 * @return tipodevisualizacion option|link
	 */
	public String getClientCertVisualizacion() {
		return propiedades.getProperty("clientCert.visualizacion");
	}

	/**
	 * Indica, en el caso de que solo exista acceso anónimo, iniciar automáticamente
	 * sin mostrar el login (true/false)
	 *
	 * @return boolean
	 */
	public boolean isAnonimoInicioAuto() {
		return "true".equals(propiedades.getProperty("anonimo.inicioAuto"));
	}

	/**
	 * Devuelve milisegundos desfase retraso.
	 *
	 * @return milisegundos desfase retraso.
	 */
	public int getClaveResponseValidationNotBefore() {
		int res = 0;
		if (StringUtils.isNotBlank(propiedades.getProperty("clave.responseValidation.notBefore"))) {
			res = Integer.parseInt(propiedades.getProperty("clave.responseValidation.notBefore"));
		}
		return res;
	}

	/**
	 * Devuelve milisegundos desfase adelanto.
	 *
	 * @return milisegundos desfase adelanto.
	 */
	public int getClaveResponseValidationNotOnOrAfter() {
		int res = 0;
		if (StringUtils.isNotBlank(propiedades.getProperty("clave.responseValidation.notOnOrAfter"))) {
			res = Integer.parseInt(propiedades.getProperty("clave.responseValidation.notOnOrAfter"));
		}
		return res;
	}

	/**
	 * Indica si esta deshabilitado el acceso por usuario/password.
	 *
	 * @return boolean
	 */
	public boolean isAccesoUsuarioPasswordDeshabilitado() {
		return "true".equals(propiedades.getProperty("usuariopassword.deshabilitado"));
	}

	/**
	 * Obtiene configuracion keycloak.
	 *
	 * @return configuracion keycloak
	 */
	public ConfiguracionKeycloak getConfiguracionKeycloak() {
		final ConfiguracionKeycloak conf = new ConfiguracionKeycloak();
		conf.setConfiguracion(propiedades.getProperty("usuariopassword.keycloak.configuration"));
		conf.setAtributoNif(propiedades.getProperty("usuariopassword.keycloak.attribute.nif"));
		conf.setAtributoApellido1(propiedades.getProperty("usuariopassword.keycloak.attribute.apellido1"));
		conf.setAtributoApellido2(propiedades.getProperty("usuariopassword.keycloak.attribute.apellido2"));
		return conf;
	}

	/**
	 * Obtiene configuracion Auditoría.
	 *
	 * @return lista headers
	 */
	public ConfiguracionAuditoria getConfiguracionAuditoria() {

		List<String> ipHeaders = new ArrayList<String>();
		final String headers = propiedades.getProperty("auditoria.ip.headers");
		if (StringUtils.isNotBlank(headers)) {
			final String[] headersArray = headers.split(";");
			ipHeaders = Arrays.asList(headersArray);
		}

		final ConfiguracionAuditoria conf = new ConfiguracionAuditoria();
		conf.setIpAuditar("true".equals(propiedades.getProperty("auditoria.ip.auditar")));
		conf.setIpMostrar("true".equals(propiedades.getProperty("auditoria.ip.mostrar")));
		conf.setAlgoritmoHash(TypeHash.fromString(propiedades.getProperty("auditoria.hash.algoritmo")));
		conf.setIpHeaders(ipHeaders);
		conf.setXmlMostrar("true".equals(propiedades.getProperty("auditoria.xml.mostrar")));
		return conf;
	}

	/**
	 * Obtiene propiedades que empiezan con un prefijo.
	 *
	 * @param prefix
	 *                   Prefijo
	 * @return propiedades
	 */
	public Map<String, String> getPropiedadesByPrefix(final String prefix) {
		final Map<String, String> res = new HashMap<>();
		for (final Object key : propiedades.keySet()) {
			final String keyStr = key.toString();
			if (keyStr.startsWith(prefix)) {
				res.put(keyStr, propiedades.getProperty(keyStr));
			}
		}
		return res;
	}

}
