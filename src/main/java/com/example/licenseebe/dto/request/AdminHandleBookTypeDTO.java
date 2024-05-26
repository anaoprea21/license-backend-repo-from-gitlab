package com.example.licenseebe.dto.request;

import com.example.licenseebe.helper.BookType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminHandleBookTypeDTO {
    BookType type;
    String orderBy;
    String isDesc;
}
