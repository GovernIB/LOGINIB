package es.caib.loginib.core.service;

/**
 *
 * Acceso a propiedades aplicacion.
 *
 * @author Indra
 *
 */
public final class ModuleConfig {

    /** Qaa. */
    private String qaa;

    /** spId. */
    private String spId;

    /** providerName. */
    private String providerName;

    /** spSector. */
    private String spSector;

    /** spApplication. */

    private String spApplication;

    /** pepsUrl. */
    private String pepsUrl;

    /** pepsLogout. */
    private String pepsLogout;

    /** returnUrl para externa. */
    private String returnUrlExterna;

    /** Return url externa. */
    private String returnLogoutUrlExterna;

    /** Timeout ticket para aplicaciones externas. */
    private Long timeoutTicketExterna;

    /** Timeout sesion para aplicaciones externas. */
    private Long timeoutSesionExterna;

    /** Deshabilitado. */
    private boolean accesoClaveDeshabilitado;

    /** Inicio sesion clave para aplicaciones externas. */
    private String inicioUrlExterna;

    /** Url logout para aplicaciones externas. */
    private String inicioLogoutExterna;

    /** Si se simula proceso de login. */
    private boolean accesoClaveSimulado;

    /** Deshabilitado. */
    private boolean claveFirmaAccesoDeshabilitado;

    /** ClaveFirma App Id. */
    private String claveFirmaAppId;

    /** ClaveFirma callback. */
    private String claveFirmaCallback;

    /** ClaveFirma url inicio. */
    private String claveFirmaUrlInicio;

    /** ClaveFirma timeout ticket. */
    private Long claveFirmaTimeoutTicket;

    /** Clave firma timeout sesion. */
    private Long clavefirmaTimeoutSesion;

    /** Clave firma timeout proceso. */
    private Long claveFirmaTimeoutProceso;

    /** Deshabilitado. */
    private boolean fireAccesoDeshabilitado;

    /** fire App Id. */
    private String fireAppId;

    /** fire callback. */
    private String fireCallback;

    /** fire url inicio. */
    private String fireUrlInicio;

    /** fire timeout ticket. */
    private Long fireTimeoutTicket;

    /** Clave firma timeout sesion. */
    private Long fireTimeoutSesion;

    /** Clave firma timeout proceso. */
    private Long fireTimeoutProceso;

    /**
     * Gets the qaa.
     *
     * @return the qaa
     */
    public String getQaa() {
        return qaa;
    }

    /**
     * Sets the qaa.
     *
     * @param pQaa
     *            the qaa to set
     */
    public void setQaa(final String pQaa) {
        qaa = pQaa;
    }

    /**
     * Gets the sp id.
     *
     * @return the spId
     */
    public String getSpId() {
        return spId;
    }

    /**
     * Sets the sp id.
     *
     * @param pSpId
     *            the spId to set
     */
    public void setSpId(final String pSpId) {
        spId = pSpId;
    }

    /**
     * Gets the provider name.
     *
     * @return the providerName
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * Sets the provider name.
     *
     * @param pProviderName
     *            the providerName to set
     */
    public void setProviderName(final String pProviderName) {
        providerName = pProviderName;
    }

    /**
     * Gets the sp sector.
     *
     * @return the spSector
     */
    public String getSpSector() {
        return spSector;
    }

    /**
     * Sets the sp sector.
     *
     * @param pSpSector
     *            the spSector to set
     */
    public void setSpSector(final String pSpSector) {
        spSector = pSpSector;
    }

    /**
     * Gets the sp application.
     *
     * @return the spApplication
     */
    public String getSpApplication() {
        return spApplication;
    }

    /**
     * Sets the sp application.
     *
     * @param pSpApplication
     *            the spApplication to set
     */
    public void setSpApplication(final String pSpApplication) {
        spApplication = pSpApplication;
    }

    /**
     * Gets the peps url.
     *
     * @return the pepsUrl
     */
    public String getPepsUrl() {
        return pepsUrl;
    }

    /**
     * Sets the peps url.
     *
     * @param pPepsUrl
     *            the pepsUrl to set
     */
    public void setPepsUrl(final String pPepsUrl) {
        pepsUrl = pPepsUrl;
    }

    /**
     * Checks if is acceso clave deshabilitado.
     *
     * @return the accesoClaveDeshabilitado
     */
    public boolean isAccesoClaveDeshabilitado() {
        return accesoClaveDeshabilitado;
    }

