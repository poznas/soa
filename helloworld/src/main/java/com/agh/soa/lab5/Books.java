package com.agh.soa.lab5;


import com.agh.soa.lab5.model.Book;
import lombok.Getter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@Getter
@SessionScoped
@ManagedBean(name = "books")
public class Books {

    @EJB(lookup = "java:global/ejb/BookRepository")
    private IBookRepository bookRepository;

    private Book formBook = new Book();

    public void delete() {
        bookRepository.delete(formBook);
    }

    public void merge() {
        bookRepository.merge(formBook);
    }

    public void insert() {
        formBook.setId(null);
        bookRepository.insert(formBook);
    }
}
