package com.astrodiary.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private LocalDate datumRodjenja;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postovi;
}
