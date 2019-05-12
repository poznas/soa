package com.agh.soa.lab8.service;

import static java.util.stream.Collectors.toList;

import com.agh.soa.lab8.dao.MovieRepository;
import com.agh.soa.lab8.model.Movie;
import java.util.List;
import java.util.function.Consumer;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@RequestScoped
public class MovieService implements IRestCrudService<Movie> {

  @EJB
  @Getter
  MovieRepository repository;

  public List<String> getAsUriList(@NotNull Consumer<Movie> decorator) {
    return repository.getEntities().stream()
      .peek(decorator).map(Movie::getUri).collect(toList());
  }
}
