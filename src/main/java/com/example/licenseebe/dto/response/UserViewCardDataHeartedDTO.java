package com.example.licenseebe.dto.response;

import com.example.licenseebe.helper.BookType;
import com.example.licenseebe.model.Book;
import com.example.licenseebe.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserViewCardDataHeartedDTO {
    private UUID id;
    String title;
    Float price;
    BookType bookType;
    byte[] picture;
    Timestamp createdAt;
    private boolean isWishListed;

    public UserViewCardDataHeartedDTO(Book book, User u) throws SQLException {
        this.id = book.getId();
        this.title = book.getTitle();
        this.bookType = book.getType();
        if (book.getPicture() != null)
            this.picture = book.getPicture().getBytes(1, (int) book.getPicture().length());
        this.price = book.getPrice();
        if (book.getWishList().contains(u))
            this.isWishListed = true;
        else
            this.isWishListed = false;
        this.createdAt = book.getCreatedAt();
    }
}
