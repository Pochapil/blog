package ru.bagration.spring.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import ru.bagration.spring.utils.ResponseData;

public interface ThemeService {

    ResponseEntity<?> addNewTheme(String name, Integer ageCategory);

    ResponseEntity<?> getAllThemes();

    ResponseEntity<?> createTheme(String name, Integer ageCategory);

    ResponseEntity<?> getThemeList(String name , Pageable pageable);

    ResponseEntity<?> getDetailsById(String id);

}
