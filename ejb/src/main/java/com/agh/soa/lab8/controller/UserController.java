package com.agh.soa.lab8.controller;

import static com.agh.soa.lab8.controller.UserController.USER_CONTEXT;
import static com.agh.soa.lab8.util.MovieUtils.decorateUri;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;

import com.agh.soa.lab8.model.MovieUser;
import com.agh.soa.lab8.service.UserService;
import io.swagger.annotations.Api;
import java.util.List;
import java.util.function.Consumer;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Api("User Services")
@RequestScoped
@Path(USER_CONTEXT)
@Produces(APPLICATION_JSON)
public class UserController {

  static final String USER_CONTEXT = "users";

  @Context
  UriInfo uriInfo;

  @Inject
  UserService userService;

  private Consumer<MovieUser> decorator = user ->
    user.getMovies().forEach(movie -> decorateUri(movie, uriInfo));

  @GET
  public Response get() {
    return userService.findAll(decorator);
  }

  @GET
  @Path("/{id}")
  public Response get(@NotNull @PathParam("id") Long id) {
    return userService.find(id, decorator);
  }

  @POST
  public Response post(@NotNull MovieUser user) {
    return userService.create(user);
  }

  @PUT
  public Response put(@NotNull MovieUser user) {
    return userService.merge(user);
  }

  @DELETE
  @Path("/{id}")
  public Response delete(@NotNull @PathParam("id") Long id) {
    return userService.delete(id);
  }

  @PUT
  @Path("/{id}/movies")
  public Response mergeUserMovies(@NotNull @PathParam("id") Long id,
                                  @NotNull List<Long> movieIds) {
    return userService.mergeUserMovies(id, movieIds);
  }

  @GET
  @Path("/{id}/avatar")
  @Produces(APPLICATION_OCTET_STREAM)
  public Response downloadAvatar(@NotNull @PathParam("id") Long id) {
    return userService.getAvatarFile(id);
  }
}
