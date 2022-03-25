package es.caib.loginib.core.api.model.login;

import java.util.Date;

public class SesionLogin {
	private Long id;

	/** Entidad. **/
	private String entidad;

	/** Fecha inicio sesion. **/
	private Date fechaInicioSesion;

	/** Idioma. */
	private String idioma;

	/** IDPs (separados por ;). */
	private String idps;

	/** Url callback. */
	private String urlCallback;

	/** Url callback. */
	private String urlCallbackError;

	/** Ticket sesion. **/
	private String ticket;

	/** Fecha ticket. **/
	private Date fechaTicket;

	/** Nivel autenticacion (C/U). **/
	private String idp;

	/** Nif usuario autenticado. */
	private String nif;

	/** Nombre usuario autenticado. */
	private String nombre;

	/** Apellidos. */
	private String apellidos;

	/** Apellido 1. */
	private String apellido1;

	/** Apellido 2. */
	private String apellido2;

	/** Nif representante. */
	private String representanteNif;

	/** Nombre representante. */
	private String representanteNombre;

	/** Apellidos. */
	private String representanteApellidos;

	/** Apellido 1 representante. */
	private String representanteApellido1;

	/** Apellido 2 representante. */
	private String representanteApellido2;

	/** Sesion. */
	private String sesion;

	/** QAA (solicitud). */
	private Integer qaa;

	/** QAA (con el que se autentica, devuelto en respuesta). */
	private Integer qaaAutenticacion;

	/** Iniciar Cl@ve Automatico. */
	private boolean inicioClaveAutomatico;

	/** Forzar autenticacion. */
	private boolean forceAuthentication;

	/** Saml Id Peticion. */
	private String samlIdPeticion;

	/** Identidicador aplicación. */
	private String aplicacion;

	/** Identidicador aplicación. */
	private boolean checkPurga;

	/** Si audita autenticación. */
	private boolean auditar;

	/** Evidencias (JSON). */
	private String evidenciasJson;

	/** Hash evidencias. */
	private String evidenciasHash;

	/** Algoritmo hash evidencias. */
	private String evidenciasAlgoritmoHash;

	/** Params. */
	private String params;

	/** Tipo Test **/
	private boolean tipoTest;

	/** Saml B64 request **/
	private String samlRequestB64;

	/**
	 * @return the id
	 */
	public final Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the entidad
	 */
	public final String getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad the entidad to set
	 */
	public final void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the fechaInicioSesion
	 */
	public final Date getFechaInicioSesion() {
		return fechaInicioSesion;
	}

	/**
	 * @param fechaInicioSesion the fechaInicioSesion to set
	 */
	public final void setFechaInicioSesion(Date fechaInicioSesion) {
		this.fechaInicioSesion = fechaInicioSesion;
	}

	/**
	 * @return the idioma
	 */
	public final String getIdioma() {
		return idioma;
	}

	/**
	 * @param idioma the idioma to set
	 */
	public final void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	/**
	 * @return the idps
	 */
	public final String getIdps() {
		return idps;
	}

	/**
	 * @param idps the idps to set
	 */
	public final void setIdps(String idps) {
		this.idps = idps;
	}

	/**
	 * @return the urlCallback
	 */
	public final String getUrlCallback() {
		return urlCallback;
	}

	/**
	 * @param urlCallback the urlCallback to set
	 */
	public final void setUrlCallback(String urlCallback) {
		this.urlCallback = urlCallback;
	}

	/**
	 * @return the urlCallbackError
	 */
	public final String getUrlCallbackError() {
		return urlCallbackError;
	}

	/**
	 * @param urlCallbackError the urlCallbackError to set
	 */
	public final void setUrlCallbackError(String urlCallbackError) {
		this.urlCallbackError = urlCallbackError;
	}

	/**
	 * @return the ticket
	 */
	public final String getTicket() {
		return ticket;
	}

	/**
	 * @param ticket the ticket to set
	 */
	public final void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * @return the fechaTicket
	 */
	public final Date getFechaTicket() {
		return fechaTicket;
	}

	/**
	 * @param fechaTicket the fechaTicket to set
	 */
	public final void setFechaTicket(Date fechaTicket) {
		this.fechaTicket = fechaTicket;
	}

	/**
	 * @return the idp
	 */
	public final String getIdp() {
		return idp;
	}

	/**
	 * @param idp the idp to set
	 */
	public final void setIdp(String idp) {
		this.idp = idp;
	}

	/**
	 * @return the nif
	 */
	public final String getNif() {
		return nif;
	}

	/**
	 * @param nif the nif to set
	 */
	public final void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 * @return the nombre
	 */
	public final String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellidos
	 */
	public final String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos the apellidos to set
	 */
	public final void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return the apellido1
	 */
	public final String getApellido1() {
		return apellido1;
	}

	/**
	 * @param apellido1 the apellido1 to set
	 */
	public final void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	/**
	 * @return the apellido2
	 */
	public final String getApellido2() {
		return apellido2;
	}

	/**
	 * @param apellido2 the apellido2 to set
	 */
	public final void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	/**
	 * @return the representanteNif
	 */
	public final String getRepresentanteNif() {
		return representanteNif;
	}

	/**
	 * @param representanteNif the representanteNif to set
	 */
	public final void setRepresentanteNif(String representanteNif) {
		this.representanteNif = representanteNif;
	}

	/**
	 * @return the representanteNombre
	 */
	public final String getRepresentanteNombre() {
		return representanteNombre;
	}

	/**
	 * @param representanteNombre the representanteNombre to set
	 */
	public final void setRepresentanteNombre(String representanteNombre) {
		this.representanteNombre = representanteNombre;
	}

