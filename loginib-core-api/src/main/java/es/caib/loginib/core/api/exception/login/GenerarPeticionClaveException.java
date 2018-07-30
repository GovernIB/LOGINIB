package es.caib.loginib.core.api.exception.login;

import es.caib.loginib.core.api.exception.ServiceRollbackException;

/**
 * 
 * Excepcion al generar peticion para Clave.
 * 
 * @author Indra
 * 
 */
@SuppressWarnings("serial")
public final class GenerarPeticionClaveException extends ServiceRollbackException {

	@Override
	public final String getCodigoError() {	
		return "0101";
	}
	
	
  /**
   * Constructor GenerarPeticionClaveException.
   * 
   * @param cause
   *          Causa
   */
  public GenerarPeticionClaveException(final Throwable cause) {
    super("Error al generar peticion para Clave: " + cause.getMessage(), cause);
  }

  /**
   * Constructor GenerarPeticionClaveException.
   * 
   * @param cause
   *          Causa
   */
  public GenerarPeticionClaveException(final String cause) {
    super("Error al generar peticion para Clave: " + cause);
  }
  
  /**
   * Constructor GenerarPeticionClaveException.
   * 
   * @param cause
   *          Causa
   */
  public GenerarPeticionClaveException(final String cause, Throwable exc) {
    super("Error al generar peticion para Clave: " + cause, exc);
  }

}
