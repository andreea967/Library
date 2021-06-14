package pos.labs.lab_4.mappers;

import org.springframework.jdbc.core.RowMapper;
import pos.labs.lab_4.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return new Book(
                resultSet.getInt("id"),
                resultSet.getString("author"),
                resultSet.getString("book_title"),
                resultSet.getString("book_genre"),
                resultSet.getDate("book_year"),
                resultSet.getString("editor_name"),
                resultSet.getString("rezumat")
                );
    }
}
