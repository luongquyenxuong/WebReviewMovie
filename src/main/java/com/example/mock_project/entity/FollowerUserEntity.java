package com.example.mock_project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "follower")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowerUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unfollow")
    private Integer unfollow;

    @Column(name = "created_at")
    private Date createdAt;

    //Nguoi theo dõi
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "follower_id")
    private UserEntity follower;

    //Nguoi được theo dõi
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "followee_id")
    private UserEntity followee;
}
