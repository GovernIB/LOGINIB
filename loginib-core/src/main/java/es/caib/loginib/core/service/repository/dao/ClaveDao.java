package es.caib.loginib.core.service.repository.dao;

import java.util.List;

import es.caib.loginib.core.api.model.login.DatosLogoutSesion;
import es.caib.loginib.core.api.model.login.DatosRepresentante;
import es.caib.loginib.core.api.model.login.DatosSesion;
import es.caib.loginib.core.api.model.login.DatosUsuario;
import es.caib.loginib.core.api.model.login.TicketClave;
import es.caib.loginib.core.api.model.login.types.TypeIdp;

/**
 * Interfaz de acceso a base de datos para crear ticket acceso sesion clave.
 *
 * @author Indra
 *
 */
public interface ClaveDao {

	/**
	 * Iniciar sesion.
	 *
	 * @param entidad
	 *            entidad
	 * @param urlCallback
	 *            url Callback
	 * @param idioma
	 *            idioma
	 * @param idps
	 *            idps
	 * @param qaa
	 *            Qaa
	 * @param forceAuth
	 *            forceAuth
	 * @param aplicacion
	 *            Aplicacion
	 * @return identificador sesion
	 */
	String crearSesionLogin(final String entidad, String urlCallback, String idioma, final List<TypeIdp> idps,
			final Integer qaa, final boolean forceAuth, final String aplicacion);

	/**
	 * Obtener datos sesion.
	 *
	 * @param idSesion
	 *            Id sesion
	 * @return Datos sesion
	 */
	DatosSesion obtenerDatosSesionLogin(String idSesion);

	/**
	 * Obtener datos sesion logout.
	 *
	 * @param idSesion
	 *            Id sesion
	 * @return Datos sesion
	 */
	DatosLogoutSesion obtenerDatosSesionLogout(String idSesion);

	/**
	 * Establecer SamlId Peticion.
	 *
	 * @param idSesion
	 *            Id sesion
	 * @param samlId
	 *            Saml Id
	 */
	void establecerSamlIdSesionLogin(String idSesion, String samlId);

	/**
	 * Establecer SamlId Logout Peticion.
	 *
	 * @param idSesion
	 *            Id sesion
	 * @param samlId
	 *            Saml Id
	 */
	void establecerSamlIdSesionLogout(String idSesion, String samlId);

	/**
	 * Crea ticket.
	 *
	 * @param idSesion
	 *            id sesion
	 * @param idp
	 *            idp
	 * @param nif
	 *            Nif
	 * @param nombre
	 *            Nombre
	 * @param apellidos
	 *            Apelllidos
	 * @param apellido1
	 *            Apellido 1
	 * @param representante
	 *            representante
	 * @parama apellido2 Apellido 2
	 * @return Ticket
	 */
	TicketClave generateTicketSesionLogin(String idSesion, TypeIdp idp, String nif, String nombre, String apellidos,
			String apellido1, String apellido2, DatosRepresentante representante);

	/**
	 * Obtiene ticket aplicacion externa y lo borra.
	 *
	 * @param ticket
	 *            Ticket
	 * @return DatosUsuario
	 */
	DatosUsuario consumirTicketSesionLogin(final String ticket);

	/**
	 * Realiza purga tickets aplicaciones externas.
	 *
	 * @param timeoutSesion
	 *            timeout sesion
	 * @param timeoutTicket
	 *            timeout ticket
	 */
	void purgaTicketSesionLogin(long timeoutSesion, long timeoutTicket);

	/**
	 * Iniciar logout sesion.
	 *
	 * @param entidad
	 *            entidad
	 * @param urlCallback
	 *            url Callback
	 * @param idioma
	 *            idioma
	 * @param aplicacion
	 *            Identificador aplicacion
	 * @return identificador sesion
	 */
	String crearSesionLogut(final String entidad, String pUrlCallback, String idioma, String aplicacion);

	/**
	 * Realiza purga logouts aplicaciones externas.
	 *
	 * @param timeoutSesion
	 *            timeout sesion
	 * @param timeoutTicket
	 *            timeout ticket
	 */
	void purgaTicketSesionLogout(Long timeoutSesionExterna, Long timeoutTicketExterna);

}
