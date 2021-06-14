package pos.labs.lab_4.interfaces;

import pos.labs.lab_4.models.Book;

import java.util.List;

public interface IBookService {

    List<Book> getAllBooks();

    Book getBookById(Integer id);

    Integer addBook(Book book);

    void deleteBook(Integer id);

    void updateBook(Book book);

    Integer addBookId(Book book);
}
