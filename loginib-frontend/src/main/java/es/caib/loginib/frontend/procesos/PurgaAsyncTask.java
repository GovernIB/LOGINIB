package es.caib.loginib.frontend.procesos;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import es.caib.loginib.core.api.ClaveService;

/**
 * Producer Siraj Task.
 */
@Component("purgaAsyncTask")
public class PurgaAsyncTask implements AsyncTaskIntf {

    /** Log. */
    private static Logger log = LoggerFactory.getLogger(PurgaAsyncTask.class);

    /** Clave service. */
    @Resource(name = "claveService")
    private ClaveService claveService;

    @Override
    @Async
    public final void doTask() {
        log.info("PurgaAsyncTask: inicio");
        claveService.purgar();
        log.info("PurgaAsyncTask: fin");
    }

}
