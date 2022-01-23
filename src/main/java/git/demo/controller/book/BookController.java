package git.demo.controller.book;

import git.demo.domain.book.Book;
import git.demo.mapper.BookMapper;
import git.demo.repository.book.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("book")
public class BookController {

    private final BookRepository bookRepository;
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

        bookRepository.save(book);
        return "redirect:/";

    }
}
