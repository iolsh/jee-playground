package me.iolsh.books.control;

import me.iolsh.books.boundry.BookModel;
import me.iolsh.books.entity.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    BookModel mapEntityToBook(Book book);
    Book mapBookToEntity(BookModel model);
}
