package com.example.licenseebe.service;

import com.example.licenseebe.dto.request.GetUserLibraryRequestDto;
import com.example.licenseebe.dto.request.HandleUserBookDTO;
import com.example.licenseebe.dto.request.ResetPasswordDTO;
import com.example.licenseebe.dto.response.AccountInfoDTO;
import com.example.licenseebe.dto.response.BooksNamesListDTO;
import com.example.licenseebe.dto.response.UserViewBookCardDataDTO;
import com.example.licenseebe.dto.response.UserViewCartCardDataDTO;
import com.example.licenseebe.helper.*;
import com.example.licenseebe.model.Book;
import com.example.licenseebe.model.Review;
import com.example.licenseebe.model.User;
import com.example.licenseebe.repository.bookCategory.UserGenresRepository;
import com.example.licenseebe.repository.books.BookRepository;
import com.example.licenseebe.repository.books.ReviewRepository;
import com.example.licenseebe.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;


    final
    TokenService tokenService;

    final
    UserGenresRepository userGenresRepository;

    @Autowired
    public UserService(UserRepository userRepository, BookRepository bookRep, TokenService tokenService, UserGenresRepository userGeneresRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRep;
        this.tokenService = tokenService;
        this.userGenresRepository = userGeneresRepository;
        this.reviewRepository = reviewRepository;
    }

    public Object getAccountInfo(HttpServletRequest request, HttpServletResponse response, String email) throws CustomConflictException {
        Optional<User> optionalUser = userRepository.getUserByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            AccountInfoDTO accInfo = new AccountInfoDTO(user.getUsername(), user.getEmail(), user.getReviews().size(), user.isTwoFactorStatus());
            return accInfo;
        } else {
            throw new CustomConflictException(Conflict.NO_USER_FOUND);
        }
    }

    public Object changeTwoFactorStatus(HttpServletRequest request, HttpServletResponse response, String email) throws CustomConflictException {
        Optional<User> optionalUser = userRepository.getUserByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setTwoFactorStatus(!user.isTwoFactorStatus());
            userRepository.save(user);
            return !user.isTwoFactorStatus();
        } else {
            throw new CustomConflictException(Conflict.NO_USER_FOUND);
        }
    }

    public Object resetPassword(HttpServletRequest request, HttpServletResponse response, ResetPasswordDTO resetPasswordDTO) throws CustomConflictException {
        Optional<User> optionalUser = userRepository.getUserByEmail(resetPasswordDTO.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(resetPasswordDTO.getPassword());
            userRepository.save(user);
            return new ResponseEntity<>(Response.SUCCESS, HttpStatus.OK).getBody();
        } else {
            throw new CustomConflictException(Conflict.NO_USER_FOUND);
        }
    }

    public Object addBookToCart(HttpServletRequest request, HttpServletResponse response, HandleUserBookDTO handleUserBookDTO) throws CustomConflictException {
        Optional<User> optionalUser = userRepository.getUserByEmail(handleUserBookDTO.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Book> optionalBook = bookRepository.getBookAsBookById(handleUserBookDTO.getBookIds().get(0));
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();

                book.getCart().add(user);
                user.getCart().add(book);

                if (handleUserBookDTO.getAction().equals(UserBookHandlingActions.REMOVE_TO_WISHLIST)) {
                    book.getWishList().remove(user);
                    user.getWishList().remove(book);
                }

                userRepository.save(user);
                bookRepository.save(book);
                return new ResponseEntity<>(Response.SUCCESS, HttpStatus.OK).getBody();
            } else {
                throw new CustomConflictException(Conflict.NO_BOOK_FOUND);
            }
        } else {
            throw new CustomConflictException(Conflict.NO_USER_FOUND);
        }
    }

    public Object addBookToLibrary(HttpServletRequest request, HttpServletResponse response, HandleUserBookDTO handleUserBookDTO) throws CustomConflictException {

        Optional<User> user = userRepository.getUserByEmail(handleUserBookDTO.getEmail());
        if (user.isPresent()) {
            User editedUser = user.get();
            List<UUID> books = handleUserBookDTO.getBookIds();
            for (UUID id : books) {
                Optional<Book> optionalBook = bookRepository.getBookAsBookById(id);
                if (optionalBook.isPresent()) {
                    Book book = optionalBook.get();

                    book.getUserList().add(editedUser);
                    editedUser.getLibrary().add(book);

                    book.getCart().remove(editedUser);
                    editedUser.getCart().remove(book);

                    Integer boughtNumber = book.getBoughtNumber() + 1;
                    book.setBoughtNumber(boughtNumber);

                    bookRepository.save(book);
                }
            }
            userRepository.save(editedUser);

            return new ResponseEntity<>(Response.SUCCESS, HttpStatus.OK).getBody();
        } else {
            throw new CustomConflictException(Conflict.NO_BOOK_FOUND);
        }
    }

    public Object removeBookFromLibrary(HttpServletRequest request, HttpServletResponse response, HandleUserBookDTO handleUserBookDTO) throws CustomConflictException {
        Optional<User> user = userRepository.getUserByEmail(handleUserBookDTO.getEmail());
        if (user.isPresent()) {
            User editedUser = user.get();

            Optional<Book> optionalBook = bookRepository.getBookAsBookById(handleUserBookDTO.getBookIds().get(0));
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();

                editedUser.getLibrary().remove(book);
                book.getUserList().remove(editedUser);

                bookRepository.save(book);
                userRepository.save(editedUser);
                return new ResponseEntity<>(Response.SUCCESS, HttpStatus.OK).getBody();
            } else {
                throw new CustomConflictException(Conflict.NO_BOOK_FOUND);
            }
        } else {
            throw new CustomConflictException(Conflict.NO_USER_FOUND);
        }
    }

    public Object addBookToWishlist(HttpServletRequest request, HttpServletResponse response, HandleUserBookDTO handleUserBookDTO) throws CustomConflictException {
        Optional<User> user = userRepository.getUserByEmail(handleUserBookDTO.getEmail());
        if (user.isPresent()) {
            User editedUser = user.get();
            Optional<Book> optionalBook = bookRepository.getBookAsBookById(handleUserBookDTO.getBookIds().get(0));
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();

                editedUser.getWishList().add(book);
                book.getWishList().add(editedUser);

                bookRepository.save(book);
                userRepository.save(editedUser);
                return new ResponseEntity<>(Response.SUCCESS, HttpStatus.OK).getBody();
            } else {
                throw new CustomConflictException(Conflict.NO_BOOK_FOUND);
            }
        } else {
            throw new CustomConflictException(Conflict.NO_USER_FOUND);
        }
    }

    public Object removeBookFromWishlist(HttpServletRequest request, HttpServletResponse response, HandleUserBookDTO handleUserBookDTO) throws CustomConflictException {
        Optional<User> u = userRepository.getUserByEmail(handleUserBookDTO.getEmail());
        User user = u.get();


        Optional<Book> optionalBook = bookRepository.getBookAsBookById(handleUserBookDTO.getBookIds().get(0));
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            user.getWishList().remove(book);
            book.getWishList().remove(user);

            bookRepository.save(book);
            userRepository.save(user);
            return new ResponseEntity<>(Response.SUCCESS, HttpStatus.OK).getBody();
        } else {
            throw new CustomConflictException(Conflict.NO_BOOK_FOUND);
        }


    }

    public Object removeBookFromCart(HttpServletRequest request, HttpServletResponse response, HandleUserBookDTO handleUserBookDTO) throws CustomConflictException {

        Optional<User> user = userRepository.getUserByEmail(handleUserBookDTO.getEmail());
        if (user.isPresent()) {
            User editedUser = user.get();
            Optional<Book> optionalBook = bookRepository.getBookAsBookById(handleUserBookDTO.getBookIds().get(0));
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();

                editedUser.getCart().remove(book);
                book.getCart().remove(editedUser);

                if (handleUserBookDTO.getAction().equals(UserBookHandlingActions.MOVE_TO_WISHLIST)) {
                    editedUser.getWishList().add(book);
                    book.getWishList().add(editedUser);
                }

                bookRepository.save(book);
                userRepository.save(editedUser);
                return new ResponseEntity<>(Response.SUCCESS, HttpStatus.OK).getBody();
            } else {
                throw new CustomConflictException(Conflict.NO_BOOK_FOUND);
            }
        } else {
            throw new CustomConflictException(Conflict.NO_USER_FOUND);
        }
    }

    public Object getLibraryBooksNames(HttpServletRequest request, HttpServletResponse response, String email) throws CustomConflictException, SQLException {

        Optional<User> user = userRepository.getUserByEmail(email);
        if (user.isPresent()) {
            Set<Book> books = user.get().getLibrary();
            if (books.isEmpty()) {
                throw new CustomConflictException(Conflict.NO_BOOKS_IN_LIBRARY);
            } else {
                List<BooksNamesListDTO> libraryBooks = new ArrayList<>();
                for (Book b : books) {
                    libraryBooks.add(new BooksNamesListDTO(b));
                }
                return libraryBooks;
            }
        } else {
            throw new CustomConflictException(Conflict.NO_USER_FOUND);
        }
    }

    public Object getWishlistBooks(HttpServletRequest request, HttpServletResponse response, String email) throws CustomConflictException, SQLException {

        Optional<User> user = userRepository.getUserByEmail(email);
        if (user.isPresent()) {
            Set<Book> books = user.get().getWishList();
            if (books.isEmpty()) {
                throw new CustomConflictException(Conflict.NO_BOOKS_IN_WISHLIST);
            } else {
                List<UserViewBookCardDataDTO> wishListBooks = new ArrayList<>();
                for (Book b : books) {
                    wishListBooks.add(new UserViewBookCardDataDTO(b));
                }
                return wishListBooks;
            }
        } else {
            throw new CustomConflictException(Conflict.NO_USER_FOUND);
        }
    }

    public Object getLibraryBooks(HttpServletRequest request, HttpServletResponse response, GetUserLibraryRequestDto getUserLibraryRequestDto) throws CustomConflictException, SQLException {

        Optional<User> u = userRepository.getUserByEmail(getUserLibraryRequestDto.getEmail());
        User user = u.get();
        Set<Book> books = user.getLibrary();
        if (books.isEmpty()) {
            throw new CustomConflictException(Conflict.NO_BOOKS_IN_LIBRARY);
        } else {
            List<UserViewBookCardDataDTO> libraryBooks = new ArrayList<>();
            for (Book b : books) {
                if (b.getType().equals(getUserLibraryRequestDto.getBookType()))
                    libraryBooks.add(new UserViewBookCardDataDTO(b));
            }

            return libraryBooks.stream()
                    .sorted(Comparator.comparing(book -> book.getTitle()))
                    .collect(Collectors.toList());
        }
    }

    public Object getCartBooks(HttpServletRequest request, HttpServletResponse response, String email) throws CustomConflictException, SQLException {

        Optional<User> user = userRepository.getUserByEmail(email);
        if (user.isPresent()) {
            Set<Book> books = user.get().getCart();
            if (books.isEmpty()) {
                throw new CustomConflictException(Conflict.NO_BOOKS_IN_CART);
            } else {

                List<UserViewCartCardDataDTO> cartBooks = new ArrayList<>();
                for (Book b : books) {
                    cartBooks.add(new UserViewCartCardDataDTO(b));
                }
                return cartBooks;
            }
        } else {
            throw new CustomConflictException(Conflict.NO_USER_FOUND);
        }
    }

    public Object getUserRecommendations(String email) throws CustomConflictException {
        Optional<User> userOptional = userRepository.getUserByEmail(email);
        User user = userOptional.get();
        List<String> userCategories = userGenresRepository.getUserCategories(user.getId());
        Map<User, Integer> usersWithSameCategoriesCounter = new HashMap<>();
        for (String category : userCategories) {
            List<User> usersWithSimilarCategories = userGenresRepository.getUserWithSameCategories(category);
            for (User u : usersWithSimilarCategories) {
                int count = usersWithSameCategoriesCounter.getOrDefault(u, 1);
                if (usersWithSameCategoriesCounter.containsKey(u)) {
                    usersWithSameCategoriesCounter.put(u, count + 1);
                } else {
                    usersWithSameCategoriesCounter.put(u, count);
                }
            }
        }
        usersWithSameCategoriesCounter = usersWithSameCategoriesCounter.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> null,
                        LinkedHashMap::new));
        List<User> result = new ArrayList<>();
        int counter = 0;
        for (Map.Entry<User, Integer> userIntegerEntry : usersWithSameCategoriesCounter.entrySet()) {
            if (counter == 12)
                break;
            result.add(userIntegerEntry.getKey());
            counter++;
        }

        return result;
    }

    public Object getUsername(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String email) {
        return userRepository.getUsername(email);
    }

    public Object getRatingByUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String username) {
        return userRepository.getUserRatings(username);
    }

    public Object deleteBookReview(HttpServletRequest request, HttpServletResponse response, UUID uuid) throws CustomConflictException {

        Optional<Review> deleted = userRepository.getRatingById(uuid);
        if (deleted.isPresent()) {
            reviewRepository.delete(deleted.get());
            return new ResponseEntity<>(Response.SUCCESS, HttpStatus.OK).getBody();
        } else {
            throw new CustomConflictException(Conflict.REVIEW_NOT_FOUND);
        }

    }
}
