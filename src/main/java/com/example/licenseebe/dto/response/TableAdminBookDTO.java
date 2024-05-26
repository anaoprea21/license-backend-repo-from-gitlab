package com.example.licenseebe.dto.response;

import com.example.licenseebe.helper.BookType;
import com.example.licenseebe.model.Book;
import com.example.licenseebe.model.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableAdminBookDTO {

    private UUID id;
    private byte[] picture;
    private String title;
    private String author;
    private BookType type;
    private String publisher;

    private Float rating;

    private List<String> genres;


    public TableAdminBookDTO(Book book) throws SQLException {
        this.id = book.getId();
        if (book.getPicture() != null)
            this.picture = book.getPicture().getBytes(1, (int) book.getPicture().length());
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.type = book.getType();
        this.publisher = book.getPublisher();
        this.rating = book.getRating();
        this.genres = new ArrayList<>();
        for(BookCategory genre:book.getBookGenres()){
            genres.add(genre.getName());
        }
    }
}
