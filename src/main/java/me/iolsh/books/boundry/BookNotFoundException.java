package me.iolsh.books.boundry;

import me.iolsh.infrastructure.exceptions.RestExceptionTemplate;

import javax.ejb.ApplicationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@ApplicationException
public class BookNotFoundException extends WebApplicationException {
    private static final String MSG = "Book not found.";
    private static final String SOLUTION = "Provide valid bookId.";

    public BookNotFoundException() {
        super(Response.status(Response.Status.NOT_FOUND).entity(new RestExceptionTemplate(MSG, SOLUTION)).build());
    }
}
