package com.locker.blog.domain.post;

import com.locker.blog.domain.repository.MyRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
@Table(name = "post")
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long pid;

    @Column(nullable = false)
    @JoinColumn(nullable = false)
    private String email;

    @Column(nullable = false)
    @JoinColumn(nullable = false)
    private String nickname;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private LocalDate created;
    private LocalDate updated;

    @ColumnDefault("0")
    private Long likes;

    private String thumbnail;
    private String description;
    private Long repoId;

    @Column(nullable = false)
    private Long usrId;
}
