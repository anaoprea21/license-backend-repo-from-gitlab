package com.example.licenseebe.model;
import com.example.licenseebe.helper.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @org.hibernate.annotations.Type(type = "uuid-char")
    @Column(length = 36)
    private UUID id;

    private String username;
    private String email;
    private String password;

    private boolean twoFactorStatus;
    private String profilePicture;

    private UUID lastBook;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String forgotPasswordCode;
    //relations

    @OneToMany(mappedBy = "user")
    private Set<CalendarBookDate> dates;

    @ManyToMany(mappedBy = "userList")
    @JsonIgnore
    private Set<Book> library;

    @ManyToMany(mappedBy = "wishList")
    @JsonIgnore
    private Set<Book> wishList;

    @ManyToMany(mappedBy = "cart")
    @JsonIgnore
    private Set<Book> cart;

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews;


}
