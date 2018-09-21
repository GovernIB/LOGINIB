package es.caib.loginib.frontend.model;

/**
 * Datos para iniciar sesion en clave.
 *
 * @author Indra
 *
 */
public final class DatosInicioSesionClave {

    /** Url clave. */
    private String urlClave;

    // TODO CLAVE2 parece que ya no se usan
    /** Metodos autenticacion permitidos en clave. */
    private String idps;

    /** Peticion SAML de autenticacion. */
    private String samlRequest;

    /** Idioma. */
    private String idioma;

    /** Relay state. */
    private String relayState;

    /**
     * Obtiene url clave.
     *
     * @return the urlClave
     */
    public String getUrlClave() {
        return urlClave;
    }

    /**
     * Establece url clave.
     *
     * @param urlClave
     *            the urlClave to set
     */
    public void setUrlClave(final String urlClave) {
        this.urlClave = urlClave;
    }

    /**
     * Obtiene idps.
     *
     * @return the idps
     */
    public String getIdps() {
        return idps;
    }

    /**
     * Establece idps.
     *
     * @param idps
     *            the idps to set
     */
    public void setIdps(final String idps) {
        this.idps = idps;
    }

    /**
     * Obtiene saml request.
     *
     * @return the samlRequest
     */
    public String getSamlRequest() {
        return samlRequest;
    }

    /**
     * Establece saml request.
     *
     * @param samlRequest
     *            the samlRequest to set
     */
    public void setSamlRequest(final String samlRequest) {
        this.samlRequest = samlRequest;
    }

    /**
     * Obtiene idioma.
     *
     * @return the idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Establece idioma.
     *
     * @param idioma
     *            the idioma to set
     */
    public void setIdioma(final String idioma) {
        this.idioma = idioma;
    }

    /**
     * Método de acceso a relayState.
     * 
     * @return relayState
     */
    public String getRelayState() {
        return relayState;
    }

    /**
     * Método para establecer relayState.
     * 
     * @param relayState
     *            relayState a establecer
     */
    public void setRelayState(String relayState) {
        this.relayState = relayState;
    }

}
