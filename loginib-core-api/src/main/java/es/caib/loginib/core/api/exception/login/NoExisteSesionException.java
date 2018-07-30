package es.caib.loginib.core.api.exception.login;

import es.caib.loginib.core.api.exception.ServiceRollbackException;

/**
 * 
 * Excepcion cuando no existe sesion.
 * 
 * @author Indra
 * 
 */
@SuppressWarnings("serial")
public final class NoExisteSesionException extends ServiceRollbackException {

	@Override
	public final String getCodigoError() {	
		return "0102";
	}
	
	
  /**
   * Constructor NoExiseSesionException.
   * 
   */
  public NoExisteSesionException() {
    super("No existe sesion");
  }

}
