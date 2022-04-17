package ru.bagration.spring.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "user_authority")
public class UserAuthority {

    @Transient
    private final String sequenceName = "user_authority_id_seq";

    @Id
    @SequenceGenerator(name = sequenceName, sequenceName = sequenceName, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequenceName)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "authority_id")
    private Long authorityId;

}
