package es.caib.loginib.core.api.service;

import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import es.caib.loginib.core.api.model.login.DatosSesion;
import es.caib.loginib.core.api.model.login.DatosUsuario;
import es.caib.loginib.core.api.model.login.PeticionClave;
import es.caib.loginib.core.api.model.login.PeticionClaveLogout;
import es.caib.loginib.core.api.model.login.RespuestaClaveLogout;
import es.caib.loginib.core.api.model.login.TicketClave;
import es.caib.loginib.core.api.model.login.types.TypeClientCert;
import es.caib.loginib.core.api.model.login.types.TypeIdp;

/**
 * Acceso a funcionalidades Clave.
 *
 * @author Indra
 *
 */
public interface ClaveService {

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
	 * @param forceAuth
	 *                             forceAuth
	 * @param aplicacion
	 *                             Aplicacion
	 * @return Id sesion
	 */
	String iniciarLoginClave(final String entidad, final String urlCallback, final String urlCallbackError,
			final String idioma, final List<TypeIdp> idps, int qaa, boolean forceAuth, final String aplicacion);

	/**
	 * Crea logout para clave.
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
	String iniciarLogoutClave(final String entidad, final String pUrlCallback, final String idioma,
			final String aplicacion);

	/**
	 * Obtener datos sesión login.
	 *
	 * @param idSesion
	 *                     id sesion
	 */
	public DatosSesion obtenerDatosSesionLogin(String idSesion);

	/**
	 * Genera petición de autenticación para Clave.
	 *
	 * @param idSesion
	 *                     id sesion
	 * @return Petición Clave
	 */
	PeticionClave generarPeticionLoginClave(final String idSesion);

	/**
	 * Genera ticket para acceso anonimo.
	 *
	 * @param pIdSesion
	 *                      Id sesion
	 * @return ticket
	 */
	TicketClave loginAnonimo(final String pIdSesion);

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
	 * @return ticket de acceso
	 */
	TicketClave procesarRespuestaLoginClave(final String idSesion, final String samlResponseB64, String relayState);

	/**
	 * Indica si el acceso a clave esta deshabilitado.
	 *
	 * @return true si esta deshabilitado
	 */
	boolean isAccesoClaveDeshabilitado();

	/**
	 * Indica si el acceso a clave esta simulado.
	 *
	 * @return true si esta simulado
	 */
	boolean isAccesoClaveSimulado();

	/**
	 * Obtiene datos usuario para aplicacion externa.
	 *
	 * @param ticket
	 *                   Ticket
	 * @return Datos usuario
	 */
	DatosUsuario obtenerDatosAutenticacion(String ticket);

	/**
	 * Realiza purga de las claves.
	 */
	void purgar();

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
	 *                       id sesion
	 * @param pIdp
	 *                       idp
	 * @param pNif
	 *                       nif
	 * @param pNombre
	 *                       nombre
	 * @param pApellidos
	 *                       apellidos
	 * @return Ticket retorno
	 */
	TicketClave simularRespuestaClave(String pIdSesion, TypeIdp pIdp, String pNif, String pNombre, String pApellidos,
			final String pApellido1, final String pApellido2);

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
	 *                        idSesion
	 * @param certificate
	 *                        certificate
	 *
	 * @return ticket de acceso
	 */
	TicketClave loginClientCert(String idSesion, X509Certificate certificate);

	/**
	 * Recupera certificado de headers.
	 *
	 * @param headers
	 *                    headers
	 * @param ipFrom
	 *                    ip from
	 * @return certificado
	 */
	X509Certificate recuperarCertificadoHeader(Map<String, String> headers, String ipFrom);

	/**
	 * Devuelve metodo para client cert.
	 *
	 * @return metodo
	 */
	TypeClientCert getClientCertMetodo();

	/**
	 * Indica si ClientCert está deshabilitado.
	 * 
	 * @return si ClientCert está deshabilitado
	 */
	boolean isAccesoClientCertDeshabilitado();

}
