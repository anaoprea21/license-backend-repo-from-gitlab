package com.example.licenseebe.dto.request;

import com.example.licenseebe.helper.BookType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewBookDTO {

    String title;
    String author;
    String language;
    BookType bookType;
    Set<String> bookCategories;
    byte[] picture;
    String publisher;
    String description;
    Float price;
    Integer size;
    String filePath;

}
