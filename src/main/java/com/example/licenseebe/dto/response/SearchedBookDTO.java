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
public class SearchedBookDTO {
    UUID id;
    String title;

    public SearchedBookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
    }
}
