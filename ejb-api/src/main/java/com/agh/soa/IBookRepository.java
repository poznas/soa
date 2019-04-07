package com.agh.soa;

import com.agh.soa.lab5.model.Book;

import java.util.List;

public interface IBookRepository {

    void insert(Book book);

    public void merge(Book book);

    public void delete(Book book);

    public List<Book> getEntities();
}
