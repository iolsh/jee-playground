package me.iolsh.api.resources;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import me.iolsh.api.model.LoginModel;
import me.iolsh.api.model.UserModel;
import me.iolsh.entity.User;
import me.iolsh.exceptions.InvalidCredentialsException;
import me.iolsh.exceptions.UserAlreadyExistsException;
import me.iolsh.mappers.UserModelToEntityMapper;
import me.iolsh.repository.UserRepository;
import me.iolsh.application.security.Security;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("")
public class SecurityResource {

    @Context
    private UriInfo uriInfo;

    private final UserRepository userRepository;
    private final Security security;
    private final UserModelToEntityMapper userModelToEntityMapper;

    @Inject
    public SecurityResource(UserRepository userRepository, Security security,
        UserModelToEntityMapper userModelToEntityMapper) {
        this.userRepository = userRepository;
        this.security = security;
        this.userModelToEntityMapper = userModelToEntityMapper;
    }

    @POST
    @Path("register")
    @Operation(summary = "Register new user.", tags = {"users"},
        description = "Creates new user in application.",
        responses = {
            @ApiResponse(responseCode = "204", description = "User created."),
            @ApiResponse(responseCode = "400", description = "User already exists.")
    })
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
    @Operation(summary = "Authenticate.", tags = {"users"},
            description = "Authenticates user in application.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Authenticated.", headers = {
                    @Header(name = HttpHeaders.AUTHORIZATION,
                        description = "JWT token used for authorization against secured resources.")
                }),
                @ApiResponse(responseCode = "401", description = "Unauthorized.")
            })
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
