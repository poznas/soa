package com.agh.soa.lab6.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Reader implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String firstName;
    private String secondName;

    @OneToMany(fetch = EAGER, mappedBy = "reader")
    private List<Borrow> borrows;

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "borrow",
            joinColumns = @JoinColumn(name = "reader_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<LibraryBook> borrowedBooks;

}
