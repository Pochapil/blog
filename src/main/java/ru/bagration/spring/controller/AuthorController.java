package ru.bagration.spring.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bagration.spring.dto.AuthorDtoAddValid;
import ru.bagration.spring.dto.CreateAuthorDto;
import ru.bagration.spring.service.AuthorService;

import javax.validation.Valid;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    ResponseEntity<?> createAuthor(@Valid @RequestBody CreateAuthorDto dto) {
        System.out.println("-------------request body--------------");
        System.out.println(dto);
        System.out.println("----------------------------------------");
        return authorService.createAuthor(dto.getFirstName(), dto.getSecondName());
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getDetailed(@PathVariable String id){
        return authorService.getDetailsById(id);
    }


    @GetMapping("/list")
    ResponseEntity<?> authorsList(@RequestParam(name = "name", defaultValue = "", required = false) String name,
                                  @RequestParam(name = "firstName", required = false) String firstName,
                                  @RequestParam(name = "secondName", required = false) String secondName,
                                  @RequestParam(name = "detailed", required = false, defaultValue = "false") Boolean detailed,
                                  @PageableDefault(size = 25, page = 0,
                                          sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return authorService.getAuthorList(name, firstName, secondName, detailed, pageable);
    }

    @PostMapping("/add")
    ResponseEntity<?> addNewAuthor(@RequestBody @Valid AuthorDtoAddValid dto){
        return authorService.addNewAuthor(dto.getFirstName(), dto.getSecondName());
    }

}
