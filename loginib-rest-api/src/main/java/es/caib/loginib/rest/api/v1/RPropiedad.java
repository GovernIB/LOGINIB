package es.caib.loginib.rest.api.v1;

import io.swagger.annotations.ApiModelProperty;

/**
 * Respuesta datos ticket.
 *
 * @author indra
 *
 */
public final class RPropiedad {

	/**
	 * Propiedad.
	 */
	@ApiModelProperty(value = "Propiedad", required = true)
	private String propiedad;

	/**
	 * Tipo valor.
	 */
	@ApiModelProperty(value = "Tipo: String / XML (Base64)", required = true)
	private String tipo;

	/**
	 * Valor.
	 */
	@ApiModelProperty(value = "Valor", required = true)
	private String valor;

	/**
	 * Valor.
	 */
	@ApiModelProperty(value = "Valor", required = true)
	private boolean mostrar;

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
	 * @param propiedad
	 *                      propiedad a establecer
	 */
	public void setPropiedad(final String propiedad) {
		this.propiedad = propiedad;
	}

	/**
	 * Método de acceso a tipo.
	 *
	 * @return tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 *
	 * @param tipo
	 *                 tipo a establecer
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
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
	 * @param valor
	 *                  valor a establecer
	 */
	public void setValor(final String valor) {
		this.valor = valor;
	}

	/**
	 * Método de acceso a mostrar.
	 * 
	 * @return mostrar
	 */
	public boolean isMostrar() {
		return mostrar;
	}

	/**
	 * Método para establecer mostrar.
	 * 
	 * @param mostrar
	 *                    mostrar a establecer
	 */
	public void setMostrar(final boolean mostrar) {
		this.mostrar = mostrar;
	}

}
