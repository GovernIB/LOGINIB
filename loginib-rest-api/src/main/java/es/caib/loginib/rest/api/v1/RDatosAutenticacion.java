package es.caib.loginib.rest.api.v1;

import io.swagger.annotations.ApiModelProperty;

/**
 * Respuesta datos ticket.
 *
 * @author indra
 *
 */
public final class RDatosAutenticacion {

    /**
     * Metodo autenticacion (Valores:
     * ANONIMO;CLAVE_CERTIFICADO;CLAVE_PIN;CLAVE_PERMANENTE).
     *
     */
    @ApiModelProperty(value = "Metodo autenticacion (Valores: ANONIMO;CLAVE_CERTIFICADO;CLAVE_PIN;CLAVE_PERMANENTE)", required = true)
    private String metodoAutenticacion;

    /**
     * Nif.
     *
     */
    @ApiModelProperty(value = "Nif", required = true)
    private String nif;

    /**
     * Nombre.
     *
     */
    @ApiModelProperty(value = "Nombre", required = true)
    private String nombre;

    /**
     * Apellidos.
     *
     */
    @ApiModelProperty(value = "Apellidos", required = false)
    private String apellidos;

    /**
     * Apellido 1.
     *
     */
    @ApiModelProperty(value = "Apellido 1", required = false)
    private String apellido1;

    /**
     * Apellido 2.
     *
     */
    @ApiModelProperty(value = "Apellido 2", required = false)
    private String apellido2;

    /**
     * Representante en caso de persona jurídica.
     */
    @ApiModelProperty(value = "Representante en caso de persona jurídica", required = false)
    private RDatosRepresentante representante;

    /**
     * Método de acceso a metodoAutenticacion.
     *
     * @return metodoAutenticacion
     */
    public String getMetodoAutenticacion() {
        return metodoAutenticacion;
    }

    /**
     * Método para establecer metodoAutenticacion.
     *
     * @param metodoAutenticacion
     *            metodoAutenticacion a establecer
     */
    public void setMetodoAutenticacion(String metodoAutenticacion) {
        this.metodoAutenticacion = metodoAutenticacion;
    }

    /**
     * Método de acceso a nif.
     *
     * @return nif
     */
    public String getNif() {
        return nif;
    }

    /**
     * Método para establecer nif.
     *
     * @param nif
     *            nif a establecer
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Método de acceso a nombre.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método para establecer nombre.
     *
     * @param nombre
     *            nombre a establecer
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método de acceso a apellidos.
     *
     * @return apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Método para establecer apellidos.
     *
     * @param apellidos
     *            apellidos a establecer
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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
     * @param apellido1
     *            apellido1 a establecer
     */
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
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
     * @param apellido2
     *            apellido2 a establecer
     */
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    /**
     * Método de acceso a representante.
     *
     * @return representante
     */
    public RDatosRepresentante getRepresentante() {
        return representante;
    }

    /**
     * Método para establecer representante.
     *
     * @param representante
     *            representante a establecer
     */
    public void setRepresentante(RDatosRepresentante representante) {
        this.representante = representante;
    }

}
