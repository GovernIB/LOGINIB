package es.caib.loginib.core.service.util;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

import es.caib.loginib.core.api.exception.ValidateClaveException;

/**
 * Utilidades AFirma.
 *
 * @author Indra
 *
 */
public final class AFirmaUtil {

	// TODO La respuesta actual de Clave2 no es un XML bien formado y no se
	// puede parsear
	/**
	 * Extrae info del certificado a partir de la respuesta de AFirma en B64.
	 *
	 * @param xmlFirmaB64
	 *                        respuesta de AFirma en B64
	 * @param idSesion
	 *                        Idsesion
	 * @return info del certificado (map con idcampo / valorcampo)
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> extraerInfoCertificado(final String xmlFirmaB64, final String idSesion) {

		try {

			final byte[] aFirmaBytes = Base64.decodeBase64(xmlFirmaB64);

			final SAXReader reader = new SAXReader();
			Document document;

			document = reader.read(new ByteArrayInputStream(aFirmaBytes));

			final Map<String, String> namespaceUris = new HashMap<String, String>();
			namespaceUris.put("dss", "urn:oasis:names:tc:dss:1.0:core:schema");
			namespaceUris.put("afxp", "urn:afirma:dss:1.0:profile:XSS:schema");

			// final XPath xPath = DocumentHelper.createXPath(
			// "//dss:OptionalOutputs/afxp:ReadableCertificateInfo/afxp:ReadableField");
			final XPath xPath = DocumentHelper.createXPath("//afxp:ReadableCertificateInfo/afxp:ReadableField");
			xPath.setNamespaceURIs(namespaceUris);

			final List<Node> nodes = xPath.selectNodes(document);

			final Map<String, String> values = new HashMap<String, String>();

			for (final Node node : nodes) {
				final Element e = (Element) node;
				final String idCampo = ((Element) e.elements().get(0)).getText();
				String valorCampo = null;
				if (e.elements().size() > 1) {
					valorCampo = ((Element) e.elements().get(1)).getText();
				}
				values.put(idCampo, valorCampo);
			}

			return values;

		} catch (final DocumentException ex) {
			throw new ValidateClaveException("Error al interpretar respuesta de aFirma", ex, idSesion);
		}

	}

}
