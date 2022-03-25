package es.caib.loginib.core.service.repository.dao;

import java.util.Date;
import java.util.List;

import es.caib.loginib.core.api.model.login.DesgloseApellidos;

/**
 * La interface DominioDao.
 */
public interface DesgloseApellidosDao {

	/**
	 * Obtiene el valor de byId.
	 *
	 * @param idDominio the id dominio
	 * @return el valor de byId
	 */
	DesgloseApellidos getByNif(final String nif);

	/**
	 * Obtiene el valor de all.
	 *
	 * @param ambito the ambito
	 * @param id     the id
	 * @return el valor de all
	 */
	List<DesgloseApellidos> getAll();

	/**
	 * Obtiene el valor de allByFiltro.
	 *
	 * @param ambito the ambito
	 * @param id     the id
	 * @param filtro the filtro
	 * @return el valor de allByFiltro
	 */
	List<DesgloseApellidos> getAllByFiltro(String filtro, Date fechaD, Date fechaH);

	/**
	 * Añade.
	 *
	 * @param entidad   the entidad
	 * @param idEntidad the id entidad
	 * @param idArea    the id area
	 */
	String add(final DesgloseApellidos da);

	/**
	 * Elimina.
	 *
	 * @param idDominio the id dominio
	 */
	boolean remove(final String nif);

	/**
	 * Actualiza dominio.
	 *
	 * @param dominio the dominio
	 */
	void update(DesgloseApellidos da);

	void clonar(String nif, final String nuevoIdentificador);

}