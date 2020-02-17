package me.iolsh.api.resources;

import me.iolsh.api.model.BookModel;
import me.iolsh.application.security.Secure;
import me.iolsh.mappers.EntityToBookModelMapper;
import me.iolsh.repository.BookRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/books")
@Produces(MediaType.APPLICATION_JSON)
@Secure
public class BookResource {

    private BookRepository bookRepository;
    private EntityToBookModelMapper entityToBookModelMapper;

    @Inject
    public BookResource(BookRepository bookRepository, EntityToBookModelMapper entityToBookModelMapper) {
        this.bookRepository = bookRepository;
        this.entityToBookModelMapper = entityToBookModelMapper;
    }

    @GET
    public Response getBooks() {
        List<BookModel> books = bookRepository.findAll().stream().map(entityToBookModelMapper::mapEntityToBook)
                .collect(Collectors.toList());
        return Response.status(Response.Status.OK).entity(books).build();
    }
}
