package es.caib.loginib.core.api.model.login;

/**
 * Validación usuario/password.
 *
 * @author Indra
 *
 */
public final class ValidacionUsuarioPassword {

	/** Validacion. */
	private boolean usuarioValido;

	/** Ticket. */
	private TicketClave ticket;

	/**
	 * Método de acceso a usuarioValido.
	 * 
	 * @return usuarioValido
	 */
	public boolean isUsuarioValido() {
		return usuarioValido;
	}

	/**
	 * Método para establecer usuarioValido.
	 * 
	 * @param usuarioValido
	 *                          usuarioValido a establecer
	 */
	public void setUsuarioValido(final boolean usuarioValido) {
		this.usuarioValido = usuarioValido;
	}

	/**
	 * Método de acceso a ticket.
	 * 
	 * @return ticket
	 */
	public TicketClave getTicket() {
		return ticket;
	}

	/**
	 * Método para establecer ticket.
	 * 
	 * @param ticket
	 *                   ticket a establecer
	 */
	public void setTicket(final TicketClave ticket) {
		this.ticket = ticket;
	}

}
