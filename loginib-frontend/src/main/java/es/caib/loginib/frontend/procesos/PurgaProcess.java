package es.caib.loginib.frontend.procesos;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Proceso que arranca proceso purga.
 */
@Service
public final class PurgaProcess {

    /** Log. */
    @SuppressWarnings("unused")
    private static Logger log = LoggerFactory.getLogger(PurgaProcess.class);

    /** Tarea asincrona. */
    @Resource(name = "purgaAsyncTask")
    private AsyncTaskIntf purgaAsyncTask;

    /** Process. */
    @Scheduled(cron = "${procesos.purga.cron}")
    public void process() {
        purgaAsyncTask.doTask();
    }

}
