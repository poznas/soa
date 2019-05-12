package com.agh.soa.lab8.dao;

import static javax.ejb.TransactionManagementType.BEAN;

import com.agh.soa.lab5.AbstractRepository;
import com.agh.soa.lab8.model.Movie;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;

@Stateful
@TransactionManagement(BEAN)
public class MovieRepository extends AbstractRepository<Movie> {

  @Override
  protected Class<Movie> getType() {
    return Movie.class;
  }
}
