package com.example.licenseebe.repository.books;


import com.example.licenseebe.dto.request.GetBooksByGenreDTO;
import com.example.licenseebe.dto.response.*;
import com.example.licenseebe.helper.BookType;
import com.example.licenseebe.model.Book;
import com.example.licenseebe.model.User;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    public List<UserViewBookCardDataDTO> getPaginatedBooksByGenre(int firstResult, int maxResult, User u) {

        String query = "SELECT NEW com.example.licenseebe.dto.response.UserViewBookCardDataDTO(b) FROM Book b";
        try {
            return entityManager
                    .createQuery(query, UserViewBookCardDataDTO.class)
                    .setFirstResult(firstResult)
                    .setMaxResults(maxResult)
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>(0);
        }
    }

    public List<CarouselBookDTO> getAllBooks(User u) {

        String query = "SELECT NEW com.example.licenseebe.dto.response.CarouselBookDTO(b,:u) FROM Book b";
        try {
            return entityManager
                    .createQuery(query, CarouselBookDTO.class)
                    .setParameter("u", u)
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>(0);
        }
    }


    public List<CarouselBookDTO> getPagedBooks(int firstResult, int maxResult, User u) {

        String query = "SELECT NEW com.example.licenseebe.dto.response.CarouselBookDTO(b,:u) FROM Book b";
        try {
            return entityManager
                    .createQuery(query, CarouselBookDTO.class)
                    .setFirstResult(firstResult)
                    .setMaxResults(maxResult)
                    .setParameter("u", u)
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>(0);
        }
    }

    @Override
    public List<SearchedBookDTO> searchBooks(String searchInput) {
        searchInput = "%" + searchInput + "%";
        String query = "SELECT NEW com.example.licenseebe.dto.response.SearchedBookDTO(b) FROM Book b WHERE b.title LIKE :searchInput ORDER BY b.title";
        try {
            return entityManager
                    .createQuery(query, SearchedBookDTO.class)
                    .setParameter("searchInput", searchInput)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Book> getAllBooks(int firstResult, int maxResult) {
        String query = "SELECT b FROM Book b";
        return entityManager
                .createQuery(query, Book.class)
                .setFirstResult(1)
                .setMaxResults(18)
                .getResultList();
    }

    @Override
    public List<Book> getRecentlyAddedBooks() {
        String query = "SELECT b FROM Book b ORDER BY b.createdAt DESC";
        return entityManager
                .createQuery(query, Book.class)
                .setFirstResult(1)
                .setMaxResults(18)
                .getResultList();
    }

    @Override
    public List<TableAdminBookDTO> getAllAdminBooksByType(BookType type, String order, String isDesc) {
        //SPAISES IST MUI IMPORTANTES
        String query = "SELECT NEW com.example.licenseebe.dto.response.TableAdminBookDTO(b) FROM Book b";
        if (type != BookType.All) {
            query += " WHERE b.type = :type ";
        }
        if (order != null) {
            query += " ORDER BY ";
            if (order.equals("TITLE"))
                query += "b.title ";
            if (order.equals("PUBLISHER"))
                query += "b.publisher ";
            if (order.equals("RATING"))
                query += "b.rating ";
            query += isDesc;
        }

        if (type != BookType.All) {
            return entityManager.createQuery(query, TableAdminBookDTO.class).setParameter("type", type).getResultList();

        } else {
            return entityManager.createQuery(query, TableAdminBookDTO.class).getResultList();
        }
    }

    @Override
    public List<UserViewBookCardDataDTO> getLibraryBooks(BookType type, String userEmail) {
        String query = "SELECT NEW com.example.licenseebe.dto.response.UserViewCartCardDataDTO(b) FROM Book b";
        if (type != BookType.All) {
            query += " WHERE b.type = :type ";
        }
        query += "ORDER BY b.title ASC";

        return entityManager
                .createQuery(query, UserViewBookCardDataDTO.class)
                .setParameter("userEmail", userEmail)
                .getResultList();
    }


    @Override
    public List<Book> getBooksByGenre(BookType type, int firstResult, int maxResult, GetBooksByGenreDTO body) {
        String query = "SELECT b FROM Book b ";
        if (type != BookType.All) {
            query += "WHERE b.type = :type ";
            if (body.getGenre().size() != 0) {

                query += "AND JOIN b.bookGenres g WHERE g.name IN :genreNames ";
                return entityManager
                        .createQuery(query, Book.class)
                        .setParameter("genreNames", body.getGenre())
                        .setParameter("type", type)
                        .setFirstResult(firstResult)
                        .setMaxResults(maxResult)
                        .getResultList();
            }

            return entityManager
                    .createQuery(query, Book.class)
                    .setParameter("type", type)
                    .setFirstResult(firstResult)
                    .setMaxResults(maxResult)
                    .getResultList();
        }

        if (body.getGenre().size() != 0) {
            query += "JOIN b.bookGenres g WHERE g.name IN :genreNames ";
            return entityManager
                    .createQuery(query, Book.class)
                    .setParameter("genreNames", body.getGenre())
                    .setFirstResult(firstResult)
                    .setMaxResults(maxResult)
                    .getResultList();
        }

        return entityManager
                .createQuery(query, Book.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .getResultList();

    }

    @Override
    public Integer getTotalCountFinal(BookType type, GetBooksByGenreDTO body) {

        String query = "SELECT COUNT(b) FROM Book b";
        if (type != BookType.All) {
            if (body.getGenre().size() > 0) {
                query += "WHERE b.type = :type AND";
                query += " JOIN b.bookGenres g WHERE g.name IN :genreNames ";
                return entityManager
                        .createQuery(query)
                        .setParameter("genreNames", body.getGenre())
                        .setParameter("type", body.getType())
                        .getFirstResult();
            } else {
                return entityManager
                        .createQuery(query)
                        .setParameter("type", body.getType())
                        .getFirstResult();
            }
        } else {
            if (body.getGenre().size() > 0) {
                query += " JOIN b.bookGenres g WHERE g.name IN :genreNames ";
                return entityManager
                        .createQuery(query)
                        .setParameter("genreNames", body.getGenre())
                        .getFirstResult();
            }
        }
        return entityManager
                .createQuery(query)
                .getFirstResult();
    }


    @Override
    public List<Book> getBooksByType(BookType type, int firstResult, int maxResult) {
        String query = "SELECT b FROM Book b";
        return entityManager
                .createQuery(query, Book.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .getResultList();
    }

}
