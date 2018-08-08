package es.caib.loginib.core.service.util;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

import es.caib.loginib.core.api.exception.GenerarPeticionClaveException;

/**
 * Utilidades SamlUtil.
 *
 * @author Indra
 *
 */
public final class SamlUtil {

	/**
	 * Extrae Saml Id de la peticion saml.
	 *
	 * @param xmlB64
	 *            xmlB64
	 * @return saml id
	 */
	public static String extraerSamlId(final String xmlB64) {

		try {

			String samlId = null;

			final byte[] xmlContent = Base64.decodeBase64(xmlB64);

			final SAXReader reader = new SAXReader();

			Document document;
			document = reader.read(new ByteArrayInputStream(xmlContent));

			final Map<String, String> namespaceUris = new HashMap<String, String>();
			namespaceUris.put("saml2p", "urn:oasis:names:tc:SAML:2.0:protocol");
			namespaceUris.put("ds", "http://www.w3.org/2000/09/xmldsig#");
			namespaceUris.put("saml2", "urn:oasis:names:tc:SAML:2.0:assertion");
			namespaceUris.put("stork", "urn:eu:stork:names:tc:STORK:1.0:assertion");
			namespaceUris.put("storkp", "urn:eu:stork:names:tc:STORK:1.0:protocol");

			final XPath xPath = DocumentHelper.createXPath("//saml2p:AuthnRequest");
			xPath.setNamespaceURIs(namespaceUris);

			final Node node = xPath.selectSingleNode(document);
			if (node != null) {
				final Element e = (Element) node;
				samlId = e.attributeValue("ID");
			}

			return samlId;

		} catch (final DocumentException ex) {
			throw new GenerarPeticionClaveException("No se ha podido extraer SamlId de la peticion", ex);
		}

	}

	/**
	 * Extrae Saml Id de la peticion saml.
	 *
	 * @param xmlB64
	 *            xmlB64
	 * @return saml id
	 */
	public static String extraerSamlIdLogout(final String xmlB64) {

		try {

			String samlId = null;

			final byte[] xmlContent = Base64.decodeBase64(xmlB64);

			final SAXReader reader = new SAXReader();

			Document document;
			document = reader.read(new ByteArrayInputStream(xmlContent));

			final Map<String, String> namespaceUris = new HashMap<String, String>();
			namespaceUris.put("saml2p", "urn:oasis:names:tc:SAML:2.0:protocol");
			namespaceUris.put("ds", "http://www.w3.org/2000/09/xmldsig#");
			namespaceUris.put("saml2", "urn:oasis:names:tc:SAML:2.0:assertion");
			namespaceUris.put("stork", "urn:eu:stork:names:tc:STORK:1.0:assertion");
			namespaceUris.put("storkp", "urn:eu:stork:names:tc:STORK:1.0:protocol");

			final XPath xPath = DocumentHelper.createXPath("//saml2p:LogoutRequest");
			xPath.setNamespaceURIs(namespaceUris);

			final Node node = xPath.selectSingleNode(document);
			if (node != null) {
				final Element e = (Element) node;
				samlId = e.attributeValue("ID");
			}

			return samlId;

		} catch (final DocumentException ex) {
			throw new GenerarPeticionClaveException("No se ha podido extraer SamlId de la peticion de logout", ex);
		}

	}

}
