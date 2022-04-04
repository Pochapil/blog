package ru.bagration.spring.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bagration.spring.dto.CreatePublicationDto;
import ru.bagration.spring.dto.NewPublicationDto;
import ru.bagration.spring.dto.PublicationDto;
import ru.bagration.spring.dto.PublicationDtoAddValid;
import ru.bagration.spring.service.PublicationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/publication")
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationService publicationService;

    @GetMapping("/feed")
    public ResponseEntity<?> getFeed() {
        return ResponseEntity.ok(publicationService.getAllPublications());
    }
    
    @PostMapping
    ResponseEntity<?> createPublication(@Valid @RequestBody CreatePublicationDto dto) {
        System.out.println("-------------request body--------------");
        System.out.println(dto);
        System.out.println("----------------------------------------");
        return publicationService.createPublication(dto.getAuthorId(), dto.getTitle(), dto.getThemeId(), dto.getContent());
    }

    @GetMapping("/list")
    ResponseEntity<?> publicationList(@RequestParam(name = "searchString", defaultValue = "", required = false) String searchString,
                                      @RequestParam(name = "title", required = false) String title,
                                      @RequestParam(name = "content", required = false) String content,
                                      @RequestParam(name = "detailed", required = false, defaultValue = "false") Boolean detailed,
                                      @PageableDefault(size = 25, page = 0,
                                              sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return publicationService.getPublicationList(searchString, title, content, detailed, pageable);
    }


    @GetMapping("/list/rh")
    ResponseEntity<?> getData(@RequestParam(name = "author_name", defaultValue = "", required = false) String authorName,
                              @RequestParam(name = "theme_name", defaultValue = "", required = false) String themeName,
                              @PageableDefault(size = 25, page = 0,
                                      sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return publicationService.getData(authorName, themeName, pageable);
    }


    @GetMapping("/{id}")
    ResponseEntity<?> getDetailed(@PathVariable String id) {
        return publicationService.getDetailsById(id);
    }

    @PostMapping("/add")
    ResponseEntity<?> addNewPublication(@RequestBody @Valid NewPublicationDto dto){
        return publicationService.addNewPublication(dto.getAuthorId(), dto.getThemeId(), dto.getTitle(), dto.getContent());
    }

}
