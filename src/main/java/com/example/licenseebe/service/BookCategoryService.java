package com.example.licenseebe.service;

import com.example.licenseebe.helper.*;
import com.example.licenseebe.repository.bookCategory.BookCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class BookCategoryService {
    final
    TokenService tokenService;

    private final BookCategoryRepository bookCategoryRepository;

    @Autowired
    public BookCategoryService(HttpStatusHelper httpStatusHelper, BookCategoryRepository bookCategoryRepository, TokenService tokenService) {
        this.bookCategoryRepository = bookCategoryRepository;
        this.tokenService = tokenService;
    }


    public Object getAllBookCategories(HttpServletRequest request, HttpServletResponse response) throws CustomConflictException {
            List<String> allBookCategoriesList = bookCategoryRepository.getAllBookCategories();
            if (allBookCategoriesList.isEmpty()) {
                throw new CustomConflictException(Conflict.NO_BOOK_CATEGORIES_IN_DB);
            } else {
                return allBookCategoriesList;
            }
    }

}
