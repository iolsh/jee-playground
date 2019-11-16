package me.iolsh.resources;

import me.iolsh.config.Login;
import me.iolsh.entity.User;
import me.iolsh.repository.UserRepository;
import me.iolsh.util.Security;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/sec")
public class SecurityResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private Logger logger;
    @Inject
    private UserRepository userRepository;
    @Inject
    private Security security;

    @POST
    @Path("/register")
    public Response register(User user) {
        logger.log(Level.INFO, "Got user {0}", user);
        user.setPassword(security.hash(user.getPassword()));
        userRepository.create(user);
        return Response.noContent().build();
    }

    @POST
    @Path("/login")
    public Response login(Login login) {
        logger.log(Level.INFO, "Login {0}", login);
        Optional<User> user = userRepository.findUserByUserName(login.getUserName());
        if (user.isPresent()) {
            String hash = user.get().getPassword();
            if (security.verifyPassword(login.getPassword(), hash)) {
                String token = security.createToken(user.get(), Security.TOKEN_VALIDITY, uriInfo);
                return Response.ok().header(HttpHeaders.AUTHORIZATION, token).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
