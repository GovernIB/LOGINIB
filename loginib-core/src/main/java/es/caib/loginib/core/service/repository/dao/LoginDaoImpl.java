package es.caib.loginib.core.service.repository.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import es.caib.loginib.core.api.exception.NoExisteSesionException;
import es.caib.loginib.core.api.exception.TicketNoValidoException;
import es.caib.loginib.core.api.model.comun.ConstantesNumero;
import es.caib.loginib.core.api.model.login.DatosAutenticacion;
import es.caib.loginib.core.api.model.login.DatosLogoutSesion;
import es.caib.loginib.core.api.model.login.DatosPersona;
import es.caib.loginib.core.api.model.login.DatosSesionData;
import es.caib.loginib.core.api.model.login.EvidenciasAutenticacion;
import es.caib.loginib.core.api.model.login.EvidenciasLista;
import es.caib.loginib.core.api.model.login.TicketClave;
import es.caib.loginib.core.api.model.login.types.TypeHash;
import es.caib.loginib.core.api.model.login.types.TypeIdp;
import es.caib.loginib.core.api.util.ClaveLoginUtil;
import es.caib.loginib.core.service.model.ConfiguracionProcesos;
import es.caib.loginib.core.service.repository.model.JSesionLogin;
import es.caib.loginib.core.service.repository.model.JSesionLogout;
import es.caib.loginib.core.service.util.GeneradorId;
import es.caib.loginib.core.service.util.JsonUtil;

/**
 * Interfaz de acceso a base de datos para los datos de login.
 *
 * @author Indra
 *
 */
@Repository("loginDao")
public final class LoginDaoImpl implements LoginDao {

	/** Log. */
	private final org.slf4j.Logger log = LoggerFactory.getLogger(LoginDaoImpl.class);

