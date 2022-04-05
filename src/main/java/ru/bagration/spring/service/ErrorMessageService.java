package ru.bagration.spring.service;

import org.springframework.stereotype.Service;

@Service
public interface ErrorMessageService {

    String findByKeyAndLang(String key, String lang);

}
