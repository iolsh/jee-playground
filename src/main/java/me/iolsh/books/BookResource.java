package me.iolsh.books;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import me.iolsh.infrastructure.security.Secure;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/books")
@Produces(MediaType.APPLICATION_JSON)
@Secure
@SecurityRequirement(name = "JWT")
public class BookResource {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Context
    ResourceContext resourceContext;

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
    public Response create(@Valid BookModel book, @Context UriInfo uriInfo) {
        Book entity = bookRepository.create(bookMapper.mapBookToEntity(book));
        URI uri = uriInfo.getAbsolutePathBuilder().path(entity.getId()).build();
        return Response.created(uri).entity(entity).build();
    }

    @Path("{id}/description")
    public BookDescriptionResource bookDescription(@PathParam("id") String bookId) {
        return resourceContext.initResource(new BookDescriptionResource(bookId ));
    }

}
