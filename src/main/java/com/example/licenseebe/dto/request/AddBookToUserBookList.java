package com.example.licenseebe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddBookToUserBookList {

    UUID userId;
    UUID bookId;
}
