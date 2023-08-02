package com.example.mock_project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "user_information")
public class UserInformationEntity {
    @Id
    @Column(name = "user_information_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userInfoId;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "cover_avr")
    private String coverAvr;

    @Column(name = "date_of_birth")
    private Date dob;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private UserEntity user;
}
