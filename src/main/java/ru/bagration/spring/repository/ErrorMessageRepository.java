package ru.bagration.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.bagration.spring.entity.ErrorMessage;

import java.util.Optional;

@Repository
public interface ErrorMessageRepository extends JpaRepository<ErrorMessage, Long>, JpaSpecificationExecutor<ErrorMessage> {

    Optional<ErrorMessage> findByKeyAndLang(String key, String lang);
}
