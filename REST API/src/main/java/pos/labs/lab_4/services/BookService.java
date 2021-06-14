package pos.labs.lab_4.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pos.labs.lab_4.interfaces.IBookService;
import pos.labs.lab_4.mappers.BookRowMapper;
import pos.labs.lab_4.models.Book;
import pos.labs.lab_4.models.Role;
import pos.labs.lab_4.models.User;

import java.util.List;

@Service
public class BookService implements IBookService {
    private JdbcTemplate jdbcTemplate;
    private BookRowMapper bookRowMapper;

    public BookService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.bookRowMapper = new BookRowMapper();
    }

    @Override
    public List<Book> getAllBooks() {
        String query = "SELECT * FROM books";
        List<Book> bookList = jdbcTemplate.query(query, bookRowMapper);

        return bookList;
    }

    @Override
    public Book getBookById(Integer id) {
        String query = "SELECT * FROM books WHERE id=?";
        List<Book> bookList = jdbcTemplate.query(query, bookRowMapper,id);
        if(bookList.size() == 0){
            return null;
        }else{
            return bookList.get(0);
        }
    }

    @Override
    public Integer addBook(Book book) {
        String query = "INSERT INTO books (author, book_title, book_genre, book_year, editor_name, rezumat) VALUES (?,?,?,?,?,?)";
        String queryId = "SELECT * from books WHERE author = ? AND book_title = ? AND book_genre = ? AND book_year = ? AND editor_name = ? AND rezumat = ?";
        jdbcTemplate.update(query, book.author, book.bookTitle, book.bookGenre, book.bookYear, book.editor, book.rezumat);
        List<Book> bookList = jdbcTemplate.query(queryId, bookRowMapper, book.author, book.bookTitle, book.bookGenre, book.bookYear, book.editor, book.rezumat);
        if(bookList.size() == 0){
            return null;
        }else{
            return bookList.get(0).id;
        }
    }

    @Override
    public void deleteBook(Integer id) {
        String query  = "DELETE FROM books WHERE id= ? ";
        jdbcTemplate.update(query, id);
    }
    @Override
    public Integer addBookId(Book book) {
        String query = "INSERT INTO books (id, author, book_title, book_genre, book_year, editor_name, rezumat) VALUES (?,?,?,?,?,?,?)";
        jdbcTemplate.update(query, book.id, book.author, book.bookTitle, book.bookGenre, book.bookYear, book.editor, book.rezumat);
        return book.id;
    }

    @Override
    public void updateBook(Book book) {
        String query = "UPDATE books SET author = ?, book_title = ?, book_genre = ?, book_year = ?, editor_name = ?, rezumat = ? WHERE id = ?";
        jdbcTemplate.update(query, book.author, book.bookTitle, book.bookGenre, book.bookYear, book.editor, book.rezumat, book.id);

    }


}
