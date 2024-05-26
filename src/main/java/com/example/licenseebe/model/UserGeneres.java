package com.example.licenseebe.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGeneres {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @org.hibernate.annotations.Type(type = "uuid-char")
    @Column(length = 36)
    private UUID id;

    @OneToOne
    private User user;

    private String bookGenre;

    public UserGeneres(User user, String bookGenre){
        this.user = user;
        this.bookGenre = bookGenre;
    }
}
