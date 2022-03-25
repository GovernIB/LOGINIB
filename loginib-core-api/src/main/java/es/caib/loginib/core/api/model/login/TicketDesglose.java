package es.caib.loginib.core.api.model.login;

/**
 * Respuesta procesar desglose.
 *
 * @author Indra
 *
 */
public final class TicketDesglose {

  /** Url callback. */
  private String urlCallback;

  /** Ticket acceso. */
  private String ticket;

  /** Idioma. */
  private String idioma;

  /** Idioma. */
  private String nif;

  /** Personalizacion entidad **/
  private PersonalizacionEntidad personalizacion;

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


	/**
	 * @return the nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * @param nif the nif to set
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 * @return the personalizacion
	 */
	public PersonalizacionEntidad getPersonalizacion() {
		return personalizacion;
	}

	/**
	 * @param personalizacion the personalizacion to set
	 */
	public void setPersonalizacion(PersonalizacionEntidad personalizacion) {
		this.personalizacion = personalizacion;
	}

	/**
	 * Comprueba si la url es de desglosar
	 * @return
	 */
	public boolean isForzarDesglose() {
		return  this.getUrlCallback()!= null && "retornoClaveSimulado/forzarDesgloseRetorno.html".equals(this.getUrlCallback());
  }

}
