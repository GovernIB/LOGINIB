package es.caib.loginib.core.api.model.login;

import java.util.Date;
import java.util.List;

import es.caib.loginib.core.api.model.login.types.TypeIdp;

/**
 * Datos sesion.
 *
 * @author Indra
 *
 */
public final class DatosSesion {

    /** Id sesion. */
    private String idSesion;

    /** Idps. */
    private List<TypeIdp> idps;

    /** Entidad. */
    private String entidad;

    /** Fecha inicio sesion. */
    private Date fechaInicioSesion;

    /** Fecha ticket (nulo si no se ha iniciado sesion en clave). */
    private Date fechaTicket;

    /** Idioma. */
    private String idioma;

    /** QAA. */
    private Integer qaa;

    /** Force auth. */
    private boolean forceAuth;

    /** Saml id peticion. */
    private String samlIdPeticion;

    /** Url callback. */
    private String urlCallback;

    /**
     * Gets the fecha.
     *
     * @return the fecha
     */
    public Date getFechaInicioSesion() {
        return fechaInicioSesion;
    }

    /**
     * Sets the fecha.
     *
     * @param pFecha
     *            the fecha to set
     */
    public void setFechaInicioSesion(final Date pFecha) {
        fechaInicioSesion = pFecha;
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
     * Gets the fecha ticket.
     *
     * @return the fechaTicket
     */
    public Date getFechaTicket() {
        return fechaTicket;
    }

    /**
     * Sets the fecha ticket.
     *
     * @param pFechaTicket
     *            the fechaTicket to set
     */
    public void setFechaTicket(final Date pFechaTicket) {
        fechaTicket = pFechaTicket;
    }

    /**
     * @return the qaa
     */
    public Integer getQaa() {
        return qaa;
    }

    /**
     * @param qaa
     *            the qaa to set
     */
    public void setQaa(Integer qaa) {
        this.qaa = qaa;
    }

    /**
     * Checks if is force auth.
     *
     * @return true, if is force auth
     */
    public boolean isForceAuth() {
        return forceAuth;
    }

    /**
     * Sets the force auth.
     *
     * @param forceAuth
     *            the new force auth
     */
    public void setForceAuth(boolean forceAuth) {
        this.forceAuth = forceAuth;
    }

    public String getSamlIdPeticion() {
        return samlIdPeticion;
    }

    public void setSamlIdPeticion(String samlIdPeticion) {
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
    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    /**
     * Método de acceso a idSesion.
     *
     * @return idSesion
     */
    public String getIdSesion() {
        return idSesion;
    }

    /**
     * Método para establecer idSesion.
     *
     * @param idSesion
     *            idSesion a establecer
     */
    public void setIdSesion(String idSesion) {
        this.idSesion = idSesion;
    }

    /**
     * Método de acceso a idps.
     *
     * @return idps
     */
    public List<TypeIdp> getIdps() {
        return idps;
    }

    /**
     * Método para establecer idps.
     *
     * @param idps
     *            idps a establecer
     */
    public void setIdps(List<TypeIdp> idps) {
        this.idps = idps;
    }

    /**
     * Método de acceso a urlCallback.
     * 
     * @return urlCallback
     */
    public String getUrlCallback() {
        return urlCallback;
    }

    /**
     * Método para establecer urlCallback.
     * 
     * @param urlCallback
     *            urlCallback a establecer
     */
    public void setUrlCallback(String urlCallback) {
        this.urlCallback = urlCallback;
    }

}
