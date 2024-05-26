package com.example.licenseebe.dto.response;

import com.example.licenseebe.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksNamesListDTO {

    UUID uuid;
    String title;

    public BooksNamesListDTO(Book book) throws SQLException {
        this.uuid = book.getId();
        this.title = book.getTitle();
    }

}
