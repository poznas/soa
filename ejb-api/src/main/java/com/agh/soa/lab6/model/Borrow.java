package com.agh.soa.lab6.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Borrow implements Serializable {

    @EmbeddedId
    private BorrowId id = new BorrowId();

    @MapsId("bookId")
    @ManyToOne(cascade = {MERGE, PERSIST})
    private LibraryBook book;

    @MapsId("readerId")
    @ManyToOne(cascade = {MERGE, PERSIST})
    private Reader reader;

    private LocalDateTime borrowDateTime;

    private LocalDateTime returnDateTime;


}
