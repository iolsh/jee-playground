package me.iolsh.infrastructure.security;

import io.jsonwebtoken.Jwts;
import me.iolsh.infrastructure.exceptions.RestExceptionTemplate;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.annotation.Metric;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;

@Provider
@Secure
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

    private static final String BEARER = "Bearer";
    private static final String ERR_MSG = "Token invalid.";
    private static final String SOLUTION = "Provide valid token.";


    private final Security security;
    private final Counter failedAuthorizationAttempts;

    @Inject
    public SecurityFilter(Security security, @Metric Counter failedAuthorizationAttempts) {
        this.security = security;
        this.failedAuthorizationAttempts = failedAuthorizationAttempts;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            throw new NotAuthorizedException();
        }
        String token = authHeader.substring(BEARER.length()).trim();
        Key key = security.generateKey(Security.JWT_SECRET);
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        } catch (Exception e) {
            failedAuthorizationAttempts.inc();
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).
                    entity(new RestExceptionTemplate(ERR_MSG, SOLUTION)).build());
        }
    }
}

