package ru.bagration.spring.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bagration.spring.dto.CreateThemeDto;
import ru.bagration.spring.dto.ThemeDtoCreate;
import ru.bagration.spring.service.ThemeService;

import javax.validation.Valid;

@RestController
@RequestMapping("/theme")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    @PostMapping
    ResponseEntity<?> createTheme(@Valid @RequestBody CreateThemeDto dto) {
        System.out.println("-------------request body--------------");
        System.out.println(dto);
        System.out.println("----------------------------------------");
        return themeService.createTheme(dto.getName(), dto.getAgeCategory());
    }

    @GetMapping("/list")
    ResponseEntity<?> themeList(@RequestParam(name = "searchString", defaultValue = "", required = false) String searchString,
                                @PageableDefault(size = 25, page = 0,
                                        sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return themeService.getThemeList(searchString, pageable);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getDetailed(@PathVariable String id) {
        return themeService.getDetailsById(id);
    }

    @PostMapping("/add")
    ResponseEntity<?> addNewTheme(@RequestBody @Valid ThemeDtoCreate dto){
        return themeService.addNewTheme(dto.getName(), dto.getAgeCategory());
    }


}
