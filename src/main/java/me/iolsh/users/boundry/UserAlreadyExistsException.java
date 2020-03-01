package me.iolsh.users.boundry;

import me.iolsh.infrastructure.exceptions.RestExceptionTemplate;

import javax.ejb.ApplicationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@ApplicationException
public class UserAlreadyExistsException extends WebApplicationException {

    private static final String MSG = "User already exists.";
    private static final String SOLUTION = "Login with valid credentials.";

    public UserAlreadyExistsException() {
        super(Response.status(Response.Status.BAD_REQUEST).entity(new RestExceptionTemplate(MSG, SOLUTION)).build());
    }

}
