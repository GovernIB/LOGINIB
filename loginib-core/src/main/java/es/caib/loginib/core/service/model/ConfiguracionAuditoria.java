package es.caib.loginib.core.service.model;

import java.util.List;

import es.caib.loginib.core.api.model.login.types.TypeHash;

/**
 * Configuración auditoria.
 *
 * @author Indra
 */
public class ConfiguracionAuditoria {

	/** Algoritmo usado en autenticación. */
	private TypeHash algoritmoHash;
	/** Indica si se audita ip origen. */
	private boolean ipAuditar;
	/** Si se habilita indica si se marca para mostrar. */
	private boolean ipMostrar;
	/**
	 * Headers para obtener ip origen separadas por ; (se aplicarán en orden y en
	 * último lugar getRemoteAddress).
	 */
	private List<String> ipHeaders;
	/** Indica si se marca para mostrar propiedades XML. */
	private boolean xmlMostrar;

	/**
	 * Método de acceso a hash.
	 *
	 * @return hash
	 */
	public TypeHash getAlgoritmoHash() {
		return algoritmoHash;
	}

	/**
	 * Método para establecer hash.
	 *
	 * @param hash
	 *                 hash a establecer
	 */
	public void setAlgoritmoHash(final TypeHash hash) {
		this.algoritmoHash = hash;
	}

	/**
	 * Método de acceso a ipAuditar.
	 *
	 * @return ipAuditar
	 */
	public boolean isIpAuditar() {
		return ipAuditar;
	}

	/**
	 * Método para establecer ipAuditar.
	 *
	 * @param ipAuditar
	 *                      ipAuditar a establecer
	 */
	public void setIpAuditar(final boolean ipAuditar) {
		this.ipAuditar = ipAuditar;
	}

	/**
	 * Método de acceso a ipMostrar.
	 *
	 * @return ipMostrar
	 */
	public boolean isIpMostrar() {
		return ipMostrar;
	}

	/**
	 * Método para establecer ipMostrar.
	 *
	 * @param ipMostrar
	 *                      ipMostrar a establecer
	 */
	public void setIpMostrar(final boolean ipMostrar) {
		this.ipMostrar = ipMostrar;
	}

	/**
	 * Método de acceso a ipHeaders.
	 *
	 * @return ipHeaders
	 */
	public List<String> getIpHeaders() {
		return ipHeaders;
	}

	/**
	 * Método para establecer ipHeaders.
	 *
	 * @param ipHeaders
	 *                      ipHeaders a establecer
	 */
	public void setIpHeaders(final List<String> ipHeaders) {
		this.ipHeaders = ipHeaders;
	}

	/**
	 * Método de acceso a xmlMostrar.
	 *
	 * @return xmlMostrar
	 */
	public boolean isXmlMostrar() {
		return xmlMostrar;
	}

	/**
	 * Método para establecer xmlMostrar.
	 *
	 * @param xmlMostrar
	 *                       xmlMostrar a establecer
	 */
	public void setXmlMostrar(final boolean xmlMostrar) {
		this.xmlMostrar = xmlMostrar;
	}

}
