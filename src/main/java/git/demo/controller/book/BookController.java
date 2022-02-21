package git.demo.controller.book;

import git.demo.domain.book.Book;
import git.demo.mapper.BookMapper;
import git.demo.service.book.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("book")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;


    @GetMapping("/controlBook")
    public String controlBookPage() {
        log.info("controlBookPage GetMapping 실행");
        return "book/controlBook";
    }


    @GetMapping("/createBook")
    public String insertBookForm(@ModelAttribute Book book) {
        log.info("insertBookForm GetMapping 실행");
        return "book/createBook";
    }

    @PostMapping("/createBook")
    public String insertBook(@Valid @ModelAttribute Book book, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "book/createBook";
        }

        bookService.save(book);
        return "redirect:/";

    }

    @GetMapping("/readBookList")
    public String readBookPage(Model model) {
        log.info("readBookPage GetMapping 실행");
        List<Book> books = bookMapper.findAllBook();
        model.addAttribute("books", books);
        System.out.println("books=" + books);
        return "book/readBookList";
    }


    @GetMapping("/BookDetail/{bookId}")
    public String readBookDetail(@PathVariable Long bookId, Model model) {
        Book book = bookMapper.findBookById(bookId);
        model.addAttribute("book", book);
        log.info("readBookDetail getMapping 성공 그리고 book 값 = " + book);
        return "book/BookDetail";
    }

    @GetMapping("/readBookList/{bookId}/edit")
    public String editBook(@PathVariable Long bookId, Model model) {
        Book book = bookMapper.findBookById(bookId);
        model.addAttribute("book", book);
        log.info("editBook getMapping 성공 그리고 book 값 = " + book);
        return "book/updateBook";
    }

    @PostMapping("/readBookList/{bookId}/updateBookFin")
    public String editBookPost(@PathVariable Long bookId, @Valid @ModelAttribute Book book, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // 값을 DB에 넣어야함
        bookMapper.updateBook(book.getId(), book.getBookName(), book.getPrice(), book.getQuantity());
        System.out.println("bookget정보들=" + book.getId()+book.getBookName()+book.getPrice()+book.getQuantity());
        log.info("수정완료 ");
        redirectAttributes.addAttribute("status", true);
        return "redirect:/book/readBookList";
    }



}
