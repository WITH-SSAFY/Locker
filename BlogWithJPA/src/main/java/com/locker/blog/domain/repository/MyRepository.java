package com.locker.blog.domain.repository;

import com.locker.blog.domain.post.Post;
import com.locker.blog.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter @Setter @ToString
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
@Table(name = "repository")
public class MyRepository {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String repoName;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "usrId", referencedColumnName = "id")
    private User user;

    private Long likes;
}

