package es.caib.loginib.core.api.service;

import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import es.caib.loginib.core.api.model.login.DatosAutenticacion;
import es.caib.loginib.core.api.model.login.DatosPersona;
import es.caib.loginib.core.api.model.login.DatosSesion;
import es.caib.loginib.core.api.model.login.EvidenciasAutenticacion;
import es.caib.loginib.core.api.model.login.PeticionClave;
import es.caib.loginib.core.api.model.login.PeticionClaveLogout;
import es.caib.loginib.core.api.model.login.RespuestaClaveLogout;
import es.caib.loginib.core.api.model.login.TicketClave;
import es.caib.loginib.core.api.model.login.ValidacionUsuarioPassword;
import es.caib.loginib.core.api.model.login.types.TypeIdp;

/**
 * Acceso a funcionalidades Clave.
 *
 * @author Indra
 *
 */
public interface LoginService {

	/**
	 * Crea sesion para clave.
	 *
	 * @param entidad
	 *                             entidad
	 * @param urlCallback
	 *                             Url callback
	 * @param urlCallbackError
	 *                             Url callback error
	 * @param idioma
	 *                             idioma
	 * @param idps
	 *                             Idps
	 * @param qaa
	 *                             Qaa
	 * @param iniClaAuto
	 *                             iniClaAuto
 *                             
	 * @param forceAuth
	 *                             forceAuth
	 * @param aplicacion
	 *                             Aplicacion
	 * @param auditar
	 *                             auditar
	 * @return Id sesion
	 */
	String iniciarSesionLogin(final String entidad, final String urlCallback, final String urlCallbackError,
			final String idioma, final List<TypeIdp> idps, Integer qaa, boolean iniClaAuto, boolean forceAuth, final String aplicacion,
			final boolean auditar);

	/**
	 * Inicia sesión logout (solo aplica de momento a Cl@ve).
	 *
	 * @param entidad
	 *                         entidad
	 * @param pUrlCallback
	 *                         url callback
	 * @param idioma
	 *                         idioma
	 * @param aplicacion
	 *                         identificador aplicacion
	 * @return id sesion
	 */
	String iniciarSesionLogout(final String entidad, final String pUrlCallback, final String idioma,
			final String aplicacion);

	/**
	 * Obtener datos sesión login.
	 *
	 * @param idSesion
	 *                     id sesion
	 */
	DatosSesion obtenerDatosSesionLogin(String idSesion);

	/**
	 * Genera petición de autenticación para Clave.
	 *
	 * @param idSesion
	 *                     id sesion
	 * @return Petición Clave
	 */
	PeticionClave generarPeticionLoginClave(final String idSesion);

	/**
	 * Procesa peticion clave, extrae los datos de autenticacion, los almacena en
	 * bbdd y devuelve ticket de autenticacion.
	 *
	 * @param samlResponseB64
	 *                            respuesta clave
	 * @param idSesion
	 *                            idSesion
	 * @param relayState
	 *                            relayState
	 * @param headersRequest
	 *                            Headers request (obtención ip)
	 * @param ipAddressFrom
	 *                            Ip desde donde se hace la petición
	 *                            (getRemoteAddress)
	 *
	 * @return ticket de acceso
	 */
	TicketClave procesarRespuestaLoginClave(final String idSesion, final String samlResponseB64, String relayState,
			final Map<String, String> headersRequest, final String ipAddressFrom);

	/**
	 * Obtiene datos usuario para aplicacion externa.
	 *
	 * @param ticket
	 *                   Ticket
	 * @return Datos usuario
	 */
	DatosAutenticacion obtenerDatosAutenticacion(String ticket);

	/**
	 * Obtener url inicio sesion clave para aplicacion externas.
	 *
	 * @param idSesion
	 *                     Id sesion
	 * @param idioma
	 *                     idioma
	 * @return url
	 */
	String obtenerUrlRedireccionLoginClave(String idSesion, String idioma);

	/**
	 * Obtener url logout sesion clave para aplicacion externas.
	 *
	 * @param idSesion
	 *                     Id sesion
	 * @return url
	 */
	String obtenerUrlRedireccionLogoutClave(final String pIdSesion);

	/**
	 * Simula respuesta Clave.
	 *
	 * @param pIdSesion
	 *                           id sesion
	 * @param pIdp
	 *                           idp
	 * @param persona
	 *                           persona
	 * @param headersRequest
	 *                           Headers request (obtención ip)
	 * @param ipAddressFrom
	 *                           Ip desde donde se hace la petición
	 *                           (getRemoteAddress)
	 * @return Ticket retorno
	 */
	TicketClave simularRespuestaClave(String pIdSesion, TypeIdp pIdp, final DatosPersona persona,
			final Map<String, String> headersRequest, final String ipAddressFrom);

	/**
	 * Generar peticion logout.
	 *
	 * @param idSesion
	 *                     id sesion
	 *
	 * @return Datos para redirigira a Clave para hacer logout
	 */
	PeticionClaveLogout generarPeticionLogoutClave(String idSesion);

	/**
	 * Respuesta peticion logout.
	 *
	 * @param pIdSesion
	 *                             Id Sesion
	 * @param pSamlResponseB64
	 *                             Saml response
	 * @param relayState
	 *                             relayState
	 * @return Redireccion a aplicacion origen
	 */
	RespuestaClaveLogout procesarRespuestaLogoutClave(final String pIdSesion, final String pSamlResponseB64);

	/**
	 * Realiza login mediante Client Cert.
	 *
	 * @param idSesion
	 *                          idSesion
	 * @param headers
	 *                          Headers request (para HEADER y obtención ip)
	 * @param certificate
	 *                          certificate request (para AJP)
	 * @param ipAddressFrom
	 *                          Ip desde donde se hace la petición
	 *
	 * @return ticket de acceso
	 */
	TicketClave loginClientCert(String idSesion, Map<String, String> headers, X509Certificate certificate,
			String ipAddressFrom);

	/**
	 * Valida usuario/password.
	 *
	 * @param idSesion
	 *                           id sesion
	 * @param usuario
	 *                           usuario
	 * @param password
	 *                           password
	 * @param headersRequest
	 *                           Headers request (obtención ip)
	 * @param ipAddressFrom
	 *                           Ip desde donde se hace la petición
	 *                           (getRemoteAddress)
	 * @return validación
	 */
	ValidacionUsuarioPassword loginUsuarioPassword(String idSesion, String usuario, String password,
			final Map<String, String> headersRequest, final String ipAddressFrom);

	/**
	 * Genera ticket para acceso anonimo.
	 *
	 * @param pIdSesion
	 *                           Id sesion
	 * @param headersRequest
	 *                           Headers request (obtención ip)
	 * @param ipAddressFrom
	 *                           Ip desde donde se hace la petición
	 *                           (getRemoteAddress)
	 * @return ticket
	 */
	TicketClave loginAnonimo(final String pIdSesion, final Map<String, String> headers, final String ipAddressFrom);

	/**
	 * Obtiene evidencias autenticación.
	 *
	 * @param idSesion
	 *                     id sesión
	 * @return evidencias
	 */
	EvidenciasAutenticacion obtenerEvidenciasSesionLogin(final String idSesion);

	/**
	 * Realiza purga sesiones.
	 */
	void purgar();

}
