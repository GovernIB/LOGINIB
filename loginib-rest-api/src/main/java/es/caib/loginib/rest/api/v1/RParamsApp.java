package es.caib.loginib.rest.api.v1;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * Respuesta params.
 *
 * @author indra
 *
 */
public final class RParamsApp {

	/**
	 * Parametros.
	 */
	@ApiModelProperty(value = "Parametros", required = true)
	private List<RParamApp> parametros;

	public List<RParamApp> getParametros() {
		return parametros;
	}

	public void setParametros(final List<RParamApp> parametros) {
		this.parametros = parametros;
	}

}
