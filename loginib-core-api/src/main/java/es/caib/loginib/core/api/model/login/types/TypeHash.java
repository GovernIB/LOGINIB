package es.caib.loginib.core.api.model.login.types;

/**
 * Tipo hash.
 *
 * @author Indra
 *
 */
public enum TypeHash {
	/**
	 * SHA216.
	 */
	SHA216("SHA-256"),
	/**
	 * SHA512.
	 */
	SHA512("SHA-512");

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
	private TypeHash(final String value) {
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
	public static TypeHash fromString(final String text) {
		TypeHash respuesta = null;
		if (text != null) {
			for (final TypeHash b : TypeHash.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

}
