package es.caib.loginib.rest.api.v1;

import io.swagger.annotations.ApiModelProperty;

/**
 * Datos representante.
 *
 * @author Indra
 *
 */
public class RDatosRepresentante {

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
}
