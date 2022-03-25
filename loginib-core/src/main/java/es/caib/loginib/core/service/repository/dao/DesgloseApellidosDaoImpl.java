package es.caib.loginib.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.loginib.core.api.model.login.DesgloseApellidos;
import es.caib.loginib.core.service.repository.model.JDesgloseApellidos;

/**
 * La clase DominioDaoImpl.
 */
@Repository("desgloseApellidosDao")
public class DesgloseApellidosDaoImpl implements DesgloseApellidosDao {

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de DesgloseApellidosDao.
	 */
	public DesgloseApellidosDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#getById(java.lang.
	 * Long)
	 */
	@Override
	public DesgloseApellidos getByNif(final String nif) {
		DesgloseApellidos da = null;
		final JDesgloseApellidos jda = entityManager.find(JDesgloseApellidos.class, nif);
		if (jda != null) {
			// Establecemos datos
			da = jda.toModel();
		}
		return da;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.DominioDao#add(es.caib.
	 * sistrages.core.api.model.Dominio, java.lang.Long, java.lang.Long)
	 */
	@Override
	public String add(final DesgloseApellidos da) {
		// Añade dominio por superadministrador estableciendo datos minimos
		final JDesgloseApellidos jda = new JDesgloseApellidos();
		jda.fromModel(da);

		entityManager.persist(jda);
		return jda.getNif();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#remove(java.lang.
	 * Long)
	 */
	@Override
	public boolean remove(final String nif) {
		boolean retorno;

		final JDesgloseApellidos jda = entityManager.find(JDesgloseApellidos.class, nif);
		if (jda == null) {
			// No existe
			retorno = false;
		} else {
			// Si no tiene relaciones, se borra correctamente
			entityManager.remove(jda);
			retorno = true;
		}

		return retorno;
	}

	@Override
	public List<DesgloseApellidos> getAllByFiltro(final String filtro, final Date fechaD, final Date fechaH) {
		return listarDesglosesApellidos(filtro, fechaD, fechaH);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.DominioDao#getAll(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long)
	 */
	@Override
	public List<DesgloseApellidos> getAll() {
		return listarDesglosesApellidos(null, null, null);
	}

	/**
	 * Listar dominios.
	 *
	 * @param ambito ambito
	 * @param id     id
	 * @param filtro filtro
	 * @return lista de dominios
	 */
	private List<DesgloseApellidos> listarDesglosesApellidos(final String filtro, final Date fechaD,
			final Date fechaH) {
		final List<DesgloseApellidos> daLista = new ArrayList<>();

		final List<JDesgloseApellidos> results = listarJDesgloseApellidos(filtro, fechaD, fechaH);

		if (results != null && !results.isEmpty()) {
			for (final JDesgloseApellidos jda : results) {
				final DesgloseApellidos da = jda.toModel();
				daLista.add(da);
			}
		}

		return daLista;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#updateDominio(es.
	 * caib.sistrages.core.api.model.Dominio)
	 */
	@Override
	public void update(final DesgloseApellidos da) {
		final JDesgloseApellidos jda = entityManager.find(JDesgloseApellidos.class, da.getNif());
		jda.fromModel(da);
		entityManager.merge(jda);
	}

	/**
	 * Lista dominios.
	 *
	 * @param ambito Ambito
	 * @param id     id
	 * @param filtro filtro
	 * @return dominios
	 */
	@SuppressWarnings("unchecked")
	private List<JDesgloseApellidos> listarJDesgloseApellidos(final String filtro) {
		String sql = "SELECT DISTINCT d FROM JDesgloseApellidos d ";

		if (StringUtils.isNotBlank(filtro)) {
			sql += "WHERE (LOWER(d.nif) LIKE :filtro OR LOWER(d.nombreCompleto) LIKE :filtro)";
		}

		sql += " ORDER BY d.fechaCreacion";

		final Query query = entityManager.createQuery(sql);

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	private List<JDesgloseApellidos> listarJDesgloseApellidos(final String filtro, final Date fechaD,
			final Date fechaH) {
		String sql = "SELECT DISTINCT d FROM JDesgloseApellidos d WHERE d = d";

		if (StringUtils.isNotBlank(filtro)) {
			sql += " AND (LOWER(d.nif) LIKE :filtro OR LOWER(d.nombreCompleto) LIKE :filtro)";
		}
		if (fechaD != null) {
			sql += " AND fechaCreacion >= :fechaD";
		}
		if (fechaH != null) {
			sql += " AND fechaCreacion <= :fechaH";
		}
		sql += " ORDER BY d.fechaCreacion";

		final Query query = entityManager.createQuery(sql);

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		if (fechaD != null) {
			query.setParameter("fechaD", fechaD);
		}

		if (fechaH != null) {
			query.setParameter("fechaH", fechaH);
		}

		return query.getResultList();
	}

	/**
	 * Limpiamos los campos id del dominio y, en caso de pasarse area y fd, se
	 * asocian.
	 */
	@Override
	public void clonar(final String nif, final String nuevoIdentificador) {

		final JDesgloseApellidos jda = JDesgloseApellidos
				.clonar(entityManager.find(JDesgloseApellidos.class, Long.valueOf(nif)));
		entityManager.persist(jda);
	}

}
