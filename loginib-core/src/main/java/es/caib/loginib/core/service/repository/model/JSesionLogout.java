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
 * Ticket logout sesion Clave para aplicacion externa.
 *
 * @author indra
 *
 */
@Entity
@Table(name = "LIB_LOGOUT")
public final class JSesionLogout {

    /** Id secuencial. */
    @Id
    @Column(name = "LGO_CODIGO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIB_LGO_SEQ")
    @SequenceGenerator(name = "LIB_LGO_SEQ", allocationSize = 1, sequenceName = "LIB_LGO_SEQ")
    private Long id;

    /** Entidad. */
    @Column(name = "LGO_ENTIDA")
    private String entidad;

    /** Fecha inicio sesion. **/
    @Column(name = "LGO_FCSES")
    private Date fechaInicioSesion;

    /** Fecha ticket. **/
    @Column(name = "LGO_FCALTA")
    private Date fechaTicket;

    /** Idioma. */
    @Column(name = "LGO_IDIOMA")
    private String idioma;

    /** Url callback. */
    @Column(name = "LGO_URLCBK")
    private String urlCallback;

    /** Saml Id Peticion. */
    @Column(name = "LGO_SAMLID")
    private String samlIdPeticion;

    /** Sesion. */
    @Column(name = "LGO_SESION")
    private String sesion;

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
     * Gets the samlIdPeticion.
     *
     * @return the sesion
     */
    public String getSamlIdPeticion() {
        return samlIdPeticion;
    }

    /**
     * Sets the saml id peticion.
     *
     * @param samlIdPeticion
     *            the saml id peticion
     */
    public void setSamlIdPeticion(final String samlIdPeticion) {
        this.samlIdPeticion = samlIdPeticion;
    }

    /**
     * @return the fechaTicket
     */
    public Date getFechaTicket() {
        return fechaTicket;
    }

    /**
     * @param fechaTicket
     *            the fechaTicket to set
     */
    public void setFechaTicket(final Date fechaTicket) {
        this.fechaTicket = fechaTicket;
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
    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

}
