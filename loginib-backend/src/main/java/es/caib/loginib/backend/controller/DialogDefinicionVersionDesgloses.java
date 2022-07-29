package es.caib.loginib.backend.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.loginib.backend.model.DialogResult;
import es.caib.loginib.backend.util.UtilJSF;
import es.caib.loginib.core.api.model.login.DesgloseApellidos;
import es.caib.loginib.core.api.model.login.types.TypeModoAcceso;
import es.caib.loginib.core.api.service.DesgloseApellidosService;
import es.caib.loginib.core.api.model.login.types.TypeNivelGravedad;

@ManagedBean
@ViewScoped
public class DialogDefinicionVersionDesgloses extends DialogControllerBase {

	/**
	 * Enlace servicio.
	 */
	@Inject
	private DesgloseApellidosService desgloseService;

	/**
	 * NIF elemento a tratar.
	 */
	private String nif;

	/**
	 * Datos desglose.
	 */
	private DesgloseApellidos desglose;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		UtilJSF.checkSecOpenDialog(modo, getNif());

		if (modo == TypeModoAcceso.CONSULTA || modo == TypeModoAcceso.EDICION) {
			setDesglose(desgloseService.loadDesglose(nif));
		} else {
			setDesglose(new DesgloseApellidos());
		}
	}

	/**
	 * Cancelar.
	 */
	public void cerrar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Aceptar
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			// comprobamos si ya existe para que no haya duplicados
			final DesgloseApellidos existe = desgloseService.loadDesglose(desglose.getNif());
			if (existe != null) {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.tipo.duplicado"));
				return;
			}
			desglose.setApellidoComp(desglose.getApellido1() + " " + desglose.getApellido2());
			desglose.setNombreCompleto(
					desglose.getNombre() + " " + desglose.getApellido1() + " " + desglose.getApellido2());
			Date fecha = new Date();
			desglose.setFechaCreacion(fecha);
			if (isCorrecto()) {
				desgloseService.addDesglose(desglose);
			} else {
				return;
			}
			break;

		case EDICION:
			if (isCorrecto()) {
				desgloseService.updateDesglose(desglose);
			} else {
				return;
			}
			break;
		case CONSULTA:
		default:
			break;
		}

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(desglose);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Método que comprueba si está todo correcto. Es decir, las particulas de
	 * nombre, apellido1 y apellido2 están en nombreCompleto.
	 * 
	 * @return
	 */
	private boolean isCorrecto() {
		final List<String> nombreCompleto = new LinkedList<String>(
				Arrays.asList(desglose.getNombreCompleto().split(" ")));

		// Se comprueba que la particula nombre esta en el nombreCompleto y se quita
		if (nombreCompleto.contains(desglose.getNombre())) {
			nombreCompleto.remove(desglose.getNombre());
		} else {
			// Lanzar error porque no existe la particula
			String[] param = new String[2];
			param[0] = desglose.getNombre();
			param[1] = desglose.getNombreCompleto();
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("error.tipo.particulaInexistente", param));
			return false;
		}

		// Se comprueba que la particula apellido1 esta en el nombreCompleto y se quita
		String[] apellidos = desglose.getApellido1().split(" ");
		for (String apellido : apellidos) {
			if (nombreCompleto.contains(apellido)) {
				nombreCompleto.remove(apellido);
			} else {
				// Lanzar error porque no existe la particula
				String[] param = new String[2];
				param[0] = apellido;
				param[1] = desglose.getNombreCompleto();
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
						UtilJSF.getLiteral("error.tipo.particulaInexistente", param));
				return false;
			}
		}

		// Se comprueba que la particula apellido2 esta en el nombreCompleto y se quita
		apellidos = desglose.getApellido2().split(" ");
		for (String apellido : apellidos) {
			if (nombreCompleto.contains(apellido)) {
				nombreCompleto.remove(apellido);
			} else {
				// Lanzar error porque no existe la particula
				String[] param = new String[2];
				param[0] = apellido;
				param[1] = desglose.getNombreCompleto();
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
						UtilJSF.getLiteral("error.tipo.particulaInexistente", param));
				return false;
			}
		}

		// Si no esta vacio, es que no se han usado todas las particulas
		return nombreCompleto.isEmpty();
	}

	/**
	 * @return the desglose
	 */
	public final DesgloseApellidos getDesglose() {
		return desglose;
	}

	/**
	 * @param desglose the desglose to set
	 */
	public final void setDesglose(DesgloseApellidos desglose) {
		this.desglose = desglose;
	}

	/**
	 * @return the nif
	 */
	public final String getNif() {
		return nif;
	}

	/**
	 * @param nif the nif to set
	 */
	public final void setNif(String nif) {
		this.nif = nif;
	}

	public final boolean getIsConsulta() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.CONSULTA) {
			return true;
		} else {
			return false;
		}
	}

	public void nombreCompuesto() {
		desglose.setApellidoComp(desglose.getApellido1() + " " + desglose.getApellido2());
		desglose.setNombreCompleto(
				desglose.getNombre() + " " + desglose.getApellido1() + " " + desglose.getApellido2());
	}

	public final boolean getIsEdicion() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.EDICION) {
			return true;
		} else {
			return false;
		}
	}
}
