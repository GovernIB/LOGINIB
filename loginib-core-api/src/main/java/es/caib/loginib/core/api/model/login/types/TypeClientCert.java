package es.caib.loginib.core.api.model.login.types;

/**
 * Tipo ClientCert.
 *
 * @author Indra
 *
 */
public enum TypeClientCert {
	/**
	 * Header.
	 */
	HEADER("HEADER"),
	/**
	 * AJP.
	 */
	AJP("AJP");

	/**
	 * Valor como string.
	 */
	private final String stringValue;

	/**
	 * Constructor.
	 *
	 * @param value
	 *                  Valor como string.
	 */
	private TypeClientCert(final String value) {
		stringValue = value;
	}

	@Override
	public String toString() {
		return stringValue;
	}

	/**
	 * Método para From string de la clase TypeEstadoDocumento.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type estado documento
	 */
	public static TypeClientCert fromString(final String text) {
		TypeClientCert respuesta = null;
		if (text != null) {
			for (final TypeClientCert b : TypeClientCert.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

}
