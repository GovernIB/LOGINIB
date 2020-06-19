package es.caib.loginib.core.api.model.login;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista evidencias autenticación.
 *
 * @author Indra
 *
 */
public class EvidenciasLista {

	/** Evidencias. */
	List<PropiedadAutenticacion> propiedades = new ArrayList<>();

	/**
	 * Método de acceso a propiedades.
	 * 
	 * @return propiedades
	 */
	public List<PropiedadAutenticacion> getPropiedades() {
		return propiedades;
	}

	/**
	 * Método para establecer propiedades.
	 * 
	 * @param propiedades
	 *                        propiedades a establecer
	 */
	public void setPropiedades(final List<PropiedadAutenticacion> propiedades) {
		this.propiedades = propiedades;
	}

}
