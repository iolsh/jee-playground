package me.iolsh.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    private String id;
    private String author;
    private String title;
    private Integer year;
    private String genre;
}
