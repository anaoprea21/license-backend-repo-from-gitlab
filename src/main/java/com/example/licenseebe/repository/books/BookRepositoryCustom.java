package com.example.licenseebe.repository.books;

import com.example.licenseebe.dto.request.GetBooksByGenreDTO;
import com.example.licenseebe.dto.response.*;
import com.example.licenseebe.helper.BookType;
import com.example.licenseebe.model.Book;
import com.example.licenseebe.model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepositoryCustom {

    public List<CarouselBookDTO> getPagedBooks(int firstResult, int maxResult, User u);

    public List<CarouselBookDTO> getAllBooks(User u);

    public List<UserViewBookCardDataDTO> getPaginatedBooksByGenre(int firstResult, int maxResult, User u);
    List<UserViewBookCardDataDTO> getLibraryBooks(BookType type, String userEmail);

    List<Book> getBooksByGenre(BookType type, int firstResult, int maxResult, GetBooksByGenreDTO body);
    Integer getTotalCountFinal(BookType type, GetBooksByGenreDTO body);

    List<Book> getBooksByType(BookType type, int firstResult, int maxResult);

    List<SearchedBookDTO> searchBooks(String searchInput);

    List<Book> getAllBooks(int firstResult, int maxResult);

    List<Book> getRecentlyAddedBooks();

    public List<TableAdminBookDTO> getAllAdminBooksByType(BookType type, String order, String isDesc);

}
