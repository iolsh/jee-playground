package me.iolsh.books.boundry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Getter
@Setter
@ToString
@Schema(description = "Book data")
public class BookModel {
    private String id;
    @Schema(required = true, example = "Warren Buffet")
    private String author;
    @Schema(required = true, example = "The Wealth of Nations")
    private String title;
    @Schema(required = true, example = "2020")
    private Integer year;
    @Schema(required = true, example = "Fantasy")
    private String genre;
}
