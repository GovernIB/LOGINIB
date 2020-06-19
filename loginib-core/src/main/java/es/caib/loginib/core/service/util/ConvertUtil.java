package es.caib.loginib.core.service.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import es.caib.loginib.core.api.exception.HashException;
import es.caib.loginib.core.api.model.login.types.TypeHash;

/**
 * Utilidades de conversion. Para la conversion de cadenas a bytes y viceversa
 * en los metodos para convertir a base 64 o hex se emplea el charset UTF-8.
 *
 */
public final class ConvertUtil {

	/** CHARSET UTF-8. */
	private static final String CHARSET = "UTF-8";

	/**
	 * Convierte cadena a B64 Url Safe.
	 *
	 * @param cadena
	 *                   cadena
	 * @return B64 Url Safe
	 * @throws Exception
	 *                       Exception
	 */
	public static String cadenaToBase64UrlSafe(final String cadena) throws Exception {
		return bytesToBase64UrlSafe(getBytes(cadena));
	}

	/**
	 * Convierte cadena a B64 Url.
	 *
	 * @param cadena
	 *                   cadena
	 * @return B64 Url Safe
	 * @throws Exception
	 *                       Exception
	 */
	public static String cadenaToBase64(final String cadena) throws Exception {
		return bytesToBase64(getBytes(cadena), false);
	}

	/**
	 * Decodifica una cadena en B64 Url Safe a texto.
	 *
	 * @param cadenaB64
	 *                      B64 Url safe
	 * @return Cadena cadena
	 * @throws Exception
	 *                       Exception
	 */
	public static String base64UrlSafeToCadena(final String cadenaB64) throws Exception {
		return getString(base64UrlSafeToBytes(cadenaB64));
	}

	// -------------------------------------------------------------------------------
	// FUNCIONES PRIVADAS
	// -------------------------------------------------------------------------------

	/**
	 * Pasa bytes a B64 Url Safe.
	 *
	 * @param bytes
	 *                  bytes
	 * @return B64 url safe
	 * @throws Exception
	 *                       Exception
	 */
	private static String bytesToBase64UrlSafe(final byte[] bytes) throws Exception {
		return bytesToBase64(bytes, true);
	}

	/**
	 * Pasa bytes a B64.
	 *
	 * @param bytes
	 *                  Bytes
	 * @param safe
	 *                  Si genera url safe
	 * @return B64
	 * @throws Exception
	 *                       Exception.
	 */
	private static String bytesToBase64(final byte[] bytes, final boolean safe) throws Exception {
		String b64 = new String(Base64.encodeBase64(bytes));
		if (safe) {
			b64 = escapeChars64UrlSafe(b64);
		}
		return b64;
	}

	/**
	 * Escape caracteres para b64 url safe.
	 *
	 * @param cadena
	 *                   cadena
	 * @return cadena escapada
	 */
	private static String escapeChars64UrlSafe(final String cadena) {
		String cad = cadena;
		cad = cad.replaceAll("\\+", "-");
		cad = cad.replaceAll("/", "_");
		cad = cad.replaceAll("[\\n\\r]", "");
		return cad;
	}

	/**
	 * Unescape caracteres para b64 url safe.
	 *
	 * @param cadena
	 *                   cadena
	 * @return cadena unescapada
	 */
	private static String unescapeChars64UrlSafe(final String cadena) {
		String cad = cadena;
		cad = cad.replaceAll("-", "+");
		cad = cad.replaceAll("_", "/");
		return cad;
	}

	/**
	 * Obtiene bytes.
	 *
	 * @param cadena
	 *                   cadena
	 * @return bytes
	 * @throws Exception
	 *                       Exception
	 */
	private static byte[] getBytes(final String cadena) throws Exception {
		return cadena.getBytes(CHARSET);
	}

	/**
	 * Crea String.
	 *
	 * @param bytes
	 *                  Bytes
	 * @return string
	 * @throws Exception
	 *                       Exception
	 */
	private static String getString(final byte[] bytes) throws Exception {
		return new String(bytes, CHARSET);
	}

	/**
	 * B64 Url Safe a Bytes.
	 *
	 * @param cadenaB64
	 *                      B64 url safe
	 * @return bytes bytes
	 * @throws Exception
	 *                       Exception
	 */
	private static byte[] base64UrlSafeToBytes(final String cadenaB64) throws Exception {
		return base64ToBytes(cadenaB64, true);
	}

	/**
	 * Base64 to Bytes.
	 *
	 * @param cadenaB64
	 *                      cadena B64
	 * @param safe
	 *                      si es url safe
	 * @return bytes
	 * @throws Exception
	 *                       Exception
	 */

	private static byte[] base64ToBytes(final String cadenaB64, final boolean safe) throws Exception {
		String b64 = cadenaB64;
		if (safe) {
			b64 = unescapeChars64UrlSafe(cadenaB64);
		}
		return Base64.decodeBase64(b64);
	}

	/**
	 * Genera hash.
	 *
	 * @param source
	 *                      cadena
	 * @param algoritmo
	 *                      algoritmo
	 * @return hash
	 * @throws NoSuchAlgorithmException
	 */
	public static String generateHash(final String source, final TypeHash algoritmo) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(algoritmo.toString());
		} catch (final NoSuchAlgorithmException e) {
			throw new HashException(e);
		}
		final byte[] encodedhash = digest.digest(source.getBytes(StandardCharsets.UTF_8));
		return bytesToHex(encodedhash);
	}

	/**
	 * Convierte a hexadecimal.
	 *
	 * @param hash
	 *                 hash
	 * @return
	 */
	public static String bytesToHex(final byte[] hash) {
		final StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			final String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

}