	/** EntityManager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String crearSesionLogin(final String entidad, final String pUrlCallback, final String pUrlCallbackError,
			final String idioma, final List<TypeIdp> idps, final Integer qaa, final boolean iniClaAuto, final boolean forceAuth,
			final String aplicacion, final boolean auditar) {
		String idTicket = null;

		// Crea sesion para aplicacion externa
		final JSesionLogin ticketExterna = new JSesionLogin();
		ticketExterna.setEntidad(entidad);
		ticketExterna.setFechaInicioSesion(new Date());
		ticketExterna.setUrlCallback(pUrlCallback);
		ticketExterna.setUrlCallbackError(pUrlCallbackError);
		ticketExterna.setIdioma(idioma);
		ticketExterna.setIdps(ClaveLoginUtil.convertToStringIdps(idps));
		ticketExterna.setSesion(GeneradorId.generarId());
		ticketExterna.setQaa(qaa);
		ticketExterna.setInicioClaveAutomatico(iniClaAuto);
		ticketExterna.setForceAuthentication(forceAuth);
		ticketExterna.setAplicacion(aplicacion);
		ticketExterna.setAuditar(auditar);

		entityManager.persist(ticketExterna);
		idTicket = ticketExterna.getSesion();

		return idTicket;
	}

	@Override
	public DatosSesionData obtenerDatosSesionLogin(final String idSesion) {
		// Recupera sesion
		final JSesionLogin ticket = getSesionLogin(idSesion, true);
		// Devuelve datos
		final DatosSesionData ds = new DatosSesionData();
		ds.setIdSesion(idSesion);
		ds.setEntidad(ticket.getEntidad());
		ds.setFechaInicioSesion(ticket.getFechaInicioSesion());
		ds.setIdioma(ticket.getIdioma());
		ds.setIdps(ClaveLoginUtil.convertToListIdps(ticket.getIdps()));
		ds.setFechaTicket(ticket.getFechaTicket());
		ds.setQaa(ticket.getQaa());
		ds.setQaaAutenticacion(ticket.getQaaAutenticacion());
		ds.setIniClaAuto(ticket.isInicioClaveAutomatico());
		ds.setForceAuth(ticket.isForceAuthentication());
		ds.setSamlIdPeticion(ticket.getSamlIdPeticion());
		ds.setUrlCallback(ticket.getUrlCallback());
		ds.setUrlCallbackError(ticket.getUrlCallbackError());
		ds.setAplicacion(ticket.getAplicacion());
		ds.setAuditar(ticket.isAuditar());
		return ds;
	}

	@Override
	public DatosLogoutSesion obtenerDatosSesionLogout(final String idSesion) {
		// Recupera sesion
		final JSesionLogout ticket = getSesionLogout(idSesion, true);

		// Devuelve datos
		final DatosLogoutSesion ds = new DatosLogoutSesion();
		ds.setEntidad(ticket.getEntidad());
		ds.setUrlCallback(ticket.getUrlCallback());
		ds.setSamlIdPeticion(ticket.getSamlIdPeticion());
		ds.setFechaTicket(ticket.getFechaTicket());
		ds.setAplicacion(ticket.getAplicacion());

		// Guardamos la fecha para no volver a usar el ticket
		if (ticket.getFechaTicket() == null) {
			ticket.setFechaTicket(new Date());
			entityManager.merge(ticket);
		}
		return ds;
	}

	@Override
	public TicketClave generateTicketSesionLogin(final String idSesion, final DatosAutenticacion datosAutenticacion,
			final EvidenciasAutenticacion evidencias) {
		// Recupera sesion
		final JSesionLogin sesionLogin = getSesionLogin(idSesion, true);

		// Verificamos que no se haya autenticado
		if (sesionLogin.getTicket() != null) {
			throw new TicketNoValidoException("Sesión ya ha sido autenticada");
		}

		// Genera ticket y almacena en sesion
		final String ticketId = GeneradorId.generarId();
		sesionLogin.setTicket(ticketId);
		sesionLogin.setFechaTicket(datosAutenticacion.getFechaTicket());
		sesionLogin.setIdp(datosAutenticacion.getMetodoAutenticacion().toString());
		sesionLogin.setQaaAutenticacion(datosAutenticacion.getQaa());

		if (datosAutenticacion.getAutenticado() != null) {
			sesionLogin.setNif(datosAutenticacion.getAutenticado().getNif());
			sesionLogin.setNombre(datosAutenticacion.getAutenticado().getNombre());
			sesionLogin.setApellidos(datosAutenticacion.getAutenticado().getApellidos());
			sesionLogin.setApellido1(datosAutenticacion.getAutenticado().getApellido1());
			sesionLogin.setApellido2(datosAutenticacion.getAutenticado().getApellido2());
		}

		if (datosAutenticacion.getRepresentante() != null) {
			sesionLogin.setRepresentanteNif(datosAutenticacion.getRepresentante().getNif());
			sesionLogin.setRepresentanteNombre(datosAutenticacion.getRepresentante().getNombre());
			sesionLogin.setRepresentanteApellidos(datosAutenticacion.getRepresentante().getApellidos());
			sesionLogin.setRepresentanteApellido1(datosAutenticacion.getRepresentante().getApellido1());
			sesionLogin.setRepresentanteApellido2(datosAutenticacion.getRepresentante().getApellido2());
		}

		if (evidencias != null) {
			sesionLogin.setEvidenciasJson(evidencias.getEvidenciasJSON());
			sesionLogin.setEvidenciasHash(evidencias.getEvidenciasHash());
			sesionLogin.setEvidenciasAlgoritmoHash(evidencias.getAlgoritmoHash().toString());
		}

		entityManager.merge(sesionLogin);

		final TicketClave respuesta = new TicketClave();
		respuesta.setTicket(ticketId);
		respuesta.setUrlCallback(sesionLogin.getUrlCallback());
		respuesta.setIdioma(sesionLogin.getIdioma());
		return respuesta;
	}

	@Override
	public EvidenciasAutenticacion obtenerEvidenciasSesionLogin(final String idSesion) {
		// Recupera sesion
		final JSesionLogin ticket = getSesionLogin(idSesion, false);

		// Devuelve evidencias
		EvidenciasAutenticacion evidencias = null;
		if (ticket.isAuditar()) {
			evidencias = new EvidenciasAutenticacion();
			evidencias.setEvidenciasJSON(ticket.getEvidenciasJson());
			evidencias.setEvidenciasHash(ticket.getEvidenciasHash());
			evidencias.setAlgoritmoHash(TypeHash.fromString(ticket.getEvidenciasAlgoritmoHash()));
			evidencias.setEvidenciasLista(
					(EvidenciasLista) JsonUtil.fromJson(ticket.getEvidenciasJson(), EvidenciasLista.class));
		}
		return evidencias;
	}

	/**
	 * Recupera sesion logout.
	 *
	 * @param idSesion
	 *                     id sesion
	 * @return sesion
	 */
	private JSesionLogout getSesionLogout(final String idSesion, final boolean activa) {
		JSesionLogout sesion = null;

		String whereActiva = "";
		if (activa) {
			whereActiva = " and checkPurga = false";
		}

		final Query query = entityManager
				.createQuery("Select p From JSesionLogout p Where p.sesion = :sesion " + whereActiva);
		query.setParameter("sesion", idSesion);

		final List<JSesionLogout> results = query.getResultList();
		if (!results.isEmpty()) {
			sesion = results.get(0);
		}

		if (sesion == null) {
			throw new NoExisteSesionException();
		}

		return sesion;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DatosAutenticacion consumirTicketSesionLogin(final String pTicket, final long timeoutTicket) {

		// Obtenemos ticket sesion activa
		final JSesionLogin t = getSesionLoginByTicket(pTicket, true);

		// Ticket caducado
		if (t.getFechaTicket() != null
				&& (new Date()).getTime() - t.getFechaTicket().getTime() > (timeoutTicket * ConstantesNumero.N1000)) {
			throw new TicketNoValidoException("Ticket caducado");
		}

		// Marcamos el check para purgar
		t.setCheckPurga(true);
		entityManager.merge(t);

		// Establecemos datos
		DatosPersona autenticado = null;
		DatosPersona representante = null;
		if (StringUtils.isNotBlank(t.getNif())) {
			autenticado = new DatosPersona(t.getNif(), t.getNombre(), t.getApellidos(), t.getApellido1(),
					t.getApellido2());
		}
		if (StringUtils.isNotBlank(t.getRepresentanteNif())) {
			representante = new DatosPersona(t.getRepresentanteNif(), t.getRepresentanteNombre(),
					t.getRepresentanteApellidos(), t.getRepresentanteApellido1(), t.getRepresentanteApellido2());
		}
		final DatosAutenticacion du = new DatosAutenticacion(t.getSesion(), t.getFechaTicket(), t.getQaaAutenticacion(),
				TypeIdp.fromString(t.getIdp()), autenticado, representante);

		return du;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void purgaTicketSesionLogin(final ConfiguracionProcesos conf) {

		// Recupera sesiones activa y marca para purgar los que hayan cumplido timeout
		final List<JSesionLogin> listaTickets = entityManager
				.createQuery("SELECT t FROM JSesionLogin t where t.checkPurga = false ").getResultList();

		final Date ahora = new Date();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		for (final JSesionLogin t : listaTickets) {
			if (t.getTicket() == null) {
				// Sesiones que han sobrepasado el timeout total para completar la sesión de
				// autenticación
				if (ahora.getTime() - (t.getFechaInicioSesion().getTime()
						+ (conf.getTimeoutSesionAutenticacion() * ConstantesNumero.N1000)) > 0) {
					log.debug("Marcamos para purgar sesion no finalizada " + t.getSesion() + " - Fecha inicio: "
							+ dateFormat.format(t.getFechaInicioSesion()) + " - Fecha actual: "
							+ dateFormat.format(ahora.getTime()));
					t.setCheckPurga(true);
					entityManager.merge(t);
				}
			} else {
				// Sesiones autenticadas que no han consumido el ticket en plazo
				if (ahora.getTime() - (t.getFechaTicket().getTime()
						+ (conf.getTimeoutTicketAutenticacion() * ConstantesNumero.N1000)) > 0) {
					log.debug("Marcamos para purgar sesion finalizada que no ha consumido ticket " + t.getSesion()
							+ " - Fecha ticket: " + dateFormat.format(t.getFechaTicket()) + " - Fecha actual: "
							+ dateFormat.format(ahora.getTime()));
					t.setCheckPurga(true);
					entityManager.merge(t);
				}
			}
		}

		// Borramos sesiones no finalizadas o finalizadas que no se auditan
		final Query queryDeleteNoAuditadas = entityManager
				.createQuery("DELETE FROM JSesionLogin where checkPurga = true and "
						+ "( (ticket is not null and auditar = false and fechaTicket < :fecha) or (ticket is null and fechaInicioSesion < :fecha))");
		final Calendar calendarNoAuditadas = Calendar.getInstance();
		calendarNoAuditadas.add(Calendar.DAY_OF_YEAR, -1 * conf.getTimeoutPurgaGeneral());
		queryDeleteNoAuditadas.setParameter("fecha", calendarNoAuditadas.getTime(), TemporalType.DATE);
		final int deletedNoAudidatas = queryDeleteNoAuditadas.executeUpdate();
		if (deletedNoAudidatas > 0) {
			log.debug("Borramos sesiones no finalizadas o finalizadas que no se auditan : " + deletedNoAudidatas);
		}

		// Borramos sesiones finalizadas que se auditan
		final Query queryDeleteAuditadas = entityManager.createQuery(
				"DELETE FROM JSesionLogin where checkPurga = true and ticket is not null and auditar = true and fechaTicket < :fecha");
		final Calendar calendarAuditadas = Calendar.getInstance();
		calendarAuditadas.add(Calendar.DAY_OF_YEAR, -1 * conf.getTimeoutPurgaAuditadasFinalizadas());
		queryDeleteAuditadas.setParameter("fecha", calendarAuditadas.getTime(), TemporalType.DATE);
		final int deletedAudidatas = queryDeleteAuditadas.executeUpdate();
		if (deletedAudidatas > 0) {
			log.debug("Borramos sesiones finalizadas que se auditan : " + deletedAudidatas);
		}

	}

	@Override
	public void establecerIdPeticionLogin(final String idSesion, final String samlId) {
		// Recupera sesion
		final JSesionLogin ticket = getSesionLogin(idSesion, true);
		ticket.setSamlIdPeticion(samlId);
		entityManager.merge(ticket);
	}

	@Override
	public void establecerIdPeticionLogout(final String idSesion, final String samlId) {
		// Recupera sesion
		final JSesionLogout ticket = getSesionLogout(idSesion, true);
		ticket.setSamlIdPeticion(samlId);
		entityManager.merge(ticket);
	}

	@Override
	public String crearSesionLogut(final String entidad, final String pUrlCallback, final String idioma,
			final String aplicacion) {
		String idTicket = null;

		// Crea sesion para aplicacion externa
		final JSesionLogout logoutExterna = new JSesionLogout();
		logoutExterna.setEntidad(entidad);
		logoutExterna.setFechaInicioSesion(new Date());
		logoutExterna.setUrlCallback(pUrlCallback);
		logoutExterna.setIdioma(idioma);
		logoutExterna.setSesion(GeneradorId.generarId());
		logoutExterna.setAplicacion(aplicacion);

		entityManager.persist(logoutExterna);
		idTicket = logoutExterna.getSesion();

		return idTicket;
	}

	@Override
	public void purgaTicketSesionLogout(final ConfiguracionProcesos conf) {

		// Recupera lista tickets aplicaciones externas
		@SuppressWarnings("unchecked")
		final List<JSesionLogout> listaTickets = entityManager
				.createQuery("SELECT t FROM JSesionLogout t where t.checkPurga = false ").getResultList();
		final Date ahora = new Date();
		for (final JSesionLogout t : listaTickets) {
			// Marca para purgar las que hayan sobrepasado el tiempo total de sesion de
			// autenticacion
			if (ahora.getTime() - (t.getFechaInicioSesion().getTime()
					+ (conf.getTimeoutSesionAutenticacion() * ConstantesNumero.N1000)) > 0) {
				t.setCheckPurga(true);
				entityManager.merge(t);
			}
		}

		// Borra las marcadas para purgar
		final Query queryDelete = entityManager.createQuery(
				"DELETE FROM JSesionLogout where checkPurga = true and (fechaInicioSesion < :fecha or fechaTicket < :fecha)");
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1 * conf.getTimeoutPurgaGeneral());
		queryDelete.setParameter("fecha", calendar.getTime(), TemporalType.DATE);
		queryDelete.executeUpdate();

	}

	/**
	 * Recupera sesion login activa.
	 *
	 * @param idSesion
	 *                     id sesion
	 * @return sesion
	 */
	private JSesionLogin getSesionLogin(final String idSesion, final boolean activa) {

		JSesionLogin sesion = null;

		String whereActiva = "";
		if (activa) {
			whereActiva = " and checkPurga = false and ticket is null";
		}

		final Query query = entityManager
				.createQuery("Select p From JSesionLogin p Where p.sesion = :sesion " + whereActiva);
		query.setParameter("sesion", idSesion);

		final List<JSesionLogin> results = query.getResultList();
		if (!results.isEmpty()) {
			sesion = results.get(0);
		}

		if (sesion == null) {
			throw new NoExisteSesionException();
		}

		return sesion;
	}

	/**
	 * Recupera sesion login activa.
	 *
	 * @param idSesion
	 *                     id sesion
	 * @return sesion
	 */
	private JSesionLogin getSesionLoginByTicket(final String ticket, final boolean activa) {

		JSesionLogin sesion = null;

		String whereActiva = "";
		if (activa) {
			whereActiva = " and checkPurga = false";
		}

		final Query query = entityManager
				.createQuery("Select p From JSesionLogin p Where p.ticket = :ticket " + whereActiva);
		query.setParameter("ticket", ticket);

		final List<JSesionLogin> results = query.getResultList();
		if (!results.isEmpty()) {
			sesion = results.get(0);
		}

		if (sesion == null) {
			throw new NoExisteSesionException();
		}

		return sesion;
	}

}
