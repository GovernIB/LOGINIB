package es.caib.loginib.core.api.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import es.caib.loginib.core.api.exception.GenerarPeticionClaveException;
import es.caib.loginib.core.api.exception.IdpNoValido;
import es.caib.loginib.core.api.model.comun.ConstantesNumero;
import es.caib.loginib.core.api.model.login.types.TypeIdp;
import eu.eidas.auth.commons.protocol.eidas.LevelOfAssurance;

/**
 * Utilidades ClaveLoginUtil.
 *
 * @author Indra
 *
 */
public final class ClaveLoginUtil {

	/**
	 * Convierte a lista idps.
	 *
	 * @param idps
	 *                 idps
	 * @return Boolean si son IDP permitidos
	 */
	public static List<TypeIdp> removeAnonimoFromIdps(final List<TypeIdp> idps) {
		final List<TypeIdp> res = new ArrayList<>();
		for (final TypeIdp s : idps) {
			if (s != TypeIdp.ANONIMO) {
				res.add(s);
			}
		}
		return res;
	}

	/**
	 * Convierte a lista idps.
	 *
	 * @param idps
	 *                 idps
	 * @return Lista de idps
	 */
	public static List<TypeIdp> convertToListIdps(final String idps) {
		final List<TypeIdp> res = new ArrayList<>();
		if (idps != null) {
			final String[] vals = idps.split(";");
			for (final String s : vals) {
				final TypeIdp idp = TypeIdp.fromString(s);
				if (idp == null) {
					throw new IdpNoValido(s);
				}
				res.add(idp);
			}
		}
		return res;
	}

	/**
	 * Convierte a lista idps.
	 *
	 * @param idps
	 *                 idps
	 * @return Boolean si son IDP permitidos
	 */
	public static String convertToStringIdps(final List<TypeIdp> idps) {
		String res = "";
		if (idps != null) {
			for (final TypeIdp idp : idps) {
				res += idp.toString() + ";";
			}
		}
		return res;
	}

	/**
	 * Indica si permite acceso anonimo.
	 *
	 * @param idps
	 *                 idps
	 * @return boolean
	 */
	public static boolean permiteAccesoAnonimo(final List<TypeIdp> idps) {
		return idps.contains(TypeIdp.ANONIMO);
	}

	/**
	 * Indica si permite acceso anonimo.
	 *
	 * @param idps
	 *                 idps
	 * @return boolean
	 */
	public static boolean permiteAccesoClave(final List<TypeIdp> idps) {
		boolean res = false;
		for (final TypeIdp idp : idps) {
			if (idp != TypeIdp.ANONIMO) {
				res = true;
				break;
			}
		}
		return res;
	}

	/**
	 * Traduce a valores clave.
	 *
	 * @param idps
	 *                 idps
	 * @return idps
	 */
	public static String traduceIdpListToIdpClave(final List<TypeIdp> idps) {
		String res = "";
		for (final TypeIdp idp : idps) {
			final String value = traduceIdpToIdpClave(idp);
			if (value != null) {
				res += value + ";";
			}
		}
		return res;
	}

	/**
	 * Traduce tipo autenticacion a valor clave.
	 *
	 * @param idp
	 * @return
	 */
	public static String traduceIdpToIdpClave(final TypeIdp idp) {
		String res = null;
		switch (idp) {
		case ANONIMO:
			res = null;
			break;
		case CERTIFICADO:
			res = "aFirma";
			break;
		case CLAVE_PERMANENTE:
			res = "SS";
			break;
		case CLAVE_PIN:
			res = "AEAT";
			break;
		default:
			res = null;
			break;
		}
		return res;
	}

	/**
	 * Traduce tipo autenticacion a valor clave.
	 *
	 * @param idp
	 * @return
	 */

	public static TypeIdp traduceIdpClaveToIdp(final String idpClave) {
		TypeIdp res = null;
		if (idpClave != null) {
			final String normalized = idpClave.toUpperCase();
			switch (normalized) {
			case "AFIRMA":
				res = TypeIdp.CERTIFICADO;
				break;
			case "SS":
				res = TypeIdp.CLAVE_PERMANENTE;
				break;
			case "AEAT":
				res = TypeIdp.CLAVE_PIN;
				break;
			default:
				res = null;
				break;
			}
		}
		return res;
	}

	/**
	 * Extrae nif del formato de nif Clave ES/ES/nif. Los cifs no llevan prefijo.
	 *
	 * @param pNifClave
	 *                      Nif formato clave
	 * @return nif
	 */
	public static String extraerNif(final String pNifClave) {
		String nif = null;
		if (pNifClave != null) {
			final String nifClave = pNifClave.trim();
			final int pos = nifClave.lastIndexOf("ES/ES/");
			if (pos >= 0) {
				// Nifs
				nif = nifClave.substring(pos + "ES/ES/".length());
			} else {
				// Cifs: no tienen prefijo (validamos que tenga formato CIF)
				if (Pattern.matches("[ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}([0-9]||[ABCDEFGHIJ]){1}", nifClave)) {
					nif = nifClave;
				}
			}
		}
		return nif;
	}

