package com.example.licenseebe.dto.response;

import com.example.licenseebe.helper.BookType;
import com.example.licenseebe.model.Book;
import com.example.licenseebe.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookListBookDTO {

    private UUID id;
    private byte[] picture;
    private String title;
    private String author;

    private String language;
    private BookType type;
    private Timestamp createdAt;
    private String publisher;

    private Float rating;

    boolean isWishListed;
    public UserBookListBookDTO(Book book, User u) throws SQLException {
        this.id = book.getId();
        if(book.getPicture() != null)
            this.picture = book.getPicture().getBytes(1,(int)book.getPicture().length());
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.language = book.getLanguage();
        this.type = book.getType();
        this.createdAt = (Timestamp) createdAt;
        this.publisher = book.getPublisher();
        this.rating = book.getRating();
        if(book.getWishList().contains(u))
        {
            this.isWishListed = true;
        } else
            this.isWishListed = false;

    }
}
