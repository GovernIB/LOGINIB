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

  /** Personalizacion **/
  private PersonalizacionEntidad personalizacion;

  /** Indica si ya está desglosado. **/
  private boolean yaDesglosado;

  /** Indica la url del desglose. **/
  private String urlDesglose;

  /** Indica si es representante **/
  private boolean representante;

  /** NIF **/
  private String nif;

  private DatosPersona personaAutenticado;
  private DatosPersona personaRepresentante;

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
	 * @return the urlDesglose
	 */
	public String getUrlDesglose() {
		return urlDesglose;
	}

	/**
	 * @param urlDesglose the urlDesglose to set
	 */
	public void setUrlDesglose(String urlDesglose) {
		this.urlDesglose = urlDesglose;
	}

		/**
	 * @return the yaDesglosado
	 */
	public boolean isYaDesglosado() {
		return yaDesglosado;
	}

	/**
	 * @param yaDesglosado the yaDesglosado to set
	 */
	public void setYaDesglosado(boolean yaDesglosado) {
		this.yaDesglosado = yaDesglosado;
	}

	/**
	 * @return the representante
	 */
	public boolean isRepresentante() {
		return representante;
	}

	/**
	 * @param representante the representante to set
	 */
	public void setRepresentante(boolean representante) {
		this.representante = representante;
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
	 * @return the personaAutenticado
	 */
	public DatosPersona getPersonaAutenticado() {
		return personaAutenticado;
	}

	/**
	 * @param personaAutenticado the personaAutenticado to set
	 */
	public void setPersonaAutenticado(DatosPersona personaAutenticado) {
		this.personaAutenticado = personaAutenticado;
	}

	/**
	 * @return the personaRepresentante
	 */
	public DatosPersona getPersonaRepresentante() {
		return personaRepresentante;
	}

	/**
	 * @param personaRepresentante the personaRepresentante to set
	 */
	public void setPersonaRepresentante(DatosPersona personaRepresentante) {
		this.personaRepresentante = personaRepresentante;
	}

	/**
  	 * Comprueba si se entra por test.html
  	 * @return
  	 */
	public boolean isTest() {
		return this.getUrlCallback() != null && this.getUrlCallback().equals("desgloseCertificado.html");
	}

}
