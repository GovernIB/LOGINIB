package es.caib.loginib.core.api;

import es.caib.loginib.core.api.model.login.DatosUsuario;
import es.caib.loginib.core.api.model.login.PeticionClave;
import es.caib.loginib.core.api.model.login.PeticionClaveLogout;
import es.caib.loginib.core.api.model.login.RespuestaClaveLogout;
import es.caib.loginib.core.api.model.login.TicketClave;

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
   * @param urlCallback
   *          Url callback
   * @param idioma
   *          idioma
   * @param idps
   *          Idps
   * @param qaa
   *          Qaa
   *  @param forceAuth
   *          forceAuth
   * @return Id sesion
   */
  String crearSesionClave(final String urlCallback, final String idioma, final String idps,
      Integer qaa, boolean forceAuth);

  
  /**
   * Crea logout para clave.
   * @param pUrlCallback url callback
   * @param idioma idioma
   * @return id sesion
   */
  String crearLogoutSesionClave(final String pUrlCallback, final String idioma);
  
  /**
   * Genera petición de autenticación para Clave.
   * 
   * @param idSesion
   *          id sesion
   * @return Petición Clave
   */
  PeticionClave generarPeticionClave(final String idSesion);

  /**
   * Procesa peticion clave, extrae los datos de autenticacion, los almacena en
   * bbdd y devuelve ticket de autenticacion.
   * 
   * @param samlResponseB64
   *          respuesta clave
   * @param idSesion
   *          idSesion
   * @return ticket de acceso
   */
  TicketClave procesarRespuestaClave(final String idSesion, final String samlResponseB64);

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
   *          Ticket
   * @return Datos usuario
   */
  DatosUsuario obtenerDatosUsuarioAplicacionExterna(String ticket);

  /**
   * Realiza purga de las claves.
   */
  void purgar();

  /**
   * Obtener url inicio sesion clave para aplicacion externas.
   * 
   * @param idSesion
   *          Id sesion
   * @return url
   */
  String obtenerUrlInicioExterna(String idSesion);
  
  /**
   * Obtener url logout sesion clave para aplicacion externas.
   * 
   * @param idSesion
   *          Id sesion
   * @return url
   */
  String obtenerUrlLogoutExterna(final String pIdSesion);

  /**
   * Simula respuesta Clave.
   * 
   * @param pIdSesion
   *          id sesion
   * @param pIdp
   *          idp
   * @param pNif
   *          nif
   * @param pNombre
   *          nombre
   * @param pApellidos
   *          apellidos
   * @return Ticket retorno
   */
  TicketClave simularRespuestaClave(String pIdSesion, String pIdp, String pNif, String pNombre,
      String pApellidos, final String pApellido1, final String pApellido2);

  /**
   * Generar peticion logout.
   * 
   * @param idSesion id sesion
   *
   * @return Datos para redirigira a Clave para hacer logout
   */
  PeticionClaveLogout generarPeticionLogout(String idSesion) ;
  
  /**
   * Respuesta peticion logout.
   * 
   * @param pIdSesion Id Sesion
   * @param pSamlResponseB64 Saml response
   * @return Redireccion a aplicacion origen 
   */
  RespuestaClaveLogout procesarRespuestaLogout(final String pIdSesion, final String pSamlResponseB64);

}
