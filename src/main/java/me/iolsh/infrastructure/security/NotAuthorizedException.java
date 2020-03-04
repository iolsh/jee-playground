package me.iolsh.infrastructure.security;

import me.iolsh.infrastructure.exceptions.RestExceptionTemplate;

import javax.ejb.ApplicationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@ApplicationException
public class NotAuthorizedException extends WebApplicationException {
    private static final String MSG = "Not authorized.";
    private static final String SOLUTION = "Provide valid credentials.";

    public NotAuthorizedException() {
        super(Response.status(Response.Status.UNAUTHORIZED).entity(new RestExceptionTemplate(MSG, SOLUTION)).build());
    }
}
