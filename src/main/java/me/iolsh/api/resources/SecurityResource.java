package me.iolsh.api.resources;

import me.iolsh.api.model.LoginModel;
import me.iolsh.api.model.UserModel;
import me.iolsh.entity.User;
import me.iolsh.mappers.UserModelToEntityMapper;
import me.iolsh.repository.UserRepository;
import me.iolsh.application.security.Security;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Optional;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("")
public class SecurityResource {

    @Context
    private UriInfo uriInfo;
    @Inject
    private UserRepository userRepository;
    @Inject
    private Security security;
    @Inject
    private UserModelToEntityMapper userModelToEntityMapper;

    @POST
    @Path("register")
    public Response register(UserModel user) {
        user.setPassword(security.hash(user.getPassword()));
        userRepository.create(userModelToEntityMapper.mapUserToEntity(user));
        return Response.noContent().build();
    }

    @POST
    @Path("login")
    public Response login(LoginModel loginModel) {
        Optional<User> user = userRepository.findUserByUserName(loginModel.getUserName());
        if (user.isPresent()) {
            String hash = user.get().getPassword();
            if (security.verifyPassword(loginModel.getPassword(), hash)) {
                String token = security.createToken(user.get(), Security.TOKEN_VALIDITY, uriInfo);
                return Response.ok().header(HttpHeaders.AUTHORIZATION, token).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
