package com.example.licenseebe.dto.response;

import com.example.licenseebe.helper.BookType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserLibraryDto {
    String title;
    String author;
    String language;
    BookType bookType;
    Set<String> bookCategories;
    byte[] picture;
    String publisher;
    String description;
    Integer size;
    String filePath;
}
