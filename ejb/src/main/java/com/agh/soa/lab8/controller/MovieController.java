package com.agh.soa.lab8.controller;

import static com.agh.soa.lab8.controller.MovieController.MOVIES_CONTEXT;
import static com.agh.soa.lab8.util.MovieUtils.decorateUri;
import static java.util.Objects.nonNull;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.agh.soa.lab8.model.Movie;
import com.agh.soa.lab8.service.MovieService;
import io.swagger.annotations.Api;
import java.util.function.Consumer;
import java.util.function.Predicate;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Api("Movie Services")
@RequestScoped
@Path(MOVIES_CONTEXT)
@Produces(APPLICATION_JSON)
public class MovieController {

  public static final String MOVIES_CONTEXT = "movies";

  @Context
  UriInfo uriInfo;

  @Inject
  MovieService movieService;

  private Consumer<Movie> decorator = movie -> decorateUri(movie, uriInfo);

  @GET
  public Response get(@QueryParam("title") String title) {
    Predicate<Movie> filter = null;

    if (nonNull(title)) {
      filter = movie -> title.equals(movie.getTitle());
    }
    return movieService.findAll(filter, decorator);
  }

  @GET
  @Path("/{id}")
  public Response get(@NotNull @PathParam("id") Long id) {
    return movieService.find(id, decorator);
  }

  @POST
  public Response post(@NotNull Movie movie) {
    return movieService.create(movie);
  }

  @PUT
  public Response put(@NotNull Movie movie) {
    return movieService.merge(movie);
  }

  @DELETE
  @Path("/{id}")
  public Response delete(@NotNull @PathParam("id") Long id) {
    return movieService.delete(id);
  }

}
