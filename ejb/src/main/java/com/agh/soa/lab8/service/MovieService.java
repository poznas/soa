package com.agh.soa.lab8.service;

import com.agh.soa.lab8.dao.MovieRepository;
import com.agh.soa.lab8.model.Movie;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import lombok.Getter;

@RequestScoped
public class MovieService implements IRestCrudService<Movie> {

  @EJB
  @Getter
  MovieRepository repository;

}
