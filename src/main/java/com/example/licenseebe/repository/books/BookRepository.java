package com.example.licenseebe.repository.books;

import com.example.licenseebe.dto.response.*;
import com.example.licenseebe.helper.BookType;
import com.example.licenseebe.model.Book;
import com.example.licenseebe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>, BookRepositoryCustom, PagingAndSortingRepository<Book,Integer> {

    @Query("SELECT NEW com.example.licenseebe.dto.response.EditableBookDTO(b) FROM Book b WHERE b.id = :uuid")
    Optional<EditableBookDTO> getBookById(UUID uuid);

    @Query("SELECT NEW com.example.licenseebe.dto.response.UserViewBookCardDataDTO(b) FROM Book b WHERE b.type = :type")
    List<UserViewBookCardDataDTO> getAllBooksByGenre(BookType type);

    @Query("SELECT NEW com.example.licenseebe.dto.response.TableAdminBookDTO(b) FROM Book b")
    List<TableAdminBookDTO> getAllAdminBooks();

    @Query("SELECT b FROM Book b WHERE b.id = :uuid")
    Optional<Book> getBookAsBookById(UUID uuid);

    @Query("SELECT b FROM Book b JOIN User u WHERE b.title LIKE :searchString AND u.id = :userId")
    List<Book> searchBooksByLibrary(String searchString, UUID userId);

    @Query("SELECT NEW com.example.licenseebe.dto.response.GetBookRatingsResponseDto(r) FROM Review r  WHERE r.book.id = :bookId")
    List<GetBookRatingsResponseDto> getBookRatings(UUID bookId);

    @Query("SELECT r.rating FROM Review r  WHERE r.book.id = :bookId")
    List<Integer> getBookRatingsInteger(UUID bookId);

    @Query("SELECT COUNT(b) FROM Book b")
    Object getTotalCount();
}
