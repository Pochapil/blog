package ru.bagration.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.bagration.spring.entity.Authority;
import ru.bagration.spring.entity.ErrorMessage;
import ru.bagration.spring.entity.Theme;
import ru.bagration.spring.repository.AuthorityRepository;
import ru.bagration.spring.repository.ErrorMessageRepository;
import ru.bagration.spring.repository.ThemeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);


    }

}
