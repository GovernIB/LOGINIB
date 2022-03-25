package es.caib.loginib.backend.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.loginib.backend.model.DialogResult;
import es.caib.loginib.backend.model.types.TypeParametroVentana;
import es.caib.loginib.backend.util.UtilJSF;
import es.caib.loginib.core.api.exception.MaxNumFilasException;
import es.caib.loginib.core.api.model.login.DesgloseApellidos;
import es.caib.loginib.core.api.model.login.types.TypeModoAcceso;
import es.caib.loginib.core.api.model.login.types.TypeNivelGravedad;
import es.caib.loginib.core.api.service.DesgloseApellidosService;

/**
 * Mantenimiento de entidades.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDesgloses extends ViewControllerBase {

	/**
	 * Enlace servicio.
	 */
	@Inject
	private DesgloseApellidosService desgloseService;

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;
	private Date filtroFechaDesde;
	private Date filtroFechaHasta;

	/**
	 * Dato seleccionado en la lista.
	 */
	private DesgloseApellidos desgloseSeleccionado;
	/**
	 * Lista de datos.
	 */
	private List<DesgloseApellidos> listaDatos;

	/**
	 * Inicializacion.
	 */
	public void init() {
		// Control acceso
		UtilJSF.verificarAccesoSuperAdministrador();
		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		listaDatos = desgloseService.listDesglose("", null, null);

	}

	public boolean getFilaSeleccionada() {
		return verificarFilaSeleccionada();
	}

	/**
	 * Abre dialogo para consultar dato.
	 */
	public void consultar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada()) {
			return;
		}

		abrirVentana(this.desgloseSeleccionado, TypeModoAcceso.CONSULTA);
	}

	/**
	 * Abre dialogo para crear dato
	 */
	public void nuevo() {
		// Muestra dialogo
		abrirVentana(this.desgloseSeleccionado, TypeModoAcceso.ALTA);
	}

	/**
	 * Abre dialogo para editar dato
	 */
	public void editar() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada()) {
			return;
		}

		abrirVentana(this.desgloseSeleccionado, TypeModoAcceso.EDICION);
	}

	private void abrirVentana(DesgloseApellidos desglose, TypeModoAcceso modoAcceso) {
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		if (desglose != null) {
			params.put(TypeParametroVentana.ID.toString(), String.valueOf(desglose.getNif()));
		}
		UtilJSF.openDialog(DialogDefinicionVersionDesgloses.class, modoAcceso, params, true, 550, 370);
	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;
		// Eliminamos
		if (desgloseService.removeDesglose(this.desgloseSeleccionado.getNif())) {
			// Refrescamos datos
			buscar();
			// Mostramos mensaje
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
		}
	}

	/**
	 * Retorno dialogo confirmar
	 *
	 * @param event respuesta dialogo
	 ***/
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			String message = null;
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				message = UtilJSF.getLiteral("info.alta.ok");
			} else {
				message = UtilJSF.getLiteral("info.modificado.ok");
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
			// Refrescamos datos
			buscar();
		}
	}

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {
		// Normaliza filtro
		filtro = normalizarFiltro(filtro);
		// Buscar
		this.buscar();
	}

	/**
	 * Método final que se encarga de realizar la búsqueda
	 */
	private void buscar() {
		// Filtra

		try {
			listaDatos = desgloseService.listDesglose(filtro, filtroFechaDesde, filtroFechaHasta);
		} catch (final EJBException e) {
			if (e.getCause() instanceof MaxNumFilasException) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.maxnumfilas"));
				return;
			} else {
				throw e;
			}

		}

		// Quitamos seleccion de dato
		desgloseSeleccionado = null;
	}

	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;

		if (this.desgloseSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * @return the filtro
	 */
	public String getFiltro() {
		return filtro;
	}

	/**
	 * @param filtro the filtro to set
	 */
	public void setFiltro(final String filtro) {
		this.filtro = filtro;
	}

	/**
	 * @return the listaDatos
	 */
	public List<DesgloseApellidos> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos the listaDatos to set
	 */
	public void setListaDatos(final List<DesgloseApellidos> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public DesgloseApellidos getDesgloseSeleccionado() {
		return desgloseSeleccionado;
	}

	/**
	 * @param datoSeleccionado the datoSeleccionado to set
	 */
	public void setDesgloseSeleccionado(final DesgloseApellidos desgloseSeleccionado) {
		this.desgloseSeleccionado = desgloseSeleccionado;
	}

	public Date getFiltroFechaDesde() {
		return filtroFechaDesde;
	}

	public void setFiltroFechaDesde(final Date filtroFechaDesde) {
		this.filtroFechaDesde = filtroFechaDesde;
	}

	public Date getFiltroFechaHasta() {
		return filtroFechaHasta;
	}

	public void setFiltroFechaHasta(final Date filtroFechaFin) {
		this.filtroFechaHasta = filtroFechaFin;
	}

}
