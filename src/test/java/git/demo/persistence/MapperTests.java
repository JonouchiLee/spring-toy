package git.demo.persistence;

import git.demo.config.DatabaseConfig;
import git.demo.domain.book.Book;
import git.demo.domain.member.Member;
import git.demo.domain.study.Study;
import git.demo.mapper.BookMapper;
import git.demo.mapper.CartMapper;
import git.demo.mapper.MemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;


@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DatabaseConfig.class)
class MapperTests {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private CartMapper cartMapper;

    Member member;

    Book book;

    Study study;

    @BeforeEach
    void memberSetup() {
        member = new Member();
        member.setId(1L);
        member.setUserId("이영진");
        member.setUserPw("1234");
    }

    @BeforeEach
    void bookSetup() {
        book = new Book();
        book.setId(1L);
        book.setBookName("토비의스프링");
        book.setPrice(30000);
        book.setQuantity(10);

        study = new Study();
        study.setStudyId(1L);
        study.setName("토비스프링 스터디");
        study.setWriterName("dudwls0505");
        study.setContent("토비의스프링 스터디구합니다. 스프링공부해신분만");
    }


    @Test
    @DisplayName("MemberMapper 테스트")
    void testMemberMapper() {
        //given
        assertThat(memberMapper.isExistsId("이영진")).isFalse();
        //when
        memberMapper.insertMember(member);
        //then
        assertThat(memberMapper.findMemberById("이영진")).isEqualTo(member);
        assertThat(memberMapper.isExistsId("이영진")).isTrue();
    }


    @Test
    @DisplayName("BookMapper 테스트")
    void testBookMapper() {
        // insertBook , findBookById
        bookMapper.insertBook(book);
        assertThat(bookMapper.findBookById(1L)).isEqualTo(book);
        assertThat(bookMapper.findBookByName("토비의스프링")).isEqualTo(book);

        // updateBook
        bookMapper.updateBook(1L, "토비의스프링2권", 50000, 25);
        assertThat(bookMapper.findBookById(1L).getBookName()).isEqualTo("토비의스프링2권");
        assertThat(bookMapper.findBookById(1L).getQuantity()).isEqualTo(25);
        assertThat(bookMapper.findBookById(1L).getPrice()).isEqualTo(50000);

        //deleteBook
        bookMapper.deleteBook(1L);
        assertThat(bookMapper.findBookById(1L)).isNull();
    }


    @Test
    @DisplayName("CartMapper 테스트")
    void testCartMapper() {

        // insertcart
        cartMapper.insertCart(study);
//        assertThat(cartMapper.insertCart(study))

    }

}
