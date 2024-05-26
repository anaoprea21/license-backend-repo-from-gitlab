package com.example.licenseebe.repository.user;

import com.example.licenseebe.dto.response.GetBookRatingsByUserResponseDto;
import com.example.licenseebe.dto.response.UserViewBookCardDataDTO;
import com.example.licenseebe.model.Review;
import com.example.licenseebe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> getUserByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> getUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> getUserById(UUID id);

    @Query("SELECT u.username FROM User u WHERE u.email = :email")
    Optional<String> getUsername(String email);

    @Query("SELECT u FROM User u WHERE u.forgotPasswordCode = :code")
    Optional<User> getUserByForgotPasswordCode(String code);

    @Query("SELECT NEW com.example.licenseebe.dto.response.GetBookRatingsByUserResponseDto(r) FROM Review r  WHERE r.user.username = :username")
    List<GetBookRatingsByUserResponseDto> getUserRatings(String username);

    @Query("SELECT r FROM Review r  WHERE r.id = :ratingId")
    Optional<Review> getRatingById(UUID ratingId);

}