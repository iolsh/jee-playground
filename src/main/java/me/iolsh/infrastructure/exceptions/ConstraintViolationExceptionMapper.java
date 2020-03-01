package me.iolsh.infrastructure.exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        final Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation vi : exception.getConstraintViolations()) {
            String message = vi.getMessage();
            String propertyName = vi.getPropertyPath().toString();
            errors.put(propertyName, message);
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errors).build();
    }
}
