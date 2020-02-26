package me.iolsh.api.resources;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import me.iolsh.api.model.BookDescriptionModel;
import me.iolsh.application.security.Secure;

import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Secure
@SecurityRequirement(name = "JWT")
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
