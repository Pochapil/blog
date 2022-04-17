package ru.bagration.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.bagration.spring.entity.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>{


}
