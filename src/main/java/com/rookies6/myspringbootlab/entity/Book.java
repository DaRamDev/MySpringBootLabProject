package com.rookies6.myspringbootlab.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter@Setter
public class Book {
    @Id
    @GeneratedValue
    private long id;

    private String title;

    private String author;

    @Column(unique = true) //중복 불허
    private String isbn;

    private LocalDate publishDate;

    private int price;
}
