package me.iolsh.books.boundry;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Getter
@AllArgsConstructor
@ToString
@Schema(description = "Book description")
public class BookDescriptionModel {
    @Schema(required = true, example = "57b7bead-4bec-48e2-b08c-08602cc8fb71")
    private String id;
    @Schema(required = true, example = "b3fc28d5-ac95-433b-8f5d-830cf28a3417")
    private String bookId;
    @Schema(required = true, example = "Some description of requested book...")
    private String description;
}
