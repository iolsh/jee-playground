package me.iolsh.users.boundry;

import me.iolsh.infrastructure.exceptions.RestExceptionTemplate;

import javax.ejb.ApplicationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@ApplicationException
public class InvalidCredentialsException extends WebApplicationException {

    private static final String MSG = "Invalid credentials.";
    private static final String SOLUTION = "Either register or provide valid user/password.";

    public InvalidCredentialsException() {
        super(Response.status(Response.Status.UNAUTHORIZED).entity(new RestExceptionTemplate(MSG, SOLUTION)).build());
    }

}
