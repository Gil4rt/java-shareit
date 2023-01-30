package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments", schema = "public")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String text;
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "author_id")
    private Long authorId;
    @Column
    private LocalDateTime created;
}
