package com.agh.soa.lab8.service;


import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import com.agh.soa.lab5.AbstractRepository;
import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

public interface IRestCrudService<T> extends Serializable {

  AbstractRepository<T> getRepository();

  default Response findAll() {
    return findAll(null);
  }

  default Response findAll(Consumer<T> decorator) {
    return findAll(null, decorator);
  }

  default Response findAll(Predicate<T> predicate, Consumer<T> decorator) {
    List<T> entities = getRepository().getEntities();

    if (nonNull(predicate)) {
      entities = entities.stream().filter(predicate).collect(toList());
    }
    if (nonNull(decorator)) {
      entities.forEach(decorator);
    }

    if (entities.isEmpty()) {
      return Response.status(NOT_FOUND).build();
    }
    return Response.ok(entities).build();
  }

  default Response find(@NotNull Long id,  Consumer<T> decorator) {
    T entity = getRepository().getEntity(id);

    if(isNull(entity)) {
      return Response.status(NOT_FOUND).build();
    }
    if (nonNull(decorator)) {
      decorator.accept(entity);
    }
    return Response.ok(entity).build();
  }

  default Response create(@NotNull T entity) {
    getRepository().insert(entity);
    return Response.status(CREATED).build();
  }

  default Response merge(@NotNull T entity) {
    getRepository().merge(entity);
    return Response.noContent().build();
  }

  default Response delete(@NotNull Long id) {
    if (getRepository().delete(id)) {
      return Response.ok().build();
    }
    return Response.status(NOT_FOUND).build();
  }

}
