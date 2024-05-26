package com.example.licenseebe.dto.response;

import com.example.licenseebe.helper.BookType;
import com.example.licenseebe.model.Book;
import com.example.licenseebe.model.BookCategory;
import com.example.licenseebe.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserViewBookCardDataDTO {

    private UUID id;
    String title;
    Float price;
    BookType bookType;
    byte[] picture;

    public UserViewBookCardDataDTO(Book book) throws SQLException {
        this.id = book.getId();
        this.title = book.getTitle();
        this.bookType = book.getType();
        if (book.getPicture() != null)
            this.picture = book.getPicture().getBytes(1, (int) book.getPicture().length());
        this.price = book.getPrice();
    }
}
