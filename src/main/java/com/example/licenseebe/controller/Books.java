package com.example.licenseebe.controller;

import com.example.licenseebe.dto.request.*;
import com.example.licenseebe.dto.response.EditableBookDTO;
import com.example.licenseebe.helper.HttpStatusHelper;
import com.example.licenseebe.service.BookCategoryService;
import com.example.licenseebe.service.BookService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.util.*;

import static com.example.licenseebe.helper.HttpStatusHelper.success;

@RestController
@Log4j2
@CrossOrigin
@RequestMapping("/book")
public class Books {

    private final BookService bookService;
    private final BookCategoryService bookCategoryService;
    private final HttpStatusHelper httpStatusHelper;


    @Autowired
    public Books(BookService bookService, BookCategoryService bookCategoryService, HttpStatusHelper httpStatusHelper) {
        this.bookService = bookService;
        this.bookCategoryService = bookCategoryService;
        this.httpStatusHelper = httpStatusHelper;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 404, message = "BOOK_NOT_FOUND"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/get-book/{uuid}")
    public ResponseEntity<Object> getBook(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable UUID uuid) {
        try {
            return success("response", bookService.getBook(httpServletRequest, httpServletResponse, uuid));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/add-book")
    public ResponseEntity<Object> createNewBook(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody CreateNewBookDTO newBook) {
        try {
            return success("response", bookService.createNewBook(httpServletRequest, httpServletResponse, newBook));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }

    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 404, message = "BOOK_NOT_FOUND"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PutMapping("/edit-book")
    public ResponseEntity<Object> editBook(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody EditableBookDTO updatedBook) {
        try {
            return success("response", bookService.editBook(httpServletRequest, httpServletResponse, updatedBook));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 404, message = "BOOK_NOT_FOUND"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @DeleteMapping("/delete-book/{uuid}")
    public ResponseEntity<Object> deleteBook(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable UUID uuid) {
        try {
            return success("response", bookService.deleteBook(httpServletRequest, httpServletResponse, uuid));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }

    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/get-book-categories")
    public ResponseEntity<Object> getAllBookCategories(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            return success("response", bookCategoryService.getAllBookCategories(httpServletRequest, httpServletResponse));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }

    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/search-books/{searchString}")
    public ResponseEntity<Object> searchBook(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String searchString) {
        try {
            return success("response", bookService.searchBooks(searchString));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }

    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/get-all-admin-books")
    public ResponseEntity<Object> getAllAdminBooks(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody AdminHandleBookTypeDTO adminHandleBookTypeDTO) {
        try {
            return success("response", bookService.getAdminBooks(httpServletRequest, httpServletResponse, adminHandleBookTypeDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/get-carousel-books")
    public ResponseEntity<Object> getCarouselBooks(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody GetCarouselBooksDTO getCarouselBooksDTO) {
        try {
            return success("response", bookService.getCarouselBooks(httpServletRequest, httpServletResponse, getCarouselBooksDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/get-popular-books")
    public ResponseEntity<Object> getPopularBooks(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody GetCarouselBooksDTO getCarouselBooksDTO) {
        try {
            return success("response", bookService.getPopularBooks(httpServletRequest, httpServletResponse, getCarouselBooksDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/get-books-by-genre")
    public ResponseEntity<Object> getBooksByGenre(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody GetBooksByGenreDTO body) {
        try {
            return success("response", bookService.getBooksByGenre(httpServletRequest, httpServletResponse, body));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/get-book-ratings/{bookId}")
    public ResponseEntity<Object> getRatings(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable UUID bookId) {
        try {
            return success("response", bookService.getRatings(httpServletRequest, httpServletResponse, bookId));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/add-book-review")
    public ResponseEntity<Object> addBookReview(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody CreateReviewRequestDto createReviewRequestDto) {
        try {
            return success("response", bookService.createReview(httpServletRequest, httpServletResponse, createReviewRequestDto));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/get-recently-added")
    public ResponseEntity<Object> getRecentlyAdded(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam String email) {
        try {
            return success("response", bookService.getRecentlyAddedBooks(httpServletRequest, httpServletResponse,email));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }
    }
}
