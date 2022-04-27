package ru.bagration.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.bagration.spring.entity.*;
import ru.bagration.spring.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);

    }

}
