package com.example.licenseebe.model;

import com.example.licenseebe.helper.BookType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @org.hibernate.annotations.Type(type = "uuid-char")
    @Column(length = 36)
    private UUID id;

    private String title;
    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer boughtNumber;
    private String publisher;
    private Float price;
    private Float rating;
    @Column(columnDefinition = "TEXT")
    private String filePath;
    @Lob
    private Blob picture;

    @Enumerated(EnumType.STRING)
    private BookType type;
    private Integer size;

    private String language;

    @CreationTimestamp
    private Timestamp createdAt;

    //relations

    @ManyToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "user_book", joinColumns = @JoinColumn(name = "library_user_id"), inverseJoinColumns = @JoinColumn(name = "library_book_id"))
    private Set<User> userList;

    @ManyToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "wish_list", joinColumns = @JoinColumn(name = "wishlist_user_id"), inverseJoinColumns = @JoinColumn(name = "wishlist_book_id"))
    private Set<User> wishList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cart",
            joinColumns = @JoinColumn(name = "cart_user_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_book_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<User> cart;

    @OneToMany(mappedBy = "book")
    private Set<CalendarBookDate> dates;

    @ManyToMany
    @JoinTable(name = "book_categories", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "category_book_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<BookCategory> bookGenres;

    @OneToMany(mappedBy = "book")
    private Set<Review> reviews;
}
