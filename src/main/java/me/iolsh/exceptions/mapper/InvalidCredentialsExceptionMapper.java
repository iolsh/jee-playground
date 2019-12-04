package me.iolsh.exceptions.mapper;

import me.iolsh.exceptions.InvalidCredentialsException;
import me.iolsh.exceptions.RestExceptionTemplate;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidCredentialsExceptionMapper implements ExceptionMapper<InvalidCredentialsException> {

    private static final String MSG = "Invalid credentials.";
    private static final String SOLUTION = "Either register or provide valid user/password.";

    @Override
    public Response toResponse(InvalidCredentialsException exception) {
        return Response.status(Response.Status.UNAUTHORIZED).entity(new RestExceptionTemplate(MSG, SOLUTION)).build();
    }

}
