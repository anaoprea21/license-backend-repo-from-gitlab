package com.example.licenseebe.dto.request;

import com.example.licenseebe.helper.BookType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagableGenericDto {
    Integer pageSize;

    Integer pageNumber;

    String bookGenre;

    String accessToken;

}
