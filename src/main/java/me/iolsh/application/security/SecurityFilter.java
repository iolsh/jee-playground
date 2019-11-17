package me.iolsh.application.security;

import io.jsonwebtoken.Jwts;
import me.iolsh.exceptions.RestExceptionTemplate;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
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

    private final String BEARER = "Bearer";
    private final String ERR_MSG = "Token invalid.";
    private final String SOLUTION = "Provide valid token.";

    @Inject
    Security security;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            throw new NotAuthorizedException("No valid token found");
        }
        String token = authHeader.substring(BEARER.length()).trim();
        Key key = security.generateKey(Security.JWT_SECRET);
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).
                entity(new RestExceptionTemplate(ERR_MSG, SOLUTION)).build());
        }
    }
}

