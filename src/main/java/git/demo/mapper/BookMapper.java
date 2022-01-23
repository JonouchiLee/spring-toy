package git.demo.mapper;

import git.demo.domain.book.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper {

    void insertBook(Book book);
}
