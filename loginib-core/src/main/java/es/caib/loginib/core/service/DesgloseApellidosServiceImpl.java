package es.caib.loginib.core.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.loginib.core.api.model.login.DesgloseApellidos;
import es.caib.loginib.core.api.service.DesgloseApellidosService;
import es.caib.loginib.core.interceptor.NegocioInterceptor;
import es.caib.loginib.core.service.repository.dao.DesgloseApellidosDao;

/**
 * La clase DominioServiceImpl.
 */
@Service("desgloseService")
@Transactional
public class DesgloseApellidosServiceImpl implements DesgloseApellidosService {

	/**
	 * log.
	 */
//	private final org.slf4j.Logger log = LoggerFactory.getLogger(DesgloseApellidosServiceImpl.class);

	/**
	 * dominio dao.
	 */
	@Autowired
	DesgloseApellidosDao desgloseDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#loadDominio(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public DesgloseApellidos loadDesglose(final String nif) {
		DesgloseApellidos result = null;
		result = desgloseDao.getByNif(nif);
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#addDominio(es.caib.
	 * sistrages.core.api.model.Dominio, java.lang.Long, java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public String addDesglose(final DesgloseApellidos da) {
		return desgloseDao.add(da);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#removeDominio(java.lang.
	 * Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean removeDesglose(final String nif) {
		return desgloseDao.remove(nif);
	}

	@Override
	@NegocioInterceptor
	public List<DesgloseApellidos> listDesglose(final String filtro, final Date fechaD, final Date fechaH) {
		return desgloseDao.getAllByFiltro(filtro, fechaD, fechaH);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#updateDominio(es.caib.
	 * sistrages.core.api.model.Dominio)
	 */
	@Override
	@NegocioInterceptor
	public void updateDesglose(final DesgloseApellidos desglose) {
		desgloseDao.update(desglose);
	}

	@Override
	@NegocioInterceptor
	public void clonar(final String nif, final String nuevoIdentificador) {
		desgloseDao.clonar(nif, nuevoIdentificador);
	}

}
