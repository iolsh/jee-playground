package me.iolsh.api.resources;

import me.iolsh.api.model.LoginModel;
import me.iolsh.api.model.UserModel;
import me.iolsh.entity.User;
import me.iolsh.exceptions.UserAlreadyExistsException;
import me.iolsh.mappers.UserModelToEntityMapper;
import me.iolsh.repository.UserRepository;
import me.iolsh.application.security.Security;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

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
    @Valid
    public Response register(@Valid  UserModel user) {
        //TODO refactor crap
        try {
            userRepository.findUserByUserName(user.getUserName());
        } catch (NoResultException e) {
            user.setPassword(security.hash(user.getPassword()));
            userRepository.create(userModelToEntityMapper.mapUserToEntity(user));
            return Response.noContent().build();
        }
        throw new UserAlreadyExistsException();
    }

    @POST
    @Path("login")
    public Response login(@Valid  LoginModel loginModel) {
        User user = userRepository.getRegisteredUserByUserName(loginModel.getUserName());
        String hash = user.getPassword();
        if (security.verifyPassword(loginModel.getPassword(), hash)) {
            String token = security.createToken(user, Security.TOKEN_VALIDITY, uriInfo);
            return Response.ok().header(HttpHeaders.AUTHORIZATION, token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
