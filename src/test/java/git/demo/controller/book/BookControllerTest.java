package git.demo.controller.book;

import git.demo.controller.member.LoginController;
import git.demo.domain.book.Book;
import git.demo.domain.member.Member;
import git.demo.mapper.BookMapper;
import git.demo.mapper.MemberMapper;
import git.demo.service.book.BookService;
import git.demo.service.member.LoginService;
import git.demo.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookMapper bookMapper;
    @MockBean
    BookService bookService;
    @MockBean
    LoginService loginService;
    @MockBean
    LoginController loginController;
    @MockBean
    MemberService memberService;
    @MockBean
    MemberMapper memberMapper;

    Member member;

    @BeforeEach
    void sessionSetup(){
        member = new Member();
        member.setUserId("test");
        member.setId(1L);
        member.setUserPw("1234");
    }




    @Test
    @DisplayName("북페이지 포워딩한다")
    void controlBookPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/book/controlBook")
        .sessionAttr("loginMember",member))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("book/controlBook"));
    }


    @Test
    @DisplayName("북추가페이지 포워딩한다")
    void createBookPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/book/createBook")
        .sessionAttr("loginMember",member))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("book/createBook"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    @DisplayName("Book값이 제대로입력되지않으면 book/createBook화면으로 돌아간다")
    void insertBookTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/book/createBook")
        .sessionAttr("loginMember",member))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors())
                .andExpect(view().name("book/createBook"));
    }

    @Test
    @DisplayName("Book값이 제대로 입력되면 리다이렉트한다")
    void insertBookTestSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/book/createBook")
        .sessionAttr("loginMember", member)
                .param("id", String.valueOf(3L))
                .param("bookName","균형의수호자")
                .param("price", String.valueOf(1000))
                .param("quantity", String.valueOf(3)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("")
    void readBookPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/book/readBookList")
        .sessionAttr("loginMember",member))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(view().name("book/readBookList"));
    }

    @Test
    @DisplayName("BookList PathVariable 확인")
    void readBookDetailTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/book/BookDetail/1")
        .sessionAttr("loginMember",member))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("bookId").value(1))
                .andExpect(view().name("book/BookDetail"))
                .andExpect(model().attributeExists("book"));
    }





}