package es.caib.loginib.core.service.repository.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import es.caib.loginib.core.api.exception.login.NoExisteSesionException;
import es.caib.loginib.core.api.model.comun.ConstantesNumero;
import es.caib.loginib.core.api.model.login.DatosLogoutSesion;
import es.caib.loginib.core.api.model.login.DatosSesion;
import es.caib.loginib.core.api.model.login.DatosUsuario;
import es.caib.loginib.core.api.model.login.TicketClave;
import es.caib.loginib.core.service.repository.model.LogoutSesionExterna;
import es.caib.loginib.core.service.repository.model.TicketSesionExterna;
import es.caib.loginib.core.service.util.GeneradorId;

/**
 * Interfaz de acceso a base de datos para los datos del ticket de acceso clave.
 *
 * @author Indra
 *
 */
@Repository("claveDao")
public final class ClaveDaoImpl implements ClaveDao {

	/** EntityManager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String crearSesion(final String pUrlCallback, final String idioma, final String idps, final Integer qaa,
			final boolean forceAuth) {
		String idTicket = null;

		// Crea sesion para aplicacion externa
		final TicketSesionExterna ticketExterna = new TicketSesionExterna();
		ticketExterna.setFechaInicioSesion(new Date());
		ticketExterna.setUrlCallback(pUrlCallback);
		ticketExterna.setIdioma(idioma);
		ticketExterna.setIdps(idps);
		ticketExterna.setSesion(GeneradorId.generarId());
		ticketExterna.setQaa(qaa);
		ticketExterna.setForceAuthentication(forceAuth);

		entityManager.persist(ticketExterna);
		idTicket = ticketExterna.getSesion();

		return idTicket;
	}

	@Override
	public DatosSesion obtenerDatosSesion(final String idSesion) {
		final DatosSesion ds = new DatosSesion();

		// Recupera sesion
		final TicketSesionExterna ticket = getTicketBySesion(idSesion);

		if (ticket == null) {
			throw new NoExisteSesionException();
		}
		ds.setFechaInicioSesion(ticket.getFechaInicioSesion());
		ds.setIdioma(ticket.getIdioma());
		ds.setIdps(ticket.getIdps());
		ds.setFechaTicket(ticket.getFechaTicket());
		ds.setQaa(ticket.getQaa());
		ds.setForceAuth(ticket.isForceAuthentication());
		ds.setSamlIdPeticion(ticket.getSamlIdPeticion());

		return ds;
	}

	@Override
	public DatosLogoutSesion obtenerDatosSesionLogout(final String idSesion) {
		final DatosLogoutSesion ds = new DatosLogoutSesion();

		// Recupera sesion
		final LogoutSesionExterna ticket = getTicketBySesionLogout(idSesion);

		if (ticket == null) {
			throw new NoExisteSesionException();
		}
		ds.setUrlCallback(ticket.getUrlCallback());
		ds.setSamlIdPeticion(ticket.getSamlIdPeticion());
		ds.setFechaTicket(ticket.getFechaTicket());

		// Guardamos la fecha para no volver a usar el ticket
		if (ticket.getFechaTicket() == null) {
			ticket.setFechaTicket(new Date());
			entityManager.merge(ticket);
		}
		return ds;
	}

	@Override
	public TicketClave generateTicket(final String idSesion, final String idp, final String pNif, final String pNombre,
			final String pApellidos, final String pApellido1, final String pApellido2) {
		TicketClave ticket;
		ticket = generaTicketExterna(idSesion, idp, pNif, pNombre, pApellidos, pApellido1, pApellido2);
		return ticket;
	}

	/**
	 * Genera ticket para aplicacion externa.
	 *
	 * @param idSesion
	 *            id sesion
	 * @param pIdp
	 *            nivel autenticacion (C: certificado / U: usuario)
	 * @param pNif
	 *            Nif
	 * @param pNombre
	 *            Nombre
	 * @param pApellidos
	 *            Apelllidos
	 * @param pApellido2
	 *            Apellido 2
	 * @param pApellido1
	 *            Apellido 1
	 * @return Ticket
	 */
	private TicketClave generaTicketExterna(final String idSesion, final String pIdp, final String pNif,
			final String pNombre, final String pApellidos, final String pApellido1, final String pApellido2) {

		// Recupera sesion
		final TicketSesionExterna ticket = getTicketBySesion(idSesion);

		if (ticket == null) {
			throw new NoExisteSesionException();
		}

		// Genera ticket y almacena en sesion
		final String ticketId = GeneradorId.generarId();
		ticket.setTicket(ticketId);
		ticket.setFechaTicket(new Date());
		ticket.setNif(pNif);
		ticket.setNombre(pNombre);
		ticket.setApellidos(pApellidos);
		ticket.setApellido1(pApellido1);
		ticket.setApellido2(pApellido2);
		ticket.setIdp(pIdp);

		entityManager.merge(ticket);

		final TicketClave respuesta = new TicketClave();
		respuesta.setTicket(ticketId);
		respuesta.setUrlCallback(ticket.getUrlCallback());
		respuesta.setIdioma(ticket.getIdioma());
		return respuesta;
	}

