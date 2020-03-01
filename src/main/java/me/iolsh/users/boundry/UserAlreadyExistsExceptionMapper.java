package me.iolsh.users.boundry;

import me.iolsh.infrastructure.exceptions.RestExceptionTemplate;
import me.iolsh.users.boundry.UserAlreadyExistsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserAlreadyExistsExceptionMapper implements ExceptionMapper<UserAlreadyExistsException> {

    private static final String MSG = "User already exists.";
    private static final String SOLUTION = "Login with valid credentials.";

    @Override
    public Response toResponse(UserAlreadyExistsException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new RestExceptionTemplate(MSG, SOLUTION)).build();
    }
}
