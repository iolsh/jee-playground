package me.iolsh.books;

import me.iolsh.books.BookModel;
import me.iolsh.books.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    BookModel mapEntityToBook(Book book);
    Book mapBookToEntity(BookModel model);
}
