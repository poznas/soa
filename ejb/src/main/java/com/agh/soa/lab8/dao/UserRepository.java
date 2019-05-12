package com.agh.soa.lab8.dao;

import static javax.ejb.TransactionManagementType.BEAN;

import com.agh.soa.lab5.AbstractRepository;
import com.agh.soa.lab8.model.MovieUser;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;

@Stateful
@TransactionManagement(BEAN)
public class UserRepository extends AbstractRepository<MovieUser> {

  @Override
  protected Class<MovieUser> getType() {
    return MovieUser.class;
  }
}
