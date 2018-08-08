package es.caib.loginib.rest.api.v1;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Peticion iniciar sesion.
 *
 * @author indra
 *
 */
@ApiModel(value = "Parámetros logout", description = "Parámetros logout")
public final class RLogoutParams {

    /**
     * Código Entidad.
     */
    @ApiModelProperty(value = "Código Entidad (DIR3)", required = true)
    private String entidad;

    /**
     * Url Callback.
     */
    @ApiModelProperty(value = "Url Callback", required = true)
    private String urlCallback;

    /**
     * Idioma.
     */
    @ApiModelProperty(value = "Idioma", required = true)
    private String idioma;

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