    /**
     * Sets the acceso clave deshabilitado.
     *
     * @param pAccesoClaveDeshabilitado
     *            the accesoClaveDeshabilitado to set
     */
    public void setAccesoClaveDeshabilitado(
            final boolean pAccesoClaveDeshabilitado) {
        accesoClaveDeshabilitado = pAccesoClaveDeshabilitado;
    }

    /**
     * Gets the return url externa.
     *
     * @return the return url externa
     */
    public String getReturnUrlExterna() {
        return returnUrlExterna;
    }

    /**
     * Sets the return url externa.
     *
     * @param pReturnUrlExterna
     *            the new return url externa
     */
    public void setReturnUrlExterna(final String pReturnUrlExterna) {
        returnUrlExterna = pReturnUrlExterna;
    }

    /**
     * Gets the timeout ticket externa.
     *
     * @return the timeout ticket externa
     */
    public Long getTimeoutTicketExterna() {
        return timeoutTicketExterna;
    }

    /**
     * Sets the timeout ticket externa.
     *
     * @param pTimeoutTicketExterna
     *            the new timeout ticket externa
     */
    public void setTimeoutTicketExterna(final Long pTimeoutTicketExterna) {
        timeoutTicketExterna = pTimeoutTicketExterna;
    }

    /**
     * Gets the timeout sesion externa.
     *
     * @return the timeout sesion externa
     */
    public Long getTimeoutSesionExterna() {
        return timeoutSesionExterna;
    }

    /**
     * Sets the timeout sesion externa.
     *
     * @param pTimeoutSesionExterna
     *            the new timeout sesion externa
     */
    public void setTimeoutSesionExterna(final Long pTimeoutSesionExterna) {
        timeoutSesionExterna = pTimeoutSesionExterna;
    }

    /**
     * Gets the inicio url externa.
     *
     * @return the inicioUrlExterna
     */
    public String getInicioUrlExterna() {
        return inicioUrlExterna;
    }

    /**
     * Sets the inicio url externa.
     *
     * @param pInicioUrlExterna
     *            the inicioUrlExterna to set
     */
    public void setInicioUrlExterna(final String pInicioUrlExterna) {
        inicioUrlExterna = pInicioUrlExterna;
    }

    /**
     * @return the simularClave
     */
    public boolean isAccesoClaveSimulado() {
        return accesoClaveSimulado;
    }

    /**
     * @param pSimularClave
     *            the simularClave to set
     */
    public void setAccesoClaveSimulado(final boolean pSimularClave) {
        accesoClaveSimulado = pSimularClave;
    }

    public String getClaveFirmaAppId() {
        return claveFirmaAppId;
    }

    public void setClaveFirmaAppId(String claveFirmaAppId) {
        this.claveFirmaAppId = claveFirmaAppId;
    }

    /**
     * @return the claveFirmaCallback
     */
    public String getClaveFirmaCallback() {
        return claveFirmaCallback;
    }

    /**
     * @param claveFirmaCallback
     *            the claveFirmaCallback to set
     */
    public void setClaveFirmaCallback(String claveFirmaCallback) {
        this.claveFirmaCallback = claveFirmaCallback;
    }

    /**
     * @return the accesoClaveFirmaDeshabilitado
     */
    public boolean isClaveFirmaAccesoDeshabilitado() {
        return claveFirmaAccesoDeshabilitado;
    }

    /**
     * @param accesoClaveFirmaDeshabilitado
     *            the accesoClaveFirmaDeshabilitado to set
     */
    public void setClaveFirmaAccesoDeshabilitado(
            boolean accesoClaveFirmaDeshabilitado) {
        this.claveFirmaAccesoDeshabilitado = accesoClaveFirmaDeshabilitado;
    }

    /**
     * @return the claveFirmaUrlInicio
     */
    public String getClaveFirmaUrlInicio() {
        return claveFirmaUrlInicio;
    }

    /**
     * @param claveFirmaUrlInicio
     *            the claveFirmaUrlInicio to set
     */
    public void setClaveFirmaUrlInicio(String claveFirmaUrlInicio) {
        this.claveFirmaUrlInicio = claveFirmaUrlInicio;
    }

    /**
     * @return the claveFirmaTimeoutTicket
     */
    public Long getClaveFirmaTimeoutTicket() {
        return claveFirmaTimeoutTicket;
    }

    /**
     * @param claveFirmaTimeoutTicket
     *            the claveFirmaTimeoutTicket to set
     */
    public void setClaveFirmaTimeoutTicket(Long claveFirmaTimeoutTicket) {
        this.claveFirmaTimeoutTicket = claveFirmaTimeoutTicket;
    }

