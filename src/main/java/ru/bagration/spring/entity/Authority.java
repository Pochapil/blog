package ru.bagration.spring.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;



@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authority {

    @Transient
    private final String sequenceName = "authorities_users_id_seq";

    @Id
    @SequenceGenerator(name = sequenceName, sequenceName = sequenceName, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequenceName)
    private Long id;

    private String authority;

}
