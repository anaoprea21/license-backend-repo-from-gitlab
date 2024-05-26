package com.example.licenseebe.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCategory {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @org.hibernate.annotations.Type(type = "uuid-char")
    @Column(length = 36)
    private UUID id;

    private String name;

    //relations
    @ManyToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "book_categories", joinColumns = @JoinColumn(name = "category_book_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Book> bookSet;

}
