package es.caib.loginib.core.service.model;

/**
 * Configuración procesos.
 *
 * @author Indra
 */
public class ConfiguracionProcesos {

	/**
	 * Timeout de todo el proceso de autenticacion de login o logout (segs). Se da
	 * un tiempo máximo a finalizar todo el proceso de autenticación (si sobrepasa
	 * ese tiempo no se podrá completar sesión autenticación).
	 */
	private int timeoutSesionAutenticacion;
	/**
	 * Timeout ticket proceso autenticacion (segs). Tiempo para consumir el ticket
	 * para obtener datos de autenticación (una vez finalizada la autenticación).
	 */
	private int timeoutTicketAutenticacion;
	/** Timeout purga definitiva (dias) para sesiones autenticacion no auditadas. */
	private int timeoutPurgaGeneral;
	/**
	 * Timeout purga definitiva (dias) para sesiones autenticacion finalizadas
	 * auditadas.
	 */
	private int timeoutPurgaAuditadasFinalizadas;

	/**
	 * Método de acceso a timeoutProcesoAutenticacion.
	 *
	 * @return timeoutProcesoAutenticacion
	 */
	public int getTimeoutSesionAutenticacion() {
		return timeoutSesionAutenticacion;
	}

	/**
	 * Método para establecer timeoutProcesoAutenticacion.
	 *
	 * @param timeoutProcesoAutenticacion
	 *                                        timeoutProcesoAutenticacion a
	 *                                        establecer
	 */
	public void setTimeoutSesionAutenticacion(final int timeoutProcesoAutenticacion) {
		this.timeoutSesionAutenticacion = timeoutProcesoAutenticacion;
	}

	/**
	 * Método de acceso a timeoutTicketAutenticacion.
	 *
	 * @return timeoutTicketAutenticacion
	 */
	public int getTimeoutTicketAutenticacion() {
		return timeoutTicketAutenticacion;
	}

	/**
	 * Método para establecer timeoutTicketAutenticacion.
	 *
	 * @param timeoutTicketAutenticacion
	 *                                       timeoutTicketAutenticacion a establecer
	 */
	public void setTimeoutTicketAutenticacion(final int timeoutTicketAutenticacion) {
		this.timeoutTicketAutenticacion = timeoutTicketAutenticacion;
	}

	/**
	 * Método de acceso a timeoutPurgaNoAuditadas.
	 *
	 * @return timeoutPurgaNoAuditadas
	 */
	public int getTimeoutPurgaGeneral() {
		return timeoutPurgaGeneral;
	}

	/**
	 * Método para establecer timeoutPurgaNoAuditadas.
	 *
	 * @param timeoutPurgaNoAuditadas
	 *                                    timeoutPurgaNoAuditadas a establecer
	 */
	public void setTimeoutPurgaGeneral(final int timeoutPurgaNoAuditadas) {
		this.timeoutPurgaGeneral = timeoutPurgaNoAuditadas;
	}

	/**
	 * Método de acceso a timeoutPurgaAuditadas.
	 *
	 * @return timeoutPurgaAuditadas
	 */
	public int getTimeoutPurgaAuditadasFinalizadas() {
		return timeoutPurgaAuditadasFinalizadas;
	}

	/**
	 * Método para establecer timeoutPurgaAuditadas.
	 *
	 * @param timeoutPurgaAuditadas
	 *                                  timeoutPurgaAuditadas a establecer
	 */
	public void setTimeoutPurgaAuditadasFinalizadas(final int timeoutPurgaAuditadas) {
		this.timeoutPurgaAuditadasFinalizadas = timeoutPurgaAuditadas;
	}

}
