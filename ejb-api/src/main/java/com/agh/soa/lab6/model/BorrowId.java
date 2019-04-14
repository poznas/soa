package com.agh.soa.lab6.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Setter
@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BorrowId implements Serializable {

    @Column(name = "reader_id")
    private Long readerId;
    @Column(name = "book_id")
    private Long bookId;
}