    /**
     * @return the clavefirmaTimeoutSesion
     */
    public Long getClavefirmaTimeoutSesion() {
        return clavefirmaTimeoutSesion;
    }

    /**
     * @param clavefirmaTimeoutSesion
     *            the clavefirmaTimeoutSesion to set
     */
    public void setClavefirmaTimeoutSesion(Long clavefirmaTimeoutSesion) {
        this.clavefirmaTimeoutSesion = clavefirmaTimeoutSesion;
    }

    /**
     * @return the fireAccesoDeshabilitado
     */
    public boolean isFireAccesoDeshabilitado() {
        return fireAccesoDeshabilitado;
    }

    /**
     * @param fireAccesoDeshabilitado
     *            the fireAccesoDeshabilitado to set
     */
    public void setFireAccesoDeshabilitado(boolean fireAccesoDeshabilitado) {
        this.fireAccesoDeshabilitado = fireAccesoDeshabilitado;
    }

    /**
     * @return the fireAppId
     */
    public String getFireAppId() {
        return fireAppId;
    }

    /**
     * @param fireAppId
     *            the fireAppId to set
     */
    public void setFireAppId(String fireAppId) {
        this.fireAppId = fireAppId;
    }

    /**
     * @return the fireCallback
     */
    public String getFireCallback() {
        return fireCallback;
    }

    /**
     * @param fireCallback
     *            the fireCallback to set
     */
    public void setFireCallback(String fireCallback) {
        this.fireCallback = fireCallback;
    }

    /**
     * @return the fireUrlInicio
     */
    public String getFireUrlInicio() {
        return fireUrlInicio;
    }

    /**
     * @param fireUrlInicio
     *            the fireUrlInicio to set
     */
    public void setFireUrlInicio(String fireUrlInicio) {
        this.fireUrlInicio = fireUrlInicio;
    }

    /**
     * @return the fireTimeoutTicket
     */
    public Long getFireTimeoutTicket() {
        return fireTimeoutTicket;
    }

    /**
     * @param fireTimeoutTicket
     *            the fireTimeoutTicket to set
     */
    public void setFireTimeoutTicket(Long fireTimeoutTicket) {
        this.fireTimeoutTicket = fireTimeoutTicket;
    }

    /**
     * @return the fireTimeoutSesion
     */
    public Long getFireTimeoutSesion() {
        return fireTimeoutSesion;
    }

    /**
     * @param fireTimeoutSesion
     *            the fireTimeoutSesion to set
     */
    public void setFireTimeoutSesion(Long fireTimeoutSesion) {
        this.fireTimeoutSesion = fireTimeoutSesion;
    }

    /**
     * @return the pepsLogout
     */
    public String getPepsLogout() {
        return pepsLogout;
    }

    /**
     * @param pepsLogout
     *            the pepsLogout to set
     */
    public void setPepsLogout(String pepsLogout) {
        this.pepsLogout = pepsLogout;
    }

    /**
     * @return the urlLogoutExterna
     */
    public String getInicioLogoutExterna() {
        return inicioLogoutExterna;
    }

    /**
     * @param urlLogoutExterna
     *            the urlLogoutExterna to set
     */
    public void setInicioLogoutExterna(String urlLogoutExterna) {
        this.inicioLogoutExterna = urlLogoutExterna;
    }

    /**
     * @return the returnLogoutUrlExterna
     */
    public String getReturnLogoutUrlExterna() {
        return returnLogoutUrlExterna;
    }

    /**
     * @param returnLogoutUrlExterna
     *            the returnLogoutUrlExterna to set
     */
    public void setReturnLogoutUrlExterna(String returnLogoutUrlExterna) {
        this.returnLogoutUrlExterna = returnLogoutUrlExterna;
    }

    /**
     * @return the clavefirmaTimeoutProceso
     */
    public Long getClaveFirmaTimeoutProceso() {
        return claveFirmaTimeoutProceso;
    }

    /**
     * @param clavefirmaTimeoutProceso
     *            the clavefirmaTimeoutProceso to set
     */
    public void setClaveFirmaTimeoutProceso(Long clavefirmaTimeoutProceso) {
        this.claveFirmaTimeoutProceso = clavefirmaTimeoutProceso;
    }

    /**
     * @return the fireTimeoutProceso
     */
    public Long getFireTimeoutProceso() {
        return fireTimeoutProceso;
    }

    /**
     * @param fireTimeoutProceso
     *            the fireTimeoutProceso to set
     */
    public void setFireTimeoutProceso(Long fireTimeoutProceso) {
        this.fireTimeoutProceso = fireTimeoutProceso;
    }

}
