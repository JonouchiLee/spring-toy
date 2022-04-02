package git.demo.service.book;

import git.demo.domain.book.Book;
import git.demo.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private static long sequence = 0L;
    private final BookMapper bookMapper;

    public void save(Book book) {
        book.setId(++sequence);
        System.out.println("book상태="+ book);
        bookMapper.insertBook(book);
    }


    public void search(Book book) {

    }

}
