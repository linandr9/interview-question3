package com.example.demo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Getter
@Setter
@Table(name="TBL_Questions")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="author")
    private String author;

    @Column(name="message")
    private String message;

    @Column(name="replies")
    @OneToMany
    private List<ReplyEntity> replies;
}