	/**
	 * Extrae dato propiedad clave. Quita corchetes.
	 *
	 * @param datoClave
	 *                      dato clave
	 * @return dato clave
	 */
	public static String extraerDatoClave(final String datoClave) {
		String res = null;
		if (datoClave != null) {
			res = datoClave;
			while (res.startsWith("[") && res.endsWith("]")) {
				res = res.substring(1, res.length() - 1);
			}
		}
		return res;
	}

	/**
	 * Verifica si es CIF.
	 *
	 * @param valor
	 *                  CIF
	 * @return si es CIF
	 */
	public static boolean esCif(final String valor) {
		final String sinCif = "[ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}([0-9]||[ABCDEFGHIJ]){1}";
		boolean res = false;
		try {
			if (StringUtils.isBlank(valor) || !Pattern.matches(sinCif, valor)) {
				res = false;
			} else {
				final String codigoControl = valor.substring(valor.length() - ConstantesNumero.N1, valor.length());
				final int[] v1 = { 0, ConstantesNumero.N2, ConstantesNumero.N4, ConstantesNumero.N6,
						ConstantesNumero.N8, ConstantesNumero.N1, ConstantesNumero.N3, ConstantesNumero.N5,
						ConstantesNumero.N7, ConstantesNumero.N9 };
				final String[] v2 = { "J", "A", "B", "C", "D", "E", "F", "G", "H", "I" };
				int suma = 0;
				for (int i = ConstantesNumero.N2; i <= ConstantesNumero.N6; i += ConstantesNumero.N2) {
					suma += v1[Integer.parseInt(valor.substring(i - ConstantesNumero.N1, i))];
					suma += Integer.parseInt(valor.substring(i, i + ConstantesNumero.N1));
				}
				suma += v1[Integer.parseInt(valor.substring(ConstantesNumero.N7, ConstantesNumero.N8))];
				suma = (ConstantesNumero.N10 - (suma % ConstantesNumero.N10));
				if (suma == ConstantesNumero.N10) {
					suma = 0;
				}

				final String letraControl = v2[suma];
				res = codigoControl.equals(Integer.toString(suma)) || codigoControl.toUpperCase().equals(letraControl);
			}
		} catch (final IllegalArgumentException e) {
			res = false;
		}
		return res;
	}

	/**
	 * Convierte qaa a LevelOfAssurance.
	 *
	 * @param qaa
	 *                qaa
	 * @return LevelOfAssurance
	 */
	public static LevelOfAssurance convertQaaToLevelOfAssurance(final Integer qaa) {
		if (qaa == null) {
			throw new GenerarPeticionClaveException("No se ha establecido parametro qaa");
		}
		LevelOfAssurance level = null;
		switch (qaa) {
		case 1:
			level = LevelOfAssurance.LOW;
			break;
		case 2:
			level = LevelOfAssurance.SUBSTANTIAL;
			break;
		case 3:
			level = LevelOfAssurance.HIGH;
			break;
		default:
			throw new GenerarPeticionClaveException("Nivel qaa no soportado: " + qaa);
		}
		return level;
	}

	/**
	 * Convierte qaa a LevelOfAssurance.
	 *
	 * @param qaa
	 *                qaa
	 * @return LevelOfAssurance
	 */
	public static Integer convertLevelOfAssuranceToQaa(final LevelOfAssurance level) {
		if (level == null) {
			throw new GenerarPeticionClaveException("No se ha establecido parametro level");
		}

		Integer qaa = null;
		switch (level) {
		case LOW:
			qaa = 1;
			break;
		case SUBSTANTIAL:
			qaa = 2;
			break;
		case HIGH:
			qaa = 3;
			break;
		default:
			throw new GenerarPeticionClaveException("Nivel no soportado: " + level);
		}
		return qaa;
	}

	/**
	 * Convierte issuer a idp.
	 *
	 * @param issuer
	 *                   issuer
	 * @return idp
	 */
	public static TypeIdp convertIssuerToIdp(final String issuer) {
		TypeIdp idp = null;
		if (issuer != null) {
			if (issuer.equals("[AFIRMA]")) {
				idp = TypeIdp.CERTIFICADO;
			}
			if (issuer.equals("[PIN24H]")) {
				idp = TypeIdp.CLAVE_PIN;
			}
			if (issuer.endsWith("[SEGSOC]")) {
				idp = TypeIdp.CLAVE_PERMANENTE;
			}
			// TODO STORK
		}

		if (idp == null) {
			throw new IdpNoValido(issuer);
		}

		return idp;
	}

}
