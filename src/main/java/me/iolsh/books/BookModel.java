package me.iolsh.books;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "Book data")
public class BookModel {
    @Schema(required = true, example = "b3fc28d5-ac95-433b-8f5d-830cf28a3417")
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