	/**
	 * @param idSesion
	 * @return
	 */
	private TicketSesionExterna getTicketBySesion(final String idSesion) {
		final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<TicketSesionExterna> cQuery = cBuilder.createQuery(TicketSesionExterna.class);
		final Root<TicketSesionExterna> from = cQuery.from(TicketSesionExterna.class);
		cQuery.where(cBuilder.equal(from.get("sesion"), idSesion));
		final TypedQuery<TicketSesionExterna> query = entityManager.createQuery(cQuery);

		return query.getSingleResult();
	}

	/**
	 * @param idSesion
	 * @return
	 */
	private LogoutSesionExterna getTicketBySesionLogout(final String idSesion) {
		final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<LogoutSesionExterna> cQuery = cBuilder.createQuery(LogoutSesionExterna.class);
		final Root<LogoutSesionExterna> from = cQuery.from(LogoutSesionExterna.class);
		cQuery.where(cBuilder.equal(from.get("sesion"), idSesion));
		final TypedQuery<LogoutSesionExterna> query = entityManager.createQuery(cQuery);

		return query.getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public DatosUsuario consumirTicketSesionExterna(final String pTicket) {

		TicketSesionExterna t = null;

		final Query query = entityManager.createQuery("Select p From TicketSesionExterna p Where p.ticket = :ticket");
		query.setParameter("ticket", pTicket);

		final List<TicketSesionExterna> results = query.getResultList();

		DatosUsuario du = null;

		if (results != null && results.size() > 0) {
			// Obtenemos ticket
			t = results.get(0);
			// Lo borramos para que no se pueda volver a usar
			entityManager.remove(t);
			// Establecemos datos
			du = new DatosUsuario();
			du.setFechaTicket(t.getFechaTicket());
			du.setNivelAutenticacion(t.getIdp());
			du.setNif(t.getNif());
			du.setNombre(t.getNombre());
			du.setApellidos(t.getApellidos());
			du.setApellido1(t.getApellido1());
			du.setApellido2(t.getApellido2());
		}

		return du;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void purgaTicketSesionExterna(final long pTimeoutSesion, final long pTimeoutTicket) {
		// Recupera lista tickets aplicaciones externas
		final List<TicketSesionExterna> listaTickets = entityManager.createQuery("SELECT t FROM TicketSesionExterna t")
				.getResultList();
		final Date ahora = new Date();
		for (final TicketSesionExterna t : listaTickets) {
			if (t.getTicket() == null) {
				if (ahora.getTime()
						- (t.getFechaInicioSesion().getTime() + (pTimeoutSesion * ConstantesNumero.N1000)) > 0) {
					entityManager.remove(t);
				}
			} else {
				if (ahora.getTime() - (t.getFechaTicket().getTime() + (pTimeoutTicket * ConstantesNumero.N1000)) > 0) {
					entityManager.remove(t);
				}
			}
		}
	}

	@Override
	public void establecerSamlIdPeticion(final String idSesion, final String samlId) {
		// Recupera sesion
		final TicketSesionExterna ticket = getTicketBySesion(idSesion);

		if (ticket == null) {
			throw new NoExisteSesionException();
		}

		ticket.setSamlIdPeticion(samlId);

		entityManager.merge(ticket);

	}

	@Override
	public void establecerSamlIdLogoutPeticion(final String idSesion, final String samlId) {
		// Recupera sesion
		final LogoutSesionExterna ticket = getTicketBySesionLogout(idSesion);

		if (ticket == null) {
			throw new NoExisteSesionException();
		}

		ticket.setSamlIdPeticion(samlId);

		entityManager.merge(ticket);

	}

	@Override
	public String crearLogutSesion(final String pUrlCallback, final String idioma) {
		String idTicket = null;

		// Crea sesion para aplicacion externa
		final LogoutSesionExterna logoutExterna = new LogoutSesionExterna();
		logoutExterna.setFechaInicioSesion(new Date());
		logoutExterna.setUrlCallback(pUrlCallback);
		logoutExterna.setIdioma(idioma);
		logoutExterna.setSesion(GeneradorId.generarId());

		entityManager.persist(logoutExterna);
		idTicket = logoutExterna.getSesion();

		return idTicket;
	}

	@Override
	public void purgaTicketLogoutSesionExterna(final Long pTimeoutSesion, final Long pTimeoutTicket) {

		// Recupera lista tickets aplicaciones externas
		@SuppressWarnings("unchecked")
		final List<LogoutSesionExterna> listaTickets = entityManager.createQuery("SELECT t FROM LogoutSesionExterna t")
				.getResultList();
		final Date ahora = new Date();
		for (final LogoutSesionExterna t : listaTickets) {
			if (ahora.getTime()
					- (t.getFechaInicioSesion().getTime() + (pTimeoutSesion * ConstantesNumero.N1000)) > 0) {
				entityManager.remove(t);
			}

		}

	}

}
