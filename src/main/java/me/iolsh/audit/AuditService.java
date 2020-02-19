package me.iolsh.audit;

import org.slf4j.Logger;

import javax.inject.Inject;

public class AuditService {

    @Inject
    private Logger logger;

    public void audit(Object toBeAudited) {
        logger.info("Audited {}", toBeAudited);
    }

}
