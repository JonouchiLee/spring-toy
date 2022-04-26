package git.demo.domain.book;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Book {

    private Long id;
    @NotEmpty
    private String bookName;
    @NotNull(message = "가격을 입력해주세요")
    private Integer price;
    @NotNull(message = "수량을 입력해주세요")
    private Integer quantity;

    public Book() {
    }

    public Book(Long id, String bookName, Integer price, Integer quantity) {
        this.id = id;
        this.bookName = bookName;
        this.price = price;
        this.quantity = quantity;
    }
}
