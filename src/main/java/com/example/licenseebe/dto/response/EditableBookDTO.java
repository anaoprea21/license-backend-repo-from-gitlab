package com.example.licenseebe.dto.response;

import com.example.licenseebe.helper.BookType;
import com.example.licenseebe.model.Book;
import com.example.licenseebe.model.BookCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditableBookDTO {

    private UUID id;
    String title;
    String author;
    String language;
    BookType bookType;
    byte[] picture;
    String publisher;
    String description;
    Float price;
    Integer size;
    Set<String> bookCategories;
    String filePath;
    Float rating;

    public EditableBookDTO(Book book) throws SQLException {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.language = book.getLanguage();
        this.bookType = book.getType();
        if (book.getPicture() != null)
            this.picture = book.getPicture().getBytes(1, (int) book.getPicture().length());
        this.publisher = book.getPublisher();
        this.description = book.getDescription();
        this.price = book.getPrice();
        this.size = book.getSize();
        this.bookCategories = new HashSet<>();
        if (!book.getBookGenres().isEmpty()) {
            for (BookCategory categ : book.getBookGenres()) {
                this.bookCategories.add(categ.getName());
            }
        }
        this.filePath = book.getFilePath();
        this.rating = book.getRating();
    }
}
