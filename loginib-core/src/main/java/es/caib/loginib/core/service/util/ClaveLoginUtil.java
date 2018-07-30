package es.caib.loginib.core.service.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import es.caib.loginib.core.api.model.comun.ConstantesNumero;

/**
 * Utilidades ClaveLoginUtil.
 *
 * @author Indra
 *
 */
public final class ClaveLoginUtil {

	/**
	 * Valida si son IDP permitidos.
	 *
	 * @param idps
	 *            idps
	 * @return Boolean si son IDP permitidos
	 */
	public static boolean validarIDPs(final String idps) {
		boolean res = false;
		if (idps != null) {
			final String[] vals = idps.split(";");
			for (int i = 0; i < vals.length; i++) {
				res = validarIDP(vals[i]);
				if (!res) {
					break;
				}
			}
		}
		return res;
	}

	/**
	 * Valida si es un IDP permitido.
	 *
	 * @param idp
	 *            idp
	 * @return Boolean si es un IDP permitido
	 */
	public static boolean validarIDP(final String idp) {
		boolean res = false;
		if ("AFIRMA".equalsIgnoreCase(idp)) {
			res = true;
		} else if ("AEAT".equalsIgnoreCase(idp)) {
			res = true;
		} else if ("SS".equalsIgnoreCase(idp)) {
			res = true;
		}
		return res;
	}

	/**
	 * Convertir IDP (valores nuevos a antiguos).
	 *
	 * @param idp
	 *            idp
	 * @return Convierte IDP a nuevos valores
	 */
	public static String convertirAnteriorIDP(final String idp) {
		String res = idp;
		if ("PIN24H".equalsIgnoreCase(idp)) {
			res = "AEAT";
		} else if ("SEGSOC".equalsIgnoreCase(idp)) {
			res = "SS";
		}
		return res;
	}

	/**
	 * Convertir IDP (valores antiguos a nuevos).
	 *
	 * @param idp
	 *            idp
	 * @return Convierte IDP a nuevos valores
	 */
	public static String convertirNuevoIDP(final String idp) {
		String res = idp;
		if ("AEAT".equalsIgnoreCase(idp)) {
			res = "PIN24H";
		} else if ("SS".equalsIgnoreCase(idp)) {
			res = "SEGSOC";
		}
		return res;
	}

	/**
	 * Extrae nif del formato de nif Clave ES/ES/nif. Los cifs no llevan prefijo.
	 *
	 * @param pNifClave
	 *            Nif formato clave
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
	 * Verifica si es CIF.
	 *
	 * @param valor
	 *            CIF
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

}
