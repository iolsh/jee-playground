package me.iolsh.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookModel {
    private String id;
    private String author;
    private String title;
    private Integer year;
    private String genre;
}
