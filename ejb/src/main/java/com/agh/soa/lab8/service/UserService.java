package com.agh.soa.lab8.service;

import static com.agh.soa.utils.CollectionUtils.mapList;
import static java.lang.System.getProperty;
import static java.util.Objects.nonNull;
import static java.util.Optional.of;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import com.agh.soa.lab8.dao.MovieRepository;
import com.agh.soa.lab8.dao.UserRepository;
import com.agh.soa.lab8.model.Movie;
import com.agh.soa.lab8.model.MovieUser;
import java.io.File;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import lombok.Getter;
import lombok.extern.java.Log;

@Log
@RequestScoped
public class UserService implements IRestCrudService<MovieUser> {

  private static final String AVATAR_DIR_PATH
    = getProperty("user.home") + File.separator + "avatars";

  @EJB
  @Getter
  UserRepository repository;
  @EJB
  MovieRepository movieRepository;

  public Response mergeUserMovies(@NotNull Long id, @NotNull List<Long> movieIds) {
    var user = repository.getEntity(id);

    if (nonNull(user)) {

      List<Movie> movies = mapList(movieIds, movieRepository::getEntity);

      user.setMovies(movies);
      repository.merge(user);

      return Response.noContent().build();
    }
    return Response.status(NOT_FOUND).build();
  }

  public Response getAvatarFile(Long id) {
    log.info("Attempt to download file from " + AVATAR_DIR_PATH);

    var avatar = of(id).map(repository::getEntity).map(MovieUser::getAvatar)
      .map(fileName -> new File(AVATAR_DIR_PATH + File.separator + fileName));

    if (avatar.isPresent()) {
      var response = Response.ok(avatar.get());
      response.header("Content-Disposition", "attachment;filename=" + avatar.get().getName());
      return response.build();
    }
    return Response.status(NOT_FOUND).build();
  }
}
