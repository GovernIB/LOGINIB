package es.caib.loginib.rest.api.v1;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * Evidencias autenticación.
 *
 * @author indra
 *
 */
public final class REvidenciasAutenticacion {

	/**
	 * Propiedades.
	 */
	@ApiModelProperty(value = "Lista de evidencias", required = true)
	private List<RPropiedad> evidencias;

	/**
	 * Hash.
	 */
	@ApiModelProperty(value = "Huella electrónica autenticación", required = true)
	private String huellaElectronica;

	/**
	 * Método de acceso a evidencias.
	 * 
	 * @return evidencias
	 */
	public List<RPropiedad> getEvidencias() {
		return evidencias;
	}

	/**
	 * Método para establecer evidencias.
	 * 
	 * @param evidencias
	 *                       evidencias a establecer
	 */
	public void setEvidencias(final List<RPropiedad> evidencias) {
		this.evidencias = evidencias;
	}

	/**
	 * Método de acceso a huellaElectronica.
	 * 
	 * @return huellaElectronica
	 */
	public String getHuellaElectronica() {
		return huellaElectronica;
	}

	/**
	 * Método para establecer huellaElectronica.
	 * 
	 * @param huellaElectronica
	 *                              huellaElectronica a establecer
	 */
	public void setHuellaElectronica(final String huellaElectronica) {
		this.huellaElectronica = huellaElectronica;
	}

}
