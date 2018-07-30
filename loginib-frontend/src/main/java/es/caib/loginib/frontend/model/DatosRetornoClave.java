package es.caib.loginib.frontend.model;

/**
 * Datos para retornar de clave.
 * 
 * @author Indra
 */
public final class DatosRetornoClave {

  /** Url callback login. */
  private String urlCallbackLogin;

  /** Ticket. */
  private String ticket;

  /** Idioma. */
  private String idioma;

  /**
   * Obtiene url callback login.
   *
   * @return the urlCallbackLogin
   */
  public String getUrlCallbackLogin() {
    return urlCallbackLogin;
  }

  /**
   * Establece url callback login.
   *
   * @param urlCallbackLogin
   *          the urlCallbackLogin to set
   */
  public void setUrlCallbackLogin(final String urlCallbackLogin) {
    this.urlCallbackLogin = urlCallbackLogin;
  }

  /**
   * Obtiene ticket.
   * 
   * @return the ticket
   */
  public String getTicket() {
    return ticket;
  }

  /**
   * Establece ticket.
   *
   * @param ticket
   *          the ticket to set
   */
  public void setTicket(final String ticket) {
    this.ticket = ticket;
  }

  /**
   * Obtiene idioma.
   *
   * @return the idioma
   */
  public String getIdioma() {
    return idioma;
  }

  /**
   * Establece idioma.
   *
   * @param idioma
   *          the idioma to set
   */
  public void setIdioma(final String idioma) {
    this.idioma = idioma;
  }

}
