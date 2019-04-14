package com.agh.soa.lab6.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryBook implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;

    @ManyToOne
    private Author author;

    @OneToMany(mappedBy = "book")
    private List<Borrow> borrows;

    @ManyToMany(mappedBy = "borrowedBooks")
    private List<Reader> readers;

    private Integer availableCopiesCount;
    private Integer totalCopiesCount;
}
