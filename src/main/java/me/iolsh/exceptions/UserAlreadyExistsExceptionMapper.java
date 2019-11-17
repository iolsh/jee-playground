package me.iolsh.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserAlreadyExistsExceptionMapper implements ExceptionMapper<UserAlreadyExistsException> {

    final String MSG = "User already exists.";
    final String SOLUTION = "Login with valid credentials.";

    @Override
    public Response toResponse(UserAlreadyExistsException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new RestExceptionTemplate(MSG, SOLUTION)).build();
    }
}
