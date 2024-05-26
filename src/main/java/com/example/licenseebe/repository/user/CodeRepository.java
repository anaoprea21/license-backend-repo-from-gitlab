package com.example.licenseebe.repository.user;

import com.example.licenseebe.model.User2FACode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CodeRepository extends JpaRepository<User2FACode,Integer> {
    @Query("SELECT c FROM User2FACode c WHERE c.user.id = :id")
    Optional<User2FACode> getCodeByUserId(UUID id);
}
