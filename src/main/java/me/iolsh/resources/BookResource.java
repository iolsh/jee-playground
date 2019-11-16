package me.iolsh.resources;

import me.iolsh.config.Secure;
import me.iolsh.entity.Book;
import me.iolsh.repository.BookRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("books")
@Produces(MediaType.APPLICATION_JSON)
@Secure
public class BookResource {

    @Inject
    private BookRepository bookRepository;

    @GET
    public Response getBooks() {
        List<Book> books = bookRepository.findAll();
        return Response.status(Response.Status.OK).entity(books).build();
    }
}
