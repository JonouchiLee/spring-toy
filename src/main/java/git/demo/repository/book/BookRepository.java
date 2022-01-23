package git.demo.repository.book;

import git.demo.domain.book.Book;
import git.demo.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BookRepository {

    private static long sequence = 0L;
    private final BookMapper bookMapper;

    public void save(Book book) {
        book.setId(++sequence);
        System.out.println("book상태="+ book);
        bookMapper.insertBook(book);
    }

}
