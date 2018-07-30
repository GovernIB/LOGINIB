package es.caib.loginib.core.service.repository.dao;

import es.caib.loginib.core.api.model.login.DatosLogoutSesion;
import es.caib.loginib.core.api.model.login.DatosSesion;
import es.caib.loginib.core.api.model.login.DatosUsuario;
import es.caib.loginib.core.api.model.login.TicketClave;

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
	 * @return identificador sesion
	 */
	String crearSesion(String urlCallback, String idioma, final String idps, final Integer qaa,
			final boolean forceAuth);

	/**
	 * Obtener datos sesion.
	 *
	 * @param idSesion
	 *            Id sesion
	 * @return Datos sesion
	 */
	DatosSesion obtenerDatosSesion(String idSesion);

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
	void establecerSamlIdPeticion(String idSesion, String samlId);

	/**
	 * Establecer SamlId Logout Peticion.
	 *
	 * @param idSesion
	 *            Id sesion
	 * @param samlId
	 *            Saml Id
	 */
	void establecerSamlIdLogoutPeticion(String idSesion, String samlId);

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
	 * @parama apellido2 Apellido 2
	 * @return Ticket
	 */
	TicketClave generateTicket(String idSesion, String idp, String nif, String nombre, String apellidos,
			String apellido1, String apellido2);

	/**
	 * Obtiene ticket aplicacion externa y lo borra.
	 *
	 * @param ticket
	 *            Ticket
	 * @return DatosUsuario
	 */
	DatosUsuario consumirTicketSesionExterna(final String ticket);

	/**
	 * Realiza purga tickets aplicaciones externas.
	 *
	 * @param timeoutSesion
	 *            timeout sesion
	 * @param timeoutTicket
	 *            timeout ticket
	 */
	void purgaTicketSesionExterna(long timeoutSesion, long timeoutTicket);

	/**
	 * Iniciar logout sesion.
	 *
	 * @param urlCallback
	 *            url Callback
	 * @param idioma
	 *            idioma
	 * @return identificador sesion
	 */
	String crearLogutSesion(String pUrlCallback, String idioma);

	/**
	 * Realiza purga logouts aplicaciones externas.
	 *
	 * @param timeoutSesion
	 *            timeout sesion
	 * @param timeoutTicket
	 *            timeout ticket
	 */
	void purgaTicketLogoutSesionExterna(Long timeoutSesionExterna, Long timeoutTicketExterna);

}
