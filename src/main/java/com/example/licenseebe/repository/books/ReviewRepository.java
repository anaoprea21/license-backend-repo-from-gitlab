package com.example.licenseebe.repository.books;

import com.example.licenseebe.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
