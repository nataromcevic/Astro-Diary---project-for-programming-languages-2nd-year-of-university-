package com.astrodiary.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter @Setter @NoArgsConstructor

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naslov;
    private String tekst;
    private LocalDateTime datumObjave = LocalDateTime.now();

    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
