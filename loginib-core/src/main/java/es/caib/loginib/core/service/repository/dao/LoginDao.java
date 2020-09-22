package es.caib.loginib.core.service.repository.dao;

import java.util.List;

import es.caib.loginib.core.api.model.login.DatosAutenticacion;
import es.caib.loginib.core.api.model.login.DatosLogoutSesion;
import es.caib.loginib.core.api.model.login.DatosSesionData;
import es.caib.loginib.core.api.model.login.EvidenciasAutenticacion;
import es.caib.loginib.core.api.model.login.TicketClave;
import es.caib.loginib.core.api.model.login.types.TypeIdp;
import es.caib.loginib.core.service.model.ConfiguracionProcesos;

/**
 * Interfaz de acceso a base de datos para gestión login.
 *
 * @author Indra
 *
 */
public interface LoginDao {

	/**
	 * Iniciar sesion.
	 *
	 * @param entidad
	 *                             entidad
	 * @param urlCallback
	 *                             url Callback
	 * @param urlCallbackError
	 *                             url Callback error
	 * @param idioma
	 *                             idioma
	 * @param idps
	 *                             idps
	 * @param qaa
	 *                             Qaa                             
	 * @param iniClaAuto
	 *                             iniClaAuto
	 * @param forceAuth
	 *                             forceAuth
	 * @param aplicacion
	 *                             Aplicacion
	 * @param auditar
	 *                             Auditar
	 *
	 * @return identificador sesion
	 */
	String crearSesionLogin(final String entidad, String urlCallback, final String pUrlCallbackError, String idioma,
			final List<TypeIdp> idps, final Integer qaa, final boolean iniClaAuto, final boolean forceAuth, final String aplicacion,
			boolean auditar);

	/**
	 * Obtener datos sesion.
	 *
	 * @param idSesion
	 *                     Id sesion
	 * @return Datos sesion
	 */
	DatosSesionData obtenerDatosSesionLogin(String idSesion);

	/**
	 * Obtener datos sesion logout.
	 *
	 * @param idSesion
	 *                     Id sesion
	 * @return Datos sesion
	 */
	DatosLogoutSesion obtenerDatosSesionLogout(String idSesion);

	/**
	 * Establecer id externo peticion login.
	 *
	 * @param idSesion
	 *                     Id sesion
	 * @param samlId
	 *                     Saml Id
	 */
	void establecerIdPeticionLogin(String idSesion, String samlId);

	/**
	 * Establecer id externo peticion logout.
	 *
	 * @param idSesion
	 *                     Id sesion
	 * @param samlId
	 *                     Saml Id
	 */
	void establecerIdPeticionLogout(String idSesion, String samlId);

	/**
	 * Crea ticket.
	 *
	 * @param idSesion
	 *                               id sesion
	 * @param datosAutenticacion
	 *                               datos autenticacion
	 * @param evidencias
	 *                               Evidencias autenticación
	 * @return Ticket
	 */
	TicketClave generateTicketSesionLogin(String idSesion, DatosAutenticacion datosAutenticacion,
			EvidenciasAutenticacion evidencias);

	/**
	 * Consume ticket (lo marca como usado).
	 *
	 * @param ticket
	 *                          Ticket
	 * @param timeoutTicket
	 *                          Timeout ticket
	 * @return DatosUsuario
	 */
	DatosAutenticacion consumirTicketSesionLogin(final String ticket, long timeoutTicket);

	/**
	 * Iniciar logout sesion.
	 *
	 * @param entidad
	 *                        entidad
	 * @param urlCallback
	 *                        url Callback
	 * @param idioma
	 *                        idioma
	 * @param aplicacion
	 *                        Identificador aplicacion
	 * @return identificador sesion
	 */
	String crearSesionLogut(final String entidad, String pUrlCallback, String idioma, String aplicacion);

	/**
	 * Obtiene evidencias autenticación.
	 *
	 * @param idSesion
	 *                     id sesión
	 * @return evidencias
	 */
	EvidenciasAutenticacion obtenerEvidenciasSesionLogin(final String idSesion);

	/**
	 * Realiza purga tickets aplicaciones externas.
	 *
	 * @param ConfiguracionProcesos
	 *                                  conf
	 */
	void purgaTicketSesionLogin(ConfiguracionProcesos conf);

	/**
	 * Realiza purga logouts.
	 *
	 * @param ConfiguracionProcesos
	 *                                  conf
	 */
	void purgaTicketSesionLogout(ConfiguracionProcesos conf);

}
