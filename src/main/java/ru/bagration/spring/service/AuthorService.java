package ru.bagration.spring.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import ru.bagration.spring.utils.ResponseData;

public interface AuthorService {

    ResponseEntity<?> getAllAuthors();

    ResponseEntity<?> createAuthor (String firstName, String secondName);

    ResponseEntity<?> getAuthorList(String name,String firstName, String secondName, Boolean detailed, Pageable pageable);

    ResponseEntity<?> getDetailsById(String id);

    ResponseEntity<?> addNewAuthor(String firstName, String secondName);
}
