package es.caib.loginib.frontend.model;

/**
 * Codigos error.
 *
 * @author Indra
 *
 */
public enum ErrorCodes {

	/** Error general. */
	ERROR_GENERAL("error.errorGeneral"),
	/** Error validacion login. */
	ERROR_LOGIN("error.errorLogin"),
	/** Usuario/password no valido. */
	USUARIOPASSWORD_NO_VALIDO("error.usuarioPasswordNovalido"),
	/** Nivel QAA insuficiente. */
	QAA_INSUFICIENTE("error.qaaInsuficiente"),
	/** Certificado caducado. */
	CERT_CADUCADO("error.certificadoCaducado"),
	/** Certificado revocado. */
	CERT_REVOCADO("error.certificadoRevocado"),
	/** No se envía certificado. */
	NO_CERT("error.noCertificado"),
	/** Tipo certificado no permitido. */
	CERT_NO_PERMITIDO("error.certificadoNoPermitido"),
	/** Usuario/password no valido. */
	DESGLOSE_APELLIDOS_INCORRECTO("error.errorApellidosDesglose");

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
	ErrorCodes(final String value) {
		stringValue = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValue;
	}

	/**
	 * Método para From string de la clase ErrorCodes.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type autenticacion
	 */
	public static ErrorCodes fromString(final String text) {
		ErrorCodes respuesta = null;
		if (text != null) {
			for (final ErrorCodes b : ErrorCodes.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

	/**
	 * Método para From string de la clase ErrorCodes.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type autenticacion
	 */
	public static ErrorCodes fromEnum(final String text) {
		ErrorCodes res = null;
		try {
			res = ErrorCodes.valueOf(text);
		} catch (final Exception ex) {
			res = null;
		}
		return res;
	}

}
