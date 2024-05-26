package com.example.licenseebe.dto.request;

import com.example.licenseebe.helper.BookType;
import com.example.licenseebe.model.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBooksByGenreDTO {

    String email;
    int page;
    int pageSize;
    List<String> genre;
    BookType type;

}
