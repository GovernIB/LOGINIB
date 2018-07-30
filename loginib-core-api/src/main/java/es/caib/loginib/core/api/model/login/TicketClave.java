package es.caib.loginib.core.api.model.login;

/**
 * Respuesta procesar clave.
 * 
 * @author Indra
 * 
 */
public final class TicketClave {

  /** Url callback. */
  private String urlCallback;

  /** Ticket acceso. */
  private String ticket;

  /** Idioma. */
  private String idioma;

  /**
   * Url callback.
   * 
   * @return the urlCallback
   */
  public String getUrlCallback() {
    return urlCallback;
  }

  /**
   * @param pUrlCallback
   *          the urlCallback to set
   */
  public void setUrlCallback(final String pUrlCallback) {
    urlCallback = pUrlCallback;
  }

  /**
   * @return the ticket
   */
  public String getTicket() {
    return ticket;
  }

  /**
   * @param pTicket
   *          the ticket to set
   */
  public void setTicket(final String pTicket) {
    ticket = pTicket;
  }

  /**
   * @return the idioma
   */
  public String getIdioma() {
    return idioma;
  }

  /**
   * @param pIdioma
   *          the idioma to set
   */
  public void setIdioma(final String pIdioma) {
    idioma = pIdioma;
  }

}
