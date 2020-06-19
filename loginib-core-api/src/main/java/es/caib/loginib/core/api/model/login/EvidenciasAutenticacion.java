package es.caib.loginib.core.api.model.login;

import es.caib.loginib.core.api.model.login.types.TypeHash;

/**
 * Evidencias autenticación.
 *
 * @author Indra
 *
 */
public class EvidenciasAutenticacion {

	/** Evidencias. */
	private EvidenciasLista evidenciasLista;

	/** Evidencias JSON. */
	private String evidenciasJSON;

	/** Hash evidencias. */
	private String evidenciasHash;

	/** Algoritmo hash. */
	private TypeHash algoritmoHash;

	/**
	 * Método de acceso a evidenciasJSON.
	 *
	 * @return evidenciasJSON
	 */
	public String getEvidenciasJSON() {
		return evidenciasJSON;
	}

	/**
	 * Método para establecer evidenciasJSON.
	 *
	 * @param evidenciasJSON
	 *                           evidenciasJSON a establecer
	 */
	public void setEvidenciasJSON(final String evidenciasJSON) {
		this.evidenciasJSON = evidenciasJSON;
	}

	/**
	 * Método de acceso a evidenciasHash.
	 *
	 * @return evidenciasHash
	 */
	public String getEvidenciasHash() {
		return evidenciasHash;
	}

	/**
	 * Método para establecer evidenciasHash.
	 *
	 * @param evidenciasHash
	 *                           evidenciasHash a establecer
	 */
	public void setEvidenciasHash(final String evidenciasHash) {
		this.evidenciasHash = evidenciasHash;
	}

	/**
	 * Método de acceso a algoritmoHash.
	 *
	 * @return algoritmoHash
	 */
	public TypeHash getAlgoritmoHash() {
		return algoritmoHash;
	}

	/**
	 * Método para establecer algoritmoHash.
	 *
	 * @param algoritmoHash
	 *                          algoritmoHash a establecer
	 */
	public void setAlgoritmoHash(final TypeHash algoritmoHash) {
		this.algoritmoHash = algoritmoHash;
	}

	/**
	 * Método de acceso a evidenciasLista.
	 * 
	 * @return evidenciasLista
	 */
	public EvidenciasLista getEvidenciasLista() {
		return evidenciasLista;
	}

	/**
	 * Método para establecer evidenciasLista.
	 * 
	 * @param evidenciasLista
	 *                            evidenciasLista a establecer
	 */
	public void setEvidenciasLista(final EvidenciasLista evidenciasLista) {
		this.evidenciasLista = evidenciasLista;
	}

}
