package es.caib.loginib.core.api.model.login;

/**
 * Peticion clave.
 * 
 * @author Indra
 * 
 */
public final class PeticionClave {

  /** SAML Request en B64. */
  private String samlRequestB64;
  /** Idps. */
  private String idps;
  /** Url clave. */
  private String urlClave;
  /** Idioma. */
  private String idioma;

  /**
   * Gets the saml request b64.
   * 
   * @return the saml request b64
   */
  public String getSamlRequestB64() {
    return samlRequestB64;
  }

  /**
   * Sets the saml request b64.
   * 
   * @param pSamlRequestB64
   *          the new saml request b64
   */
  public void setSamlRequestB64(final String pSamlRequestB64) {
    samlRequestB64 = pSamlRequestB64;
  }

  /**
   * Gets the idps.
   * 
   * @return the idps
   */
  public String getIdps() {
    return idps;
  }

  /**
   * Sets the idps.
   * 
   * @param pIdps
   *          the new idps
   */
  public void setIdps(final String pIdps) {
    idps = pIdps;
  }

  /**
   * Gets the idioma.
   * 
   * @return the idioma
   */
  public String getIdioma() {
    return idioma;
  }

  /**
   * Sets the idioma.
   * 
   * @param pIdioma
   *          the new idioma
   */
  public void setIdioma(final String pIdioma) {
    idioma = pIdioma;
  }

  /**
   * Gets the url clave.
   * 
   * @return the url clave
   */
  public String getUrlClave() {
    return urlClave;
  }

  /**
   * Sets the url clave.
   * 
   * @param pUrlClave
   *          the new url clave
   */
  public void setUrlClave(final String pUrlClave) {
    urlClave = pUrlClave;
  }

}
