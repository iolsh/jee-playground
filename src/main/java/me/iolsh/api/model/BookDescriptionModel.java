package me.iolsh.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class BookDescriptionModel {
    private String id;
    private String bookId;
    private String description;
}
