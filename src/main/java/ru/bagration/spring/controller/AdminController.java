package ru.bagration.spring.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static ru.bagration.spring.utils.ResponseData.response;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/test")
    ResponseEntity<?> test() {
        return response(Collections.singletonMap("admin", "test"));
    }

}
