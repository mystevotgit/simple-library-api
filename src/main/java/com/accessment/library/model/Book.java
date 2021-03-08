package com.accessment.library.model;

import com.accessment.library.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id
    @Setter(AccessLevel.PROTECTED) long id;
    @Column(name = "title", unique = true, nullable = false)
    private String title;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "copies", nullable = false)
    private int copies;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Borrow> borrowers;
}
