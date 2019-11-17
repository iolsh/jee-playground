package me.iolsh.mappers;

import me.iolsh.api.model.BookModel;
import me.iolsh.entity.Book;
import org.mapstruct.Mapper;

@Mapper
public interface EntityToBookModelMapper {

    BookModel mapEntityToBook(Book book);

}
