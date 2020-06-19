package es.caib.loginib.core.api.model.login;

import es.caib.loginib.core.api.model.login.types.TypePropiedad;

/**
 * Propiedad autenticacion.
 *
 * @author Indra
 *
 */
public class PropiedadAutenticacion {

	/** Propiedad. */
	private String propiedad;

	/** Tipo. */
	private TypePropiedad tipo;

	/** Valor. */
	private String valor;

	/** Permite indicar si se debería mostar o no al usuario. */
	private boolean mostrar;

	/**
	 * Constructor.
	 *
	 * @param propiedad
	 *                      propiedad
	 * @param tipo
	 *                      tipo
	 * @param valor
	 *                      valor
	 * @param mostrar
	 *                      mostrar
	 */
	public PropiedadAutenticacion(final String propiedad, final TypePropiedad tipo, final String valor,
			final boolean mostrar) {
		super();
		this.propiedad = propiedad;
		this.tipo = tipo;
		this.valor = valor;
		this.mostrar = mostrar;
	}

	/**
	 * Constructor.
	 */
	public PropiedadAutenticacion() {
		super();
	}

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
	public TypePropiedad getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 *
	 * @param tipo
	 *                 tipo a establecer
	 */
	public void setTipo(final TypePropiedad tipo) {
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
