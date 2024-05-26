package com.example.licenseebe.dto.response;

import com.example.licenseebe.model.Book;
import com.example.licenseebe.model.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LastReadBook {
    UUID bookId;
    byte[] picture;

    public LastReadBook(Book book) throws SQLException {
        this.bookId = book.getId();
        if(book.getPicture() != null)
            this.picture = book.getPicture().getBytes(1,(int)book.getPicture().length());
    }
}
