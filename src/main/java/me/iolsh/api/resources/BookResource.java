package me.iolsh.api.resources;

import me.iolsh.api.model.BookModel;
import me.iolsh.application.security.Secure;
import me.iolsh.mappers.BookMapper;
import me.iolsh.repository.BookRepository;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Inject
    public BookResource(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @GET
    public Response getBooks() {
        List<BookModel> books = bookRepository.findAll().stream().map(bookMapper::mapEntityToBook)
                .collect(Collectors.toList());
        return Response.status(Response.Status.OK).entity(books).build();
    }

    @POST
    public Response create(@Valid BookModel book) {
        bookRepository.create(bookMapper.mapBookToEntity(book));
        return Response.ok().build();
    }


}
