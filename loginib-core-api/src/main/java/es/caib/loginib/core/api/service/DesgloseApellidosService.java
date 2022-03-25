package es.caib.loginib.core.api.service;

import java.util.Date;
import java.util.List;

import es.caib.loginib.core.api.model.login.DesgloseApellidos;

/**
 * Dominio service.
 *
 * @author Indra.
 *
 */
public interface DesgloseApellidosService {

	/**
	 * Obtener dominio.
	 *
	 * @param codDominio codigo del dominio
	 * @return dominio
	 */
	public DesgloseApellidos loadDesglose(String nif);

	/**
	 * Añade dominio.
	 *
	 * @param dominio   Dominio a crear.
	 * @param idEntidad Id de la entidad
	 * @param idArea    Id del area.
	 */
	public String addDesglose(DesgloseApellidos da);

	/**
	 * Actualiza dominio.
	 *
	 * @param dominio Dominio con los datos requeridos por superadministrador.
	 */
	public void updateDesglose(DesgloseApellidos da);

	/**
	 * Elimina dominio.
	 *
	 * @param idDominio the id dominio
	 * @return true, si se realiza correctamente
	 */
	public boolean removeDesglose(String nif);

	/**
	 * Listar dominios.
	 *
	 * @param ambito Ambito GLOBAL(G), ENTIDAD(E) o AREA(A)
	 * @param id     Id de la entidad o area
	 * @param filtro Filro aplicado al código o descripcion.
	 * @return lista de dominios
	 */
	public List<DesgloseApellidos> listDesglose(String filtro, Date fechaD, Date fechaH);

	/**
	 * Clonar dominio.
	 *
	 * @param dominioID
	 * @param nuevoIdentificador
	 * @param areaID
	 * @param fdID
	 * @param idEntidad
	 */
	public void clonar(String nif, String nuevoIdentificador);

}
