package pos.labs.lab_4.models;

import java.sql.Date;

public class Book {
    public Integer id;
    public String author;
    public String bookTitle;
    public String bookGenre;
    public Date bookYear;
    public String editor;
    public String rezumat;

    public Book(Integer id, String author, String bookTitle, String bookGenre, Date bookYear, String editor, String rezumat) {
        this.id = id;
        this.author = author;
        this.bookTitle = bookTitle;
        this.bookGenre = bookGenre;
        this.bookYear = bookYear;
        this.editor = editor;
        this.rezumat = rezumat;
    }
}
