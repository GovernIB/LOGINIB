package es.caib.loginib.core.api.model.login.types;

/**
 * Enum para indicar el tipo de filtro de fecha.
 *
 * @author Indra
 *
 */
public enum TypeFiltroFecha {
	/**
	 * CREACION.
	 */
	CREACION("C"),
	/**
	 * PAGO
	 */
	PAGO("P");

	/** Valor. **/
	private String valor;

	/** Constructor. **/
	private TypeFiltroFecha(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeFiltroFecha fromString(final String text) {
		TypeFiltroFecha respuesta = null;
		if (text != null) {
			for (final TypeFiltroFecha b : TypeFiltroFecha.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

	@Override
	public String toString() {
		return valor;
	}
}
