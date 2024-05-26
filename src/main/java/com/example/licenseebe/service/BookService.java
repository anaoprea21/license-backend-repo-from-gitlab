package com.example.licenseebe.service;

import com.example.licenseebe.dto.request.*;
import com.example.licenseebe.dto.response.*;
import com.example.licenseebe.helper.*;
import com.example.licenseebe.model.Book;
import com.example.licenseebe.model.BookCategory;
import com.example.licenseebe.model.Review;
import com.example.licenseebe.model.User;
import com.example.licenseebe.repository.bookCategory.BookCategoryRepository;
import com.example.licenseebe.repository.books.BookRepository;
import com.example.licenseebe.repository.books.ReviewRepository;
import com.example.licenseebe.repository.user.UserRepository;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    final
    TokenService tokenService;
    private final BookRepository bookRepository;
    private final BookCategoryRepository bookCategoryRepository;

    private final UserRepository userRepository;

    private final ReviewRepository reviewRepository;

    @Autowired
    public BookService(HttpStatusHelper httpStatusHelper, BookRepository bookRep, BookCategoryRepository bookCategRep, TokenService tokenService, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRep;
        this.bookCategoryRepository = bookCategRep;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    public Object getBook(HttpServletRequest request, HttpServletResponse response, UUID uuid) throws CustomConflictException {

        Optional<EditableBookDTO> optionalEditableBook = bookRepository.getBookById(uuid);
        if (optionalEditableBook.isPresent()) {
            return optionalEditableBook.get();
        } else {
            throw new CustomConflictException(Conflict.BOOK_NOT_FOUND);
        }
    }

    public Object deleteBook(HttpServletRequest request, HttpServletResponse response, UUID uuid) throws CustomConflictException {

        Optional<Book> deleted = bookRepository.getBookAsBookById(uuid);
        if (deleted.isPresent()) {
            bookRepository.delete(deleted.get());
            return new ResponseEntity<>(Response.SUCCESS, HttpStatus.OK).getBody();
        } else {
            throw new CustomConflictException(Conflict.BOOK_NOT_FOUND);
        }

    }

    public Object editBook(HttpServletRequest request, HttpServletResponse response, EditableBookDTO updatedBook) throws CustomConflictException {
        Optional<Book> updated = bookRepository.getBookAsBookById(updatedBook.getId());
        if (updated.isPresent()) {
            Book editedBook = updated.get();
            editedBook.setType(updatedBook.getBookType());
            editedBook.setAuthor(updatedBook.getAuthor());
            editedBook.setTitle(updatedBook.getTitle());
            editedBook.setLanguage(updatedBook.getLanguage());
            editedBook.setPublisher(updatedBook.getPublisher());
            editedBook.setPrice(updatedBook.getPrice());
            editedBook.setSize(updatedBook.getSize());
            editedBook.setDescription(updatedBook.getDescription());
            editedBook.setPicture(BlobProxy.generateProxy(updatedBook.getPicture()));
            editedBook.setFilePath(updatedBook.getFilePath());

            Set<BookCategory> results = new HashSet<>();
            for (String categName : updatedBook.getBookCategories()) {
                Optional<BookCategory> optionalBookCategory = bookCategoryRepository.getBookCategory(categName);
                if (optionalBookCategory.isPresent()) {
                    results.add(optionalBookCategory.get());
                } else {
                    BookCategory newBookCategory = BookCategory.builder()
                            .name(categName)
                            .build();
                    results.add(newBookCategory);
                    bookCategoryRepository.save(newBookCategory);
                }
            }
            editedBook.setBookGenres(results);

            bookRepository.save(editedBook);

            return new ResponseEntity<>(Response.SUCCESS, HttpStatus.OK).getBody();
        } else {
            throw new CustomConflictException(Conflict.BOOK_NOT_FOUND);
        }
    }

    public Object createNewBook(HttpServletRequest request, HttpServletResponse response, CreateNewBookDTO newBook) throws IOException, CustomConflictException {

        Book newBookToBeInserted = Book.builder()
                .author(newBook.getAuthor())
                .title(newBook.getTitle())
                .language(newBook.getLanguage())
                .picture(BlobProxy.generateProxy(newBook.getPicture()))
                .publisher(newBook.getPublisher())
                .price(newBook.getPrice())
                .type(newBook.getBookType())
                .description(newBook.getDescription())
                .size(newBook.getSize())
                .filePath(newBook.getFilePath())
                .boughtNumber(0)
                .build();

        Set<BookCategory> results = new HashSet<>();
        for (String categName : newBook.getBookCategories()) {
            Optional<BookCategory> optionalBookCategory = bookCategoryRepository.getBookCategory(categName);
            if (optionalBookCategory.isPresent()) {
                results.add(optionalBookCategory.get());
            } else {
                BookCategory newBookCategory = BookCategory.builder()
                        .name(categName)
                        .build();
                results.add(newBookCategory);
                bookCategoryRepository.save(newBookCategory);
            }
        }

        newBookToBeInserted.setBookGenres(results);

        if (newBook.getPicture() != null) {
            File file = new File("image");
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(newBook.getPicture());
                if (file.length() < 2097152) {
                    newBookToBeInserted.setPicture(BlobProxy.generateProxy(newBook.getPicture()));
                } else {
                    throw new CustomConflictException(Conflict.IMAGE_SIZE_IS_TOO_BIG);
                }
            }
        }

        bookRepository.save(newBookToBeInserted);

        return new ResponseEntity<>(Response.SUCCESS, HttpStatus.OK).getBody();
    }

    public Object getPopularBooks(HttpServletRequest request, HttpServletResponse response, GetCarouselBooksDTO getCarouselBooksDTO) throws CustomConflictException, SQLException {

        Optional<User> user = userRepository.getUserByEmail(getCarouselBooksDTO.getEmail());
        int pageSize = getCarouselBooksDTO.getPageSize();
        int pageNumber = getCarouselBooksDTO.getPage();
        int firstResult = pageNumber * pageSize;
        List<Book> booksList = bookRepository.getAllBooks(firstResult, getCarouselBooksDTO.getPageSize());

        if (booksList.isEmpty()) {
            throw new CustomConflictException(Conflict.NO_BOOKS_IN_DB);
        }

        List<CarouselBookDTO> result = new ArrayList<>();
        for (Book b : booksList) {
            CarouselBookDTO adminTableBookDTO = new CarouselBookDTO(b, user.get());
            result.add(adminTableBookDTO);
        }


        return result.stream()
                .sorted(Comparator.comparing(book -> book.getBoughtNumber()))
                .collect(Collectors.toList());
    }

    public Object getCarouselBooks(HttpServletRequest request, HttpServletResponse response, GetCarouselBooksDTO getCarouselBooksDTO) throws CustomConflictException, SQLException {

        Optional<User> user = userRepository.getUserByEmail(getCarouselBooksDTO.getEmail());
        int pageSize = getCarouselBooksDTO.getPageSize();
        int pageNumber = getCarouselBooksDTO.getPage();
        int firstResult = pageNumber * pageSize;
        List<Book> booksList = bookRepository.getAllBooks(firstResult, getCarouselBooksDTO.getPageSize());

        if (booksList.isEmpty()) {
            throw new CustomConflictException(Conflict.NO_BOOKS_IN_DB);
        }
        List<CarouselBookDTO> result = new ArrayList<>();
        for (Book b : booksList) {
            CarouselBookDTO adminTableBookDTO = new CarouselBookDTO(b, user.get());
            result.add(adminTableBookDTO);
        }
        return result;
    }

    public Object getAdminBooks(HttpServletRequest request, HttpServletResponse response, AdminHandleBookTypeDTO adminHandleBookTypeDTO) throws CustomConflictException {

        List<TableAdminBookDTO> result = bookRepository.getAllAdminBooksByType(adminHandleBookTypeDTO.getType(), adminHandleBookTypeDTO.getOrderBy(), adminHandleBookTypeDTO.getIsDesc());
        if (result.isEmpty()) {
            throw new CustomConflictException(Conflict.NO_BOOKS_IN_DB);
        }
        return result;

    }

    public Object searchBooks(String searchInput) throws CustomConflictException {
        List<SearchedBookDTO> books = bookRepository.searchBooks(searchInput);
        if (books.isEmpty()) {
            throw new CustomConflictException(Conflict.NO_BOOK_FOUND);
        }
        return books;
    }

    public Object getBooksByGenre(HttpServletRequest request, HttpServletResponse response, GetBooksByGenreDTO body) throws
            CustomConflictException, SQLException {

        int pageSize = body.getPageSize();
        int pageNumber = body.getPage();
        int firstResult = (pageNumber - 1) * pageSize;

        Optional<User> user = userRepository.getUserByEmail(body.getEmail());
        User u = user.get();
        List<Book> booksList = bookRepository.getBooksByGenre(body.getType(), firstResult, pageSize, body);
        if (booksList.isEmpty()) {
            throw new CustomConflictException(Conflict.NO_BOOKS_IN_DB);
        } else {
            List<UserViewCardDataHeartedDTO> result = new ArrayList<>();
            for (Book b : booksList) {
                UserViewCardDataHeartedDTO adminTableBookDTO = new UserViewCardDataHeartedDTO(b, u);
                result.add(adminTableBookDTO);
            }

            Map<String, Object> totalResult = new HashMap<>();
            totalResult.put("data", result);
            totalResult.put("totalCount", bookRepository.getTotalCount());
            return totalResult;
        }

    }

    public Object getRatings(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, UUID bookId) {
        return bookRepository.getBookRatings(bookId);
    }

    public Object createReview(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, CreateReviewRequestDto createReviewRequestDto) throws CustomConflictException {
        Optional<Book> optionalBook = bookRepository.getBookAsBookById(createReviewRequestDto.getBookId());
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            Optional<User> optionalUser = userRepository.getUserByEmail(createReviewRequestDto.getEmail());
            if (optionalBook.isPresent()) {
                User user = optionalUser.get();
                Review review = Review.builder()
                        .book(book)
                        .text(createReviewRequestDto.getText())
                        .user(user)
                        .rating(createReviewRequestDto.getRating())
                        .build();
                reviewRepository.save(review);
                List<Integer> userRatings = bookRepository.getBookRatingsInteger(createReviewRequestDto.getBookId());
                Integer finalRating = 0;
                for (Integer i : userRatings)
                    finalRating += i;
                book.setRating(Float.valueOf(finalRating / userRatings.size()));
                bookRepository.save(book);
                return new ResponseEntity<>(Response.SUCCESS, HttpStatus.OK).getBody();
            } else throw new CustomConflictException(Conflict.USER_NOT_FOUND);
        } else throw new CustomConflictException(Conflict.BOOK_NOT_FOUND);
    }

    public Object getRecentlyAddedBooks(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String email) throws CustomConflictException, SQLException {
        Optional<User> user = userRepository.getUserByEmail(email);
        List<Book> booksList = bookRepository.getRecentlyAddedBooks();

        if (booksList.isEmpty()) {
            throw new CustomConflictException(Conflict.NO_BOOKS_IN_DB);
        }
        List<CarouselBookDTO> result = new ArrayList<>();
        for (Book b : booksList) {
            CarouselBookDTO adminTableBookDTO = new CarouselBookDTO(b, user.get());
            result.add(adminTableBookDTO);
        }
        return result;
    }

    public Object addUserLastBook(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AddUserLastBookRequestDto addUserLastBookRequestDto) throws CustomConflictException {
        Optional<User> user = userRepository.getUserByEmail(addUserLastBookRequestDto.getEmail());
        User u = user.get();
        u.setLastBook(addUserLastBookRequestDto.getBookId());
        userRepository.save(u);
        return new ResponseEntity<>(Response.SUCCESS, HttpStatus.OK).getBody();
    }

    public Object getUserLastBook(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String email) throws CustomConflictException, SQLException {
        Optional<User> user = userRepository.getUserByEmail(email);
        User u = user.get();
        Optional<Book> book = bookRepository.getBookAsBookById(u.getLastBook());
        Book b = book.get();
        LastReadBook lastReadBook = new LastReadBook(b);
        return lastReadBook;
    }


}