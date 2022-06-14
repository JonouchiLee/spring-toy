package git.demo.controller.book;

import git.demo.domain.book.Book;
import git.demo.mapper.BookMapper;
import git.demo.service.book.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
        return "book/controlBook";
    }

    @GetMapping("/createBook")
    public String insertBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book/createBook";
    }

    @PostMapping("/createBook")
    public String insertBook(@Validated @ModelAttribute Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "book/createBook";
        }
        bookService.save(book);
        return "redirect:/";
    }

    @GetMapping("/readBookList")
    public String readBookPage(Model model) {
        List<Book> books = bookMapper.findAllBook();
        model.addAttribute("books", books);
        return "book/readBookList";
    }

    @GetMapping("/BookDetail/{bookId}")
    public String readBookDetail(@PathVariable Long bookId, Model model) {
        Book book = bookMapper.findBookById(bookId);
        model.addAttribute("book", book);
        log.info("readBookDetail getMapping 성공 그리고 book 값 = " + book);
        return "book/BookDetail";
    }

    @PostMapping("/readBookList/{bookId}/updateBookFin")
    public String editBookPost(@PathVariable Long bookId, @Validated @ModelAttribute Book book, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // 값을 DB에 넣어야함
        bookMapper.updateBook(book.getId(), book.getBookName(), book.getPrice(), book.getQuantity());
        log.info("수정완료 ");
        redirectAttributes.addAttribute("status", true);
        return "redirect:/book/readBookList";
    }

    @PostMapping("/BookDetail/{bookId}/bookDelete")
    public String deleteBookGet(@PathVariable Long bookId) {
        Book book = bookMapper.findBookById(bookId);
        bookMapper.deleteBook(bookId);
        log.info("deleteBook 성공");
        return "redirect:/book/readBookList";
    }
}
