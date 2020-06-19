package es.caib.loginib.core.api.model.login.types;

/**
 * Tipo autenticación.
 *
 * @author Indra
 *
 */
public enum TypeIdp {
	/**
	 * Anónimo.
	 */
	ANONIMO("ANONIMO"),
	/**
	 * Certificado digital.
	 */
	CERTIFICADO("CLAVE_CERTIFICADO"),
	/**
	 * Clave pin 24h.
	 */
	CLAVE_PIN("CLAVE_PIN"),
	/**
	 * Clave permanente.
	 */
	CLAVE_PERMANENTE("CLAVE_PERMANENTE"),
	/**
	 * ClientCert.
	 */
	CLIENTCERT("CLIENTCERT"),
	/**
	 * Usuario/Password.
	 */
	USUARIO_PASSWORD("USUARIO_PASSWORD");

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
	private TypeIdp(final String value) {
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
	public static TypeIdp fromString(final String text) {
		TypeIdp respuesta = null;
		if (text != null) {
			for (final TypeIdp b : TypeIdp.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

}
