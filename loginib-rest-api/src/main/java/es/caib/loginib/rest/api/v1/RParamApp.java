package es.caib.loginib.rest.api.v1;

import io.swagger.annotations.ApiModelProperty;

/**
 * Respuesta parametro.
 *
 * @author indra
 *
 */
public final class RParamApp {

	/**
	 * Propiedad.
	 */
	@ApiModelProperty(value = "Propiedad", required = true)
	private String propiedad;

	/**
	 * Valor.
	 */
	@ApiModelProperty(value = "Valor", required = true)
	private String valor;

	/**
	 * Método de acceso a propiedad.
	 *
	 * @return propiedad
	 */
	public String getPropiedad() {
		return propiedad;
	}

	/**
	 * Método para establecer propiedad.
	 *
	 * @param propiedad propiedad a establecer
	 */
	public void setPropiedad(final String propiedad) {
		this.propiedad = propiedad;
	}

	/**
	 * Método de acceso a valor.
	 *
	 * @return valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * Método para establecer valor.
	 *
	 * @param valor valor a establecer
	 */
	public void setValor(final String valor) {
		this.valor = valor;
	}

}
