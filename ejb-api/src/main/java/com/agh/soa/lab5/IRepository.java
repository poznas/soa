package com.agh.soa.lab5;

import java.io.Serializable;
import java.util.List;


public interface IRepository<T extends Serializable> {

    T insert(T book);

    void merge(T book);

    void delete(T book);

    T getEntity(Long id);

    List<T> getEntities();
}
