package com.example.licenseebe.dto.response;

import com.example.licenseebe.model.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCategoryDto {
    String categoryName;

    public GetCategoryDto(BookCategory bc){
        this.categoryName = bc.getName();
    }
}
