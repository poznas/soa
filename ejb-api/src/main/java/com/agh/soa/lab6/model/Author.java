package com.agh.soa.lab6.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String firstName;
    private String secondName;

    @OneToMany(mappedBy = "author")
    private List<LibraryBook> books;
}
