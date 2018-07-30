package es.caib.loginib.core.api.exception.login;

import es.caib.loginib.core.api.exception.ServiceRollbackException;

/**
 * 
 * Excepcion ticket no valido.
 * 
 * @author Indra
 * 
 */
@SuppressWarnings("serial")
public final class TicketNoValidoException extends ServiceRollbackException {
	
	@Override
	public final String getCodigoError() {	
		return "0103";
	}
	
	
  /**
   * Constructor TicketNoValidoException.
   * 
   * @param cause
   *          Causa
   */
  public TicketNoValidoException(final Throwable cause) {
    super("Ticket no valido: " + cause.getMessage(), cause);
  }

  /**
   * Constructor TicketNoValidoException.
   * 
   * @param mensaje
   *          mensaje
   */
  public TicketNoValidoException(final String mensaje) {
    super("Ticket no valido: " + mensaje);
  }

}
