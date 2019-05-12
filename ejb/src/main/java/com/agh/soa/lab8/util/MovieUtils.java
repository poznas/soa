package com.agh.soa.lab8.util;

import static com.agh.soa.lab8.controller.MovieController.MOVIES_CONTEXT;

import com.agh.soa.lab8.model.Movie;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.UriInfo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MovieUtils {

  public static void decorateUri(@NotNull Movie movie, @NotNull UriInfo uriInfo) {
    String fullUri = uriInfo.getBaseUri() + MOVIES_CONTEXT + "/" + movie.getId();

    movie.setUri(fullUri);
  }

}
