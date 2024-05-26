package com.example.licenseebe.repository.bookCategory;

import com.example.licenseebe.model.BookCategory;
import com.example.licenseebe.model.User;
import com.example.licenseebe.model.UserGeneres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserGenresRepository extends JpaRepository<UserGeneres, Integer> {
    @Query("SELECT ug.bookGenre FROM UserGeneres ug WHERE ug.user.id = :id")
    List<String> getUserCategories(UUID id);

    @Query("SELECT ug.user FROM UserGeneres ug WHERE ug.bookGenre = :category")
    List<User> getUserWithSameCategories(String category);
}
