package me.iolsh.books.boundry;


import me.iolsh.books.control.BookMapper;
import me.iolsh.books.entity.Book;
import me.iolsh.books.entity.BookRepository;
import me.iolsh.infrastructure.security.Secure;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Secure
@Path("/books")
@SecurityRequirement(name = "JWT")
@Tag(name = "Books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BooksResource {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Context
    ResourceContext resourceContext;

    @Inject
    public BooksResource(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @GET
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "200", description = "List of books.",
                            content = @Content(mediaType = "application/json"))
            })
    @Operation(summary = "Get books.", description = "Obtains list of books.")
    public Response getBooks(@RequestBody @HeaderParam(HttpHeaders.AUTHORIZATION) String auth) {
        List<BookModel> books = bookRepository.findAll().stream().map(bookMapper::mapEntityToBook)
                .collect(Collectors.toList());
        return Response.status(Response.Status.OK).entity(books).build();
    }


    @POST
    public Response create(@Valid BookModel book, @Context UriInfo uriInfo,
                           @HeaderParam(HttpHeaders.AUTHORIZATION) String auth) {
        Book entity = bookRepository.create(bookMapper.mapBookToEntity(book));
        URI uri = uriInfo.getAbsolutePathBuilder().path(entity.getId()).build();
        return Response.created(uri).entity(entity).build();
    }

    @GET
    @Path("{id}")
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "200", description = "Book.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookModel.class)))
            })
    @Operation(summary = "Get book.", description = "Get single book.")
    public Response getBook(@PathParam("id") String bookId, @HeaderParam(HttpHeaders.AUTHORIZATION) String auth) {
        Book book = bookRepository.getOne(bookId);
        return Response.ok().entity(bookMapper.mapEntityToBook(book)).build();
    }

    @Path("{id}/description")
    public BookDescriptionResource bookDescription(@PathParam("id") String bookId) {
        return resourceContext.initResource(new BookDescriptionResource(bookId));
    }

}
