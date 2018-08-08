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

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.loginib.core.api.exception.NoExisteSesionException;
import es.caib.loginib.core.api.model.comun.ConstantesNumero;
import es.caib.loginib.core.api.model.login.DatosLogoutSesion;
import es.caib.loginib.core.api.model.login.DatosRepresentante;
import es.caib.loginib.core.api.model.login.DatosSesion;
import es.caib.loginib.core.api.model.login.DatosUsuario;
import es.caib.loginib.core.api.model.login.TicketClave;
import es.caib.loginib.core.api.model.login.types.TypeIdp;
import es.caib.loginib.core.api.util.ClaveLoginUtil;
import es.caib.loginib.core.service.repository.model.JSesionLogin;
import es.caib.loginib.core.service.repository.model.JSesionLogout;
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
    public String crearSesionLogin(final String entidad,
            final String pUrlCallback, final String idioma,
            final List<TypeIdp> idps, final Integer qaa,
            final boolean forceAuth) {
        String idTicket = null;

        // Crea sesion para aplicacion externa
        final JSesionLogin ticketExterna = new JSesionLogin();
        ticketExterna.setEntidad(entidad);
        ticketExterna.setFechaInicioSesion(new Date());
        ticketExterna.setUrlCallback(pUrlCallback);
        ticketExterna.setIdioma(idioma);
        ticketExterna.setIdps(ClaveLoginUtil.convertToStringIdps(idps));
        ticketExterna.setSesion(GeneradorId.generarId());
        ticketExterna.setQaa(qaa);
        ticketExterna.setForceAuthentication(forceAuth);

        entityManager.persist(ticketExterna);
        idTicket = ticketExterna.getSesion();

        return idTicket;
    }

    @Override
    public DatosSesion obtenerDatosSesionLogin(final String idSesion) {
        // Recupera sesion
        final JSesionLogin ticket = getSesionLogin(idSesion);
        if (ticket == null) {
            throw new NoExisteSesionException();
        }
        // Devuelve datos
        final DatosSesion ds = new DatosSesion();
        ds.setIdSesion(idSesion);
        ds.setEntidad(ticket.getEntidad());
        ds.setFechaInicioSesion(ticket.getFechaInicioSesion());
        ds.setIdioma(ticket.getIdioma());
        ds.setIdps(ClaveLoginUtil.convertToListIdps(ticket.getIdps()));
        ds.setFechaTicket(ticket.getFechaTicket());
        ds.setQaa(ticket.getQaa());
        ds.setForceAuth(ticket.isForceAuthentication());
        ds.setSamlIdPeticion(ticket.getSamlIdPeticion());

        return ds;
    }

    @Override
    public DatosLogoutSesion obtenerDatosSesionLogout(final String idSesion) {
        // Recupera sesion
        final JSesionLogout ticket = getSesionLogout(idSesion);
        if (ticket == null) {
            throw new NoExisteSesionException();
        }
        // Devuelve datos
        final DatosLogoutSesion ds = new DatosLogoutSesion();
        ds.setEntidad(ticket.getEntidad());
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
    public TicketClave generateTicketSesionLogin(final String idSesion,
            final TypeIdp idp, final String pNif, final String pNombre,
            final String pApellidos, final String pApellido1,
            final String pApellido2, DatosRepresentante representante) {
        TicketClave ticket;
        ticket = generaTicketExterna(idSesion, idp, pNif, pNombre, pApellidos,
                pApellido1, pApellido2, representante);
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
     * @param representante
     *            representante
     * @return Ticket
     */
    private TicketClave generaTicketExterna(final String idSesion,
            final TypeIdp pIdp, final String pNif, final String pNombre,
            final String pApellidos, final String pApellido1,
            final String pApellido2, DatosRepresentante representante) {

        // Recupera sesion
        final JSesionLogin ticket = getSesionLogin(idSesion);

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
        ticket.setIdp(pIdp.toString());

        if (representante != null) {
            ticket.setRepresentanteNif(representante.getNif());
            ticket.setRepresentanteNombre(representante.getNombre());
            ticket.setRepresentanteApellidos(representante.getApellidos());
            ticket.setRepresentanteApellido1(representante.getApellido1());
            ticket.setRepresentanteApellido2(representante.getApellido2());
        }

        entityManager.merge(ticket);

        final TicketClave respuesta = new TicketClave();
        respuesta.setTicket(ticketId);
        respuesta.setUrlCallback(ticket.getUrlCallback());
        respuesta.setIdioma(ticket.getIdioma());
        return respuesta;
    }

    /**
     * Recupera sesion login.
     *
     * @param idSesion
     *            id sesion
     * @return sesion
     */
    private JSesionLogin getSesionLogin(final String idSesion) {
        final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<JSesionLogin> cQuery = cBuilder
                .createQuery(JSesionLogin.class);
        final Root<JSesionLogin> from = cQuery.from(JSesionLogin.class);
        cQuery.where(cBuilder.equal(from.get("sesion"), idSesion));
        final TypedQuery<JSesionLogin> query = entityManager
                .createQuery(cQuery);

        JSesionLogin sesion = null;
        final List<JSesionLogin> res = query.getResultList();
        if (!res.isEmpty()) {
            sesion = res.get(0);
        }

        return sesion;
    }

    /**
     * Recupera sesion logout.
     *
     * @param idSesion
     *            id sesion
     * @return sesion
     */
    private JSesionLogout getSesionLogout(final String idSesion) {
        final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<JSesionLogout> cQuery = cBuilder
                .createQuery(JSesionLogout.class);
        final Root<JSesionLogout> from = cQuery.from(JSesionLogout.class);
        cQuery.where(cBuilder.equal(from.get("sesion"), idSesion));
        final TypedQuery<JSesionLogout> query = entityManager
                .createQuery(cQuery);

        JSesionLogout sesion = null;
        final List<JSesionLogout> res = query.getResultList();
        if (!res.isEmpty()) {
            sesion = res.get(0);
        }

        return sesion;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DatosUsuario consumirTicketSesionLogin(final String pTicket) {

        JSesionLogin t = null;

        final Query query = entityManager.createQuery(
                "Select p From JSesionLogin p Where p.ticket = :ticket");
        query.setParameter("ticket", pTicket);

        final List<JSesionLogin> results = query.getResultList();

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
            if (StringUtils.isNotBlank(t.getRepresentanteNif())) {
                final DatosRepresentante dr = new DatosRepresentante();
                dr.setNif(t.getRepresentanteNif());
                dr.setNombre(t.getRepresentanteNombre());
                dr.setApellidos(t.getRepresentanteApellidos());
                dr.setApellido1(t.getRepresentanteApellido1());
                dr.setApellido2(t.getRepresentanteApellido2());
                du.setRepresentante(dr);
            }
        }

        return du;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void purgaTicketSesionLogin(final long pTimeoutSesion,
            final long pTimeoutTicket) {
        // Recupera lista tickets aplicaciones externas
        final List<JSesionLogin> listaTickets = entityManager
                .createQuery("SELECT t FROM JSesionLogin t").getResultList();
        final Date ahora = new Date();
        for (final JSesionLogin t : listaTickets) {
            if (t.getTicket() == null) {
                if (ahora.getTime() - (t.getFechaInicioSesion().getTime()
                        + (pTimeoutSesion * ConstantesNumero.N1000)) > 0) {
                    entityManager.remove(t);
                }
            } else {
                if (ahora.getTime() - (t.getFechaTicket().getTime()
                        + (pTimeoutTicket * ConstantesNumero.N1000)) > 0) {
                    entityManager.remove(t);
                }
            }
        }
    }

    @Override
    public void establecerSamlIdSesionLogin(final String idSesion,
            final String samlId) {
        // Recupera sesion
        final JSesionLogin ticket = getSesionLogin(idSesion);

        if (ticket == null) {
            throw new NoExisteSesionException();
        }

        ticket.setSamlIdPeticion(samlId);

        entityManager.merge(ticket);

    }

    @Override
    public void establecerSamlIdSesionLogout(final String idSesion,
            final String samlId) {
        // Recupera sesion
        final JSesionLogout ticket = getSesionLogout(idSesion);

        if (ticket == null) {
            throw new NoExisteSesionException();
        }

        ticket.setSamlIdPeticion(samlId);

        entityManager.merge(ticket);

    }

    @Override
    public String crearSesionLogut(final String entidad,
            final String pUrlCallback, final String idioma) {
        String idTicket = null;

        // Crea sesion para aplicacion externa
        final JSesionLogout logoutExterna = new JSesionLogout();
        logoutExterna.setEntidad(entidad);
        logoutExterna.setFechaInicioSesion(new Date());
        logoutExterna.setUrlCallback(pUrlCallback);
        logoutExterna.setIdioma(idioma);
        logoutExterna.setSesion(GeneradorId.generarId());

        entityManager.persist(logoutExterna);
        idTicket = logoutExterna.getSesion();

        return idTicket;
    }

    @Override
    public void purgaTicketSesionLogout(final Long pTimeoutSesion,
            final Long pTimeoutTicket) {

        // Recupera lista tickets aplicaciones externas
        @SuppressWarnings("unchecked")
        final List<JSesionLogout> listaTickets = entityManager
                .createQuery("SELECT t FROM JSesionLogout t").getResultList();
        final Date ahora = new Date();
        for (final JSesionLogout t : listaTickets) {
            if (ahora.getTime() - (t.getFechaInicioSesion().getTime()
                    + (pTimeoutSesion * ConstantesNumero.N1000)) > 0) {
                entityManager.remove(t);
            }

        }

    }

}
