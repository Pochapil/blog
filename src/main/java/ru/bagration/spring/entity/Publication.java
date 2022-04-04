package ru.bagration.spring.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "publications")
public class Publication {

    @Transient
    private final String sequenceName = "publications_id_seq";

    @Id
    @SequenceGenerator(name = sequenceName, sequenceName = sequenceName, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequenceName)
    private Long id;

    @Column(name = "public_id")
    private String publicId;

    @Column(name = "author_id")
    public Long authorId;

    @Column(name = "title")
    private String title;

    @Column(name = "theme_id")
    private Long themeId;

    @Column(name = "content", length = 3000)
    private String content;

}
