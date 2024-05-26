package com.example.licenseebe.dto.request;

import com.example.licenseebe.helper.BookType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCarouselBooksDTO {

    String email;
    int page;
    int pageSize;
}
