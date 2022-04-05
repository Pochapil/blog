package ru.bagration.spring.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "error_messages")
public class ErrorMessage {

    @Transient
    private final String sequenceName = "error_messages_id_seq";

    @Id
    @SequenceGenerator(name = sequenceName, sequenceName = sequenceName, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequenceName)
    private Long id;

    @Column(name = "key")
    private String key;

    @Column(name = "value", length = 1000)
    private String value;

    @Column(name = "lang")
    private String lang;

}