	/**
	 * @return the representanteApellidos
	 */
	public final String getRepresentanteApellidos() {
		return representanteApellidos;
	}

	/**
	 * @param representanteApellidos the representanteApellidos to set
	 */
	public final void setRepresentanteApellidos(String representanteApellidos) {
		this.representanteApellidos = representanteApellidos;
	}

	/**
	 * @return the representanteApellido1
	 */
	public final String getRepresentanteApellido1() {
		return representanteApellido1;
	}

	/**
	 * @param representanteApellido1 the representanteApellido1 to set
	 */
	public final void setRepresentanteApellido1(String representanteApellido1) {
		this.representanteApellido1 = representanteApellido1;
	}

	/**
	 * @return the representanteApellido2
	 */
	public final String getRepresentanteApellido2() {
		return representanteApellido2;
	}

	/**
	 * @param representanteApellido2 the representanteApellido2 to set
	 */
	public final void setRepresentanteApellido2(String representanteApellido2) {
		this.representanteApellido2 = representanteApellido2;
	}

	/**
	 * @return the sesion
	 */
	public final String getSesion() {
		return sesion;
	}

	/**
	 * @param sesion the sesion to set
	 */
	public final void setSesion(String sesion) {
		this.sesion = sesion;
	}

	/**
	 * @return the qaa
	 */
	public final Integer getQaa() {
		return qaa;
	}

	/**
	 * @param qaa the qaa to set
	 */
	public final void setQaa(Integer qaa) {
		this.qaa = qaa;
	}

	/**
	 * @return the qaaAutenticacion
	 */
	public final Integer getQaaAutenticacion() {
		return qaaAutenticacion;
	}

	/**
	 * @param qaaAutenticacion the qaaAutenticacion to set
	 */
	public final void setQaaAutenticacion(Integer qaaAutenticacion) {
		this.qaaAutenticacion = qaaAutenticacion;
	}

	/**
	 * @return the inicioClaveAutomatico
	 */
	public final boolean isInicioClaveAutomatico() {
		return inicioClaveAutomatico;
	}

	/**
	 * @param inicioClaveAutomatico the inicioClaveAutomatico to set
	 */
	public final void setInicioClaveAutomatico(boolean inicioClaveAutomatico) {
		this.inicioClaveAutomatico = inicioClaveAutomatico;
	}

	/**
	 * @return the forceAuthentication
	 */
	public final boolean isForceAuthentication() {
		return forceAuthentication;
	}

	/**
	 * @param forceAuthentication the forceAuthentication to set
	 */
	public final void setForceAuthentication(boolean forceAuthentication) {
		this.forceAuthentication = forceAuthentication;
	}

	/**
	 * @return the samlIdPeticion
	 */
	public final String getSamlIdPeticion() {
		return samlIdPeticion;
	}

	/**
	 * @param samlIdPeticion the samlIdPeticion to set
	 */
	public final void setSamlIdPeticion(String samlIdPeticion) {
		this.samlIdPeticion = samlIdPeticion;
	}

	/**
	 * @return the aplicacion
	 */
	public final String getAplicacion() {
		return aplicacion;
	}

	/**
	 * @param aplicacion the aplicacion to set
	 */
	public final void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	/**
	 * @return the checkPurga
	 */
	public final boolean isCheckPurga() {
		return checkPurga;
	}

	/**
	 * @param checkPurga the checkPurga to set
	 */
	public final void setCheckPurga(boolean checkPurga) {
		this.checkPurga = checkPurga;
	}

	/**
	 * @return the auditar
	 */
	public final boolean isAuditar() {
		return auditar;
	}

	/**
	 * @param auditar the auditar to set
	 */
	public final void setAuditar(boolean auditar) {
		this.auditar = auditar;
	}

	/**
	 * @return the evidenciasJson
	 */
	public final String getEvidenciasJson() {
		return evidenciasJson;
	}

	/**
	 * @param evidenciasJson the evidenciasJson to set
	 */
	public final void setEvidenciasJson(String evidenciasJson) {
		this.evidenciasJson = evidenciasJson;
	}

	/**
	 * @return the evidenciasHash
	 */
	public final String getEvidenciasHash() {
		return evidenciasHash;
	}

	/**
	 * @param evidenciasHash the evidenciasHash to set
	 */
	public final void setEvidenciasHash(String evidenciasHash) {
		this.evidenciasHash = evidenciasHash;
	}

	/**
	 * @return the evidenciasAlgoritmoHash
	 */
	public final String getEvidenciasAlgoritmoHash() {
		return evidenciasAlgoritmoHash;
	}

	/**
	 * @param evidenciasAlgoritmoHash the evidenciasAlgoritmoHash to set
	 */
	public final void setEvidenciasAlgoritmoHash(String evidenciasAlgoritmoHash) {
		this.evidenciasAlgoritmoHash = evidenciasAlgoritmoHash;
	}

	/**
	 * @return the params
	 */
	public final String getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public final void setParams(String params) {
		this.params = params;
	}

	/**
	 * @return the tipoTest
	 */
	public boolean isTipoTest() {
		return tipoTest;
	}

	/**
	 * @param tipoTest the tipoTest to set
	 */
	public void setTipoTest(boolean tipoTest) {
		this.tipoTest = tipoTest;
	}

	/**
	 * @return the samlRequestB64
	 */
	public String getSamlRequestB64() {
		return samlRequestB64;
	}

	/**
	 * @param samlRequestB64 the samlRequestB64 to set
	 */
	public void setSamlRequestB64(String samlRequestB64) {
		this.samlRequestB64 = samlRequestB64;
	}

}
