package es.caib.loginib.core.service.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Ticket sesion Clave para aplicacion externa.
 *
 * @author indra
 *
 */
@Entity
@Table(name = "LIB_LOGIN")
public final class JSesionLogin {

	/** Id secuencial. */
	@Id
	@Column(name = "LGI_CODIGO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIB_LGI_SEQ")
	@SequenceGenerator(name = "LIB_LGI_SEQ", allocationSize = 1, sequenceName = "LIB_LGI_SEQ")
	private Long id;

	/** Entidad. **/
	@Column(name = "LGI_ENTIDA")
	private String entidad;

	/** Fecha inicio sesion. **/
	@Column(name = "LGI_FCSES")
	private Date fechaInicioSesion;

	/** Idioma. */
	@Column(name = "LGI_IDIOMA")
	private String idioma;

	/** IDPs (separados por ;). */
	@Column(name = "LGI_IDPS")
	private String idps;

	/** Url callback. */
	@Column(name = "LGI_URLCBK")
	private String urlCallback;

	/** Ticket sesion. **/
	@Column(name = "LGI_TICKET")
	private String ticket;

	/** Fecha ticket. **/
	@Column(name = "LGI_FCALTA")
	private Date fechaTicket;

	/** Nivel autenticacion (C/U). **/
	@Column(name = "LGI_NIVAUT")
	private String idp;

	/** Nif usuario autenticado. */
	@Column(name = "LGI_NIF")
	private String nif;

	/** Nombre usuario autenticado. */
	@Column(name = "LGI_NOM")
	private String nombre;

	/** Apellidos. */
	@Column(name = "LGI_APE")
	private String apellidos;

	/** Apellido 1. */
	@Column(name = "LGI_APE1")
	private String apellido1;

	/** Apellido 2. */
	@Column(name = "LGI_APE2")
	private String apellido2;

	/** Nif representante. */
	@Column(name = "LGI_NIFRPT")
	private String representanteNif;

	/** Nombre representante. */
	@Column(name = "LGI_NOMRPT")
	private String representanteNombre;

	/** Apellidos. */
	@Column(name = "LGI_APERPT")
	private String representanteApellidos;

	/** Apellido 1 representante. */
	@Column(name = "LGI_AP1RPT")
	private String representanteApellido1;

	/** Apellido 2 representante. */
	@Column(name = "LGI_AP2RPT")
	private String representanteApellido2;

	/** Sesion. */
	@Column(name = "LGI_SESION")
	private String sesion;

	/** QAA. */
	@Column(name = "LGI_QAA")
	private Integer qaa;

	/** Forzar autenticacion. */
	@Column(name = "LGI_FCAUTH")
	private boolean forceAuthentication;

	/** Saml Id Peticion. */
	@Column(name = "LGI_SAMLID")
	private String samlIdPeticion;

	/** Identidicador aplicación. */
	@Column(name = "LGI_APLICA")
	private String aplicacion;

	/**
	 * Gets the ticket.
	 *
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * Sets the ticket.
	 *
	 * @param pTicket
	 *            the ticket to set
	 */
	public void setTicket(final String pTicket) {
		ticket = pTicket;
	}

	/**
	 * Gets the fecha ticket.
	 *
	 * @return the fecha
	 */
	public Date getFechaTicket() {
		return fechaTicket;
	}

	/**
	 * Sets the fecha ticket.
	 *
	 * @param pFecha
	 *            the fecha to set
	 */
	public void setFechaTicket(final Date pFecha) {
		fechaTicket = pFecha;
	}

	/**
	 * Gets the nivel autenticacion.
	 *
	 * @return the nivelAutenticacion
	 */
	public String getIdp() {
		return idp;
	}

	/**
	 * Sets the nivel autenticacion.
	 *
	 * @param pNivelAutenticacion
	 *            the nivelAutenticacion to set
	 */
	public void setIdp(final String pNivelAutenticacion) {
		idp = pNivelAutenticacion;
	}

