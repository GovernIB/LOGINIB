package es.caib.loginib.core.service.util;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.opensaml.saml2.core.Response;
import org.opensaml.xml.XMLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import eu.eidas.auth.commons.xml.DocumentBuilderFactoryUtil;
import eu.eidas.auth.commons.xml.opensaml.OpenSamlHelper;
import eu.eidas.encryption.exception.UnmarshallException;
import eu.eidas.engine.exceptions.EIDASSAMLEngineException;

public class SPUtil {

    SPUtil() {
    };

    private static final Logger LOG = LoggerFactory.getLogger(SPUtil.class);

    private static final String NO_ASSERTION = "no assertion found";

    private static final String ASSERTION_XPATH = "//*[local-name()='Assertion']";

    // TODO CLAVE2 De momento comento estas funciones, se usan desde factoria SP

    // public static String getConfigFilePath() {
    // /*
    // * String envLocation =
    // * System.getenv().get(Constants.SP_CONFIG_REPOSITORY); String
    // * configLocation = System.getProperty(Constants.SP_CONFIG_REPOSITORY,
    // * envLocation); return configLocation;
    // */
    //
    // // TODO CLAVE2 Retocamos para fijar propiedad ¿se podría cambiar?
    // // return
    // //
    // ((String)ApplicationContextProvider.getApplicationContext().getBean(Constants.SP_REPO_BEAN_NAME)).trim();
    // return System.getProperty(Constants.SP_CONFIG_REPOSITORY);
    // }
    //
    // private static Properties loadConfigs(String fileName) throws IOException
    // {
    // final Properties properties = new Properties();
    // properties.load(new FileReader(SPUtil.getConfigFilePath() + fileName));
    // return properties;
    // }
    //
    // public static Properties loadSPConfigs() {
    // try {
    // return SPUtil.loadConfigs(Constants.SP_PROPERTIES);
    // } catch (final IOException e) {
    // throw new RuntimeException(
    // "Could not load configuration file: " + e.getMessage());
    // }
    // }

    /**
     * Returns true when the input contains an encrypted SAML Response
     *
     * @param tokenSaml
     * @return
     * @throws EIDASSAMLEngineException
     */
    public static boolean isEncryptedSamlResponse(byte[] tokenSaml)
            throws UnmarshallException {
        final XMLObject samlObject = OpenSamlHelper.unmarshall(tokenSaml);
        if (samlObject instanceof Response) {
            final Response response = (Response) samlObject;
            return response.getEncryptedAssertions() != null
                    && !response.getEncryptedAssertions().isEmpty();
        }
        return false;

    }

    /**
     * @param samlMsg
     *            the saml response as a string
     * @return a string representing the Assertion
     */
    public static String extractAssertionAsString(String samlMsg) {
        String assertion = NO_ASSERTION;
        try {
            final Document doc = DocumentBuilderFactoryUtil.parse(samlMsg);

            final XPath xPath = XPathFactory.newInstance().newXPath();
            final Node node = (Node) xPath.evaluate(ASSERTION_XPATH, doc,
                    XPathConstants.NODE);
            if (node != null) {
                assertion = DocumentBuilderFactoryUtil.toString(node);
            }
        } catch (final ParserConfigurationException pce) {
            LOG.error("cannot parse response {}", pce);
        } catch (final SAXException saxe) {
            LOG.error("cannot parse response {}", saxe);
        } catch (final IOException ioe) {
            LOG.error("cannot parse response {}", ioe);
        } catch (final XPathExpressionException xpathe) {
            LOG.error("cannot find the assertion {}", xpathe);
        } catch (final TransformerException trfe) {
            LOG.error("cannot output the assertion {}", trfe);
        }

        return assertion;
    }

    public static String extractAssertion(String samlMsg,
            String xpathAssertion) {
        String assertion = null;
        try {
            final Document doc = DocumentBuilderFactoryUtil.parse(samlMsg);

            final XPath xPath = XPathFactory.newInstance().newXPath();
            final Node node = (Node) xPath.evaluate(xpathAssertion, doc,
                    XPathConstants.NODE);
            if (node != null) {
                assertion = node.getNodeValue();
            }
        } catch (final ParserConfigurationException pce) {
            LOG.error("cannot parse response {}", pce);
        } catch (final SAXException saxe) {
            LOG.error("cannot parse response {}", saxe);
        } catch (final IOException ioe) {
            LOG.error("cannot parse response {}", ioe);
        } catch (final XPathExpressionException xpathe) {
            LOG.error("cannot find the assertion {}", xpathe);
        }

        return assertion;
    }
}
