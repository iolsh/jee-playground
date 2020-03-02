package me.iolsh.books.boundry;

import me.iolsh.infrastructure.security.Secure;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Secure
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookDescriptionResource {

    private final String bookId;

    @Context
    HttpHeaders httpHeaders;

    public BookDescriptionResource(String bookId) {
        this.bookId = bookId;
    }

    @GET
    public Response getDescription() {
        BookDescriptionModel descriptionModel = new BookDescriptionModel("descriptionId", bookId, "Some description");
        return Response.status(Response.Status.OK).entity(descriptionModel).build();
    }
}