	/**
	 * Gets the nif.
	 *
	 * @return the nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Sets the nif.
	 *
	 * @param pNif
	 *            the nif to set
	 */
	public void setNif(final String pNif) {
		nif = pNif;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param pId
	 *            the id to set
	 */
	public void setId(final Long pId) {
		id = pId;
	}

	/**
	 * Gets the fecha inicio sesion.
	 *
	 * @return the fechaInicioSesion
	 */
	public Date getFechaInicioSesion() {
		return fechaInicioSesion;
	}

	/**
	 * Sets the fecha inicio sesion.
	 *
	 * @param pFechaInicioSesion
	 *            the fechaInicioSesion to set
	 */
	public void setFechaInicioSesion(final Date pFechaInicioSesion) {
		fechaInicioSesion = pFechaInicioSesion;
	}

	/**
	 * Gets the url callback.
	 *
	 * @return the urlCallback
	 */
	public String getUrlCallback() {
		return urlCallback;
	}

	/**
	 * Sets the url callback.
	 *
	 * @param pUrlCallback
	 *            the urlCallback to set
	 */
	public void setUrlCallback(final String pUrlCallback) {
		urlCallback = pUrlCallback;
	}

	/**
	 * Gets the idioma.
	 *
	 * @return the idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Sets the idioma.
	 *
	 * @param pIdioma
	 *            the idioma to set
	 */
	public void setIdioma(final String pIdioma) {
		idioma = pIdioma;
	}

	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param pNombre
	 *            the new nombre
	 */
	public void setNombre(final String pNombre) {
		nombre = pNombre;
	}

	/**
	 * Gets the apellidos.
	 *
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Sets the apellidos.
	 *
	 * @param pApellidos
	 *            the new apellidos
	 */
	public void setApellidos(final String pApellidos) {
		apellidos = pApellidos;
	}

	/**
	 * Gets the idps.
	 *
	 * @return the idps
	 */
	public String getIdps() {
		return idps;
	}

	/**
	 * Sets the idps.
	 *
	 * @param pIdps
	 *            the new idps
	 */
	public void setIdps(final String pIdps) {
		idps = pIdps;
	}

	/**
	 * Método de acceso a apellido1.
	 *
	 * @return apellido1
	 */
	public String getApellido1() {
		return apellido1;
	}

	/**
	 * Método para establecer apellido1.
	 *
	 * @param pApellido1
	 *            apellido1 a establecer
	 */
	public void setApellido1(final String pApellido1) {
		apellido1 = pApellido1;
	}

	/**
	 * Método de acceso a apellido2.
	 *
	 * @return apellido2
	 */
	public String getApellido2() {
		return apellido2;
	}

	/**
	 * Método para establecer apellido2.
	 *
	 * @param pApellido2
	 *            apellido2 a establecer
	 */
	public void setApellido2(final String pApellido2) {
		apellido2 = pApellido2;
	}

	/**
	 * Gets the sesion.
	 *
	 * @return the sesion
	 */
	public String getSesion() {
		return sesion;
	}

	/**
	 * Sets the sesion.
	 *
	 * @param sesion
	 *            the sesion to set
	 */
	public void setSesion(final String sesion) {
		this.sesion = sesion;
	}

	/**
	 * Gets the qaa.
	 *
	 * @return the qaa
	 */
	public Integer getQaa() {
		return qaa;
	}

	/**
	 * Sets the qaa.
	 *
	 * @param qaa
	 *            the qaa to set
	 */
	public void setQaa(final Integer qaa) {
		this.qaa = qaa;
	}

	/**
	 * Checks if is force authentication.
	 *
	 * @return true, if is force authentication
	 */
	public boolean isForceAuthentication() {
		return forceAuthentication;
	}

	/**
	 * Sets the force authentication.
	 *
	 * @param forceAuthentication
	 *            the new force authentication
	 */
	public void setForceAuthentication(final boolean forceAuthentication) {
		this.forceAuthentication = forceAuthentication;
	}

	public String getSamlIdPeticion() {
		return samlIdPeticion;
	}

	public void setSamlIdPeticion(final String samlIdPeticion) {
		this.samlIdPeticion = samlIdPeticion;
	}

	/**
	 * Método de acceso a entidad.
	 *
	 * @return entidad
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * Método para establecer entidad.
	 *
	 * @param entidad
	 *            entidad a establecer
	 */
	public void setEntidad(final String entidad) {
		this.entidad = entidad;
	}

	/**
	 * Método de acceso a representanteNif.
	 *
	 * @return representanteNif
	 */
	public String getRepresentanteNif() {
		return representanteNif;
	}

	/**
	 * Método para establecer representanteNif.
	 *
	 * @param representanteNif
	 *            representanteNif a establecer
	 */
	public void setRepresentanteNif(final String representanteNif) {
		this.representanteNif = representanteNif;
	}

	/**
	 * Método de acceso a representanteNombre.
	 *
	 * @return representanteNombre
	 */
	public String getRepresentanteNombre() {
		return representanteNombre;
	}

	/**
	 * Método para establecer representanteNombre.
	 *
	 * @param representanteNombre
	 *            representanteNombre a establecer
	 */
	public void setRepresentanteNombre(final String representanteNombre) {
		this.representanteNombre = representanteNombre;
	}

	/**
	 * Método de acceso a representanteApellidos.
	 *
	 * @return representanteApellidos
	 */
	public String getRepresentanteApellidos() {
		return representanteApellidos;
	}

	/**
	 * Método para establecer representanteApellidos.
	 *
	 * @param representanteApellidos
	 *            representanteApellidos a establecer
	 */
	public void setRepresentanteApellidos(final String representanteApellidos) {
		this.representanteApellidos = representanteApellidos;
	}

	/**
	 * Método de acceso a representanteApellido1.
	 *
	 * @return representanteApellido1
	 */
	public String getRepresentanteApellido1() {
		return representanteApellido1;
	}

	/**
	 * Método para establecer representanteApellido1.
	 *
	 * @param representanteApellido1
	 *            representanteApellido1 a establecer
	 */
	public void setRepresentanteApellido1(final String representanteApellido1) {
		this.representanteApellido1 = representanteApellido1;
	}

	/**
	 * Método de acceso a representanteApellido2.
	 *
	 * @return representanteApellido2
	 */
	public String getRepresentanteApellido2() {
		return representanteApellido2;
	}

	/**
	 * Método para establecer representanteApellido2.
	 *
	 * @param representanteApellido2
	 *            representanteApellido2 a establecer
	 */
	public void setRepresentanteApellido2(final String representanteApellido2) {
		this.representanteApellido2 = representanteApellido2;
	}

	/**
	 * Método para obtener el identificador aplicación.
	 *
	 * @return the aplicacion
	 */
	public String getAplicacion() {
		return aplicacion;
	}

	/**
	 * Método para obtener el identificador aplicación
	 *
	 * @param aplicacion
	 *            the aplicacion to set
	 */
	public void setAplicacion(final String aplicacion) {
		this.aplicacion = aplicacion;
	}

}
