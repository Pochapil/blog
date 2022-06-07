package ru.bagration.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.bagration.spring.dto.TestDto;
import ru.bagration.spring.service.ApplicationUserService;

import java.util.Collections;

import static ru.bagration.spring.utils.ResponseData.response;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final ApplicationUserService applicationUserService;

    @GetMapping("/admin_access")
    @PreAuthorize("hasAuthority('admin')")
    ResponseEntity<?> adminIsAccess() {
        System.out.println("access");
        return response(Collections.singletonMap("access", "granted"));
    }

    @PostMapping("/create_user")
    @PreAuthorize("hasAuthority('admin_create_user')")
    ResponseEntity<?> createUser(@RequestBody TestDto testDto) {
        return applicationUserService.createUser(testDto.getUsername(), testDto.getPassword());
    }

    @GetMapping("/cors")
    ResponseEntity<?> corsTest() {
        return response(Collections.singletonMap("access", "granted"));
    }

}
