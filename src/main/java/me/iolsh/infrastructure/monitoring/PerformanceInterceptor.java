package me.iolsh.infrastructure.monitoring;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Date;

@Interceptor
@PerformanceMonitor
public class PerformanceInterceptor {

    private final Logger logger;

    @Inject
    public PerformanceInterceptor(Logger logger) {
        this.logger = logger;
    }

    @AroundInvoke
    public Object monitor(InvocationContext ctx) throws Exception {
        long start = new Date().getTime();
        try {
            return ctx.proceed();
        } finally {
            long elapsed = new Date().getTime() - start;
            logger.info("Execution time of method {} is {} ms.", ctx.getMethod().getName(), elapsed);
        }
    }
}
