package pos.labs.lab_4.controllers;

import org.apache.coyote.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.labs.lab_4.interfaces.IBookService;
import pos.labs.lab_4.models.Book;

import java.util.List;

@RestController()
public class BooksController {
    private final IBookService bookService;

    public BooksController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("api/books")
    public ResponseEntity<?> getBooks(){
        List<Book> bookList = bookService.getAllBooks();
        return ResponseEntity.status(200).body(bookList);
    }

    @GetMapping("api/books/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Integer id){
        Book book = bookService.getBookById(id);
        if(book == null){
            return ResponseEntity.status(404).body(null);
        }else{
            return ResponseEntity.status(200).body(book);
        }
    }

    @RequestMapping(value = "api/books", method = RequestMethod.POST)
    public ResponseEntity<?> addBook(@RequestBody Book book){
        try{
            Integer id = bookService.addBook(book);
            return ResponseEntity.status(201).body(id);
        }catch(DuplicateKeyException e){
            return ResponseEntity.status(401).body("book exists");
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("api/books/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Integer id){
        bookService.deleteBook(id);
        return ResponseEntity.status(200).body(null);
    }

    @RequestMapping(value = "api/books/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateBook(@PathVariable Integer id, @RequestBody Book book){
        try{
            book.id = id;
            bookService.addBookId(book);
            return ResponseEntity.status(201).body(id);
        }catch(DuplicateKeyException e){
            try{
                bookService.updateBook(book);
                return ResponseEntity.status(200).body(null);
            }
            catch(DuplicateKeyException e1){
                return ResponseEntity.status(401).body("book exists");
            }
            catch(Exception e1){
                return ResponseEntity.status(500).body(e.getMessage());
            }
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());

        }
    }
}
