package com.example.licenseebe.controller;

import com.example.licenseebe.dto.request.*;
import com.example.licenseebe.dto.response.GetUserLibraryDto;
import com.example.licenseebe.helper.HttpStatusHelper;
import com.example.licenseebe.service.BookService;
import com.example.licenseebe.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.example.licenseebe.helper.HttpStatusHelper.success;

@RestController
@Log4j2
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private final BookService bookService;
    private final HttpStatusHelper httpStatusHelper;

    @Autowired
    public UserController(UserService userService, BookService bookService, HttpStatusHelper httpStatusHelper) {
        this.bookService = bookService;
        this.userService = userService;
        this.httpStatusHelper = httpStatusHelper;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/get-account-info/{email}")
    public ResponseEntity<Object> getAccountInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String email) {
        try {
            return success("response", userService.getAccountInfo(httpServletRequest, httpServletResponse, email));
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
    @PutMapping("/change-account-2fa")
    public ResponseEntity<Object> changeTwoFactorStatus(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String email) {
        try {
            return success("response", userService.changeTwoFactorStatus(httpServletRequest, httpServletResponse, email));
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
    @PostMapping("/get-user-last-book")
    public ResponseEntity<Object> getUserLastBook(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam String email) {
        try {
            return success("response", bookService.getUserLastBook(httpServletRequest, httpServletResponse, email));
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
    @PostMapping("/add-user-last-book")
    public ResponseEntity<Object> addUserLastBook(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody AddUserLastBookRequestDto addUserLastBookRequestDto) {
        try {
            return success("response", bookService.addUserLastBook(httpServletRequest, httpServletResponse, addUserLastBookRequestDto));
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
    @PutMapping("/change-password")
    public ResponseEntity<Object> resetPassword(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody ResetPasswordDTO resetPasswordDTO) {
        try {
            return success("response", userService.resetPassword(httpServletRequest, httpServletResponse, resetPasswordDTO));
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
    @PostMapping("/get-cart-books/{email}")
    public ResponseEntity<Object> getCartBooks(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String email) {
        try {
            return success("response", userService.getCartBooks(httpServletRequest, httpServletResponse, email));
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
    @PostMapping("/get-library-books")
    public ResponseEntity<Object> getLibraryBooks(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody GetUserLibraryRequestDto getUserLibraryRequestDto) {
        try {
            return success("response", userService.getLibraryBooks(httpServletRequest, httpServletResponse, getUserLibraryRequestDto));
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
    @PostMapping("/get-wishlist-books/{email}")
    public ResponseEntity<Object> getWishlistBooks(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String email) {
        try {
            return success("response", userService.getWishlistBooks(httpServletRequest, httpServletResponse, email));
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
    @GetMapping("/get-library-books-names/{email}")
    public ResponseEntity<Object> getLibraryBooksNames(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String email) {
        try {
            return success("response", userService.getLibraryBooksNames(httpServletRequest, httpServletResponse, email));
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
    @PutMapping("/add-book-to-library")
    public ResponseEntity<Object> addBookToLibrary(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody HandleUserBookDTO handleUserBookDTO) {
        try {
            return success("response", userService.addBookToLibrary(httpServletRequest, httpServletResponse, handleUserBookDTO));
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
    @PutMapping("/add-book-to-wishlist")
    public ResponseEntity<Object> addBookToWishlist(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody HandleUserBookDTO handleUserBookDTO) {
        try {
            return success("response", userService.addBookToWishlist(httpServletRequest, httpServletResponse, handleUserBookDTO));
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
    @PutMapping("/remove-book-from-wishlist")
    public ResponseEntity<Object> removeBookFromWishlist(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody HandleUserBookDTO handleUserBookDTO) {
        try {
            return success("response", userService.removeBookFromWishlist(httpServletRequest, httpServletResponse, handleUserBookDTO));
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
    @PutMapping("/remove-book-from-library")
    public ResponseEntity<Object> removeBookFromLibrary(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody HandleUserBookDTO handleUserBookDTO) {
        try {
            return success("response", userService.removeBookFromLibrary(httpServletRequest, httpServletResponse, handleUserBookDTO));
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
    @PutMapping("/add-book-to-cart")
    public ResponseEntity<Object> addBookToCart(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody HandleUserBookDTO handleUserBookDTO) {
        try {
            return success("response", userService.addBookToCart(httpServletRequest, httpServletResponse, handleUserBookDTO));
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
    @PutMapping("/remove-book-from-cart")
    public ResponseEntity<Object> removeBookFromCart(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody HandleUserBookDTO handleUserBookDTO) {
        try {
            return success("response", userService.removeBookFromCart(httpServletRequest, httpServletResponse, handleUserBookDTO));
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
    @GetMapping("/get-user-recommendations/{email}")
    public ResponseEntity<Object> getUserRecommendations(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String email) {
        try {
            return success("response", userService.getUserRecommendations(email));
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
    @GetMapping("/get-user-ratings/{username}")
    public ResponseEntity<Object> getRatingsByUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String username) {
        try {
            return success("response", userService.getRatingByUser(httpServletRequest, httpServletResponse, username));
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
    @GetMapping("/get-username/{email}")
    public ResponseEntity<Object> getUsername(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String email) {
        try {
            return success("response", userService.getUsername(httpServletRequest, httpServletResponse, email));
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
    @DeleteMapping("/delete-rating/{ratingId}")
    public ResponseEntity<Object> deleteRating(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable UUID ratingId) {
        try {
            return success("response", userService.deleteBookReview(httpServletRequest, httpServletResponse, ratingId));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }
    }
}
