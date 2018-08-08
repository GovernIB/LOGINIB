package es.caib.loginib.frontend.model;

/**
 * Datos para iniciar sesion en clave.
 *
 * @author Indra
 *
 */
public final class DatosSeleccionAutenticacion {

    /** Id sesion. */
    private String idSesion;

    /** Idioma. */
    private String idioma;

    /** Permite clave. */
    private boolean clave;

    /** Permite anonimo. */
    private boolean anonimo;

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
     * Método de acceso a idioma.
     * 
     * @return idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Método para establecer idioma.
     * 
     * @param idioma
     *            idioma a establecer
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Método de acceso a clave.
     * 
     * @return clave
     */
    public boolean isClave() {
        return clave;
    }

    /**
     * Método para establecer clave.
     * 
     * @param clave
     *            clave a establecer
     */
    public void setClave(boolean clave) {
        this.clave = clave;
    }

    /**
     * Método de acceso a anonimo.
     * 
     * @return anonimo
     */
    public boolean isAnonimo() {
        return anonimo;
    }

    /**
     * Método para establecer anonimo.
     * 
     * @param anonimo
     *            anonimo a establecer
     */
    public void setAnonimo(boolean anonimo) {
        this.anonimo = anonimo;
    }

}
