package me.iolsh.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidCredentialsExceptionMapper implements ExceptionMapper<InvalidCredentialsException> {

    final String MSG = "Invalid credentials.";
    final String SOLUTION = "Either register or provide valid user/password.";

    @Override
    public Response toResponse(InvalidCredentialsException exception) {
        return Response.status(Response.Status.UNAUTHORIZED).entity(new RestExceptionTemplate(MSG, SOLUTION)).build();
    }

}
