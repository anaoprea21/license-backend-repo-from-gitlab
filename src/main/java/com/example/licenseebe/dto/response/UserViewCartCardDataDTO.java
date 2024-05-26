package com.example.licenseebe.dto.response;

import com.example.licenseebe.helper.BookType;
import com.example.licenseebe.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserViewCartCardDataDTO {
    private UUID id;
    String title;
    Float price;
    BookType bookType;
    byte[] picture;
    String author;
    Float rating;

    public UserViewCartCardDataDTO(Book book) throws SQLException {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.bookType = book.getType();
        if(book.getPicture() != null)
            this.picture = book.getPicture().getBytes(1,(int)book.getPicture().length());
        this.price = book.getPrice();
        this.rating = book.getRating();
    }
}
