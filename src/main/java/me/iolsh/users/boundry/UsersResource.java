package me.iolsh.users.boundry;

import me.iolsh.infrastructure.security.Security;
import me.iolsh.users.control.UserModelToEntityMapper;
import me.iolsh.users.entity.User;
import me.iolsh.users.entity.UserRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users")
public class UsersResource {

    @Context
    private UriInfo uriInfo;

    private final UserRepository userRepository;
    private final Security security;
    private final UserModelToEntityMapper userModelToEntityMapper;

    @Inject
    public UsersResource(UserRepository userRepository, Security security,
                         UserModelToEntityMapper userModelToEntityMapper) {
        this.userRepository = userRepository;
        this.security = security;
        this.userModelToEntityMapper = userModelToEntityMapper;
    }

    @POST
    @Path("register")
    @APIResponses(
        value = {
            @APIResponse(responseCode = "204", description = "User created."),
            @APIResponse(responseCode = "400", description ="User already exists.")
     })
    @Operation(summary = "Register new user.", description = "Creates new user in application.")
    public Response register(
            @RequestBody(description = "New user data.", required = true,
            content = @Content(
                schema = @Schema(implementation = UserModel.class)
            ))
            @Valid  UserModel user) {
        userRepository.findUserByUserName(user.getUserName()).ifPresent(u -> {throw new UserAlreadyExistsException();});
        user.setPassword(security.hash(user.getPassword()));
        userRepository.create(userModelToEntityMapper.mapUserToEntity(user));
        return Response.noContent().build();
    }

    @POST
    @Path("login")
    @APIResponses(
        value = {
            @APIResponse(responseCode = "200", description = "Authenticated.",
                headers = {
                    @Header(name = HttpHeaders.AUTHORIZATION,
                        description = "JWT token used for authorization against secured resources.")
                }),
            @APIResponse(responseCode = "401", description ="Unauthorized.")
        }
    )
    @Operation(summary = "Authenticate.", description = "Authenticates user in application.")
    public Response login(@Valid  LoginModel loginModel) {
        User user = userRepository.findUserByUserName(loginModel.getUserName())
            .orElseThrow(InvalidCredentialsException::new);
        String hash = user.getPassword();
        if (security.verifyPassword(loginModel.getPassword(), hash)) {
            String token = security.createToken(user, Security.TOKEN_VALIDITY, uriInfo);
            return Response.ok().header(HttpHeaders.AUTHORIZATION, token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
