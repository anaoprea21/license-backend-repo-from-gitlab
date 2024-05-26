package com.example.licenseebe.repository.bookCategory;

import com.example.licenseebe.dto.response.Category;
import com.example.licenseebe.dto.response.EditableBookDTO;
import com.example.licenseebe.model.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Integer>, BookCategoryRepositoryCustom {

    @Query("SELECT bc FROM BookCategory bc WHERE bc.name = :name")
    Optional<BookCategory> getBookCategory(String name);
}
