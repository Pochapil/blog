package ru.bagration.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.bagration.spring.entity.ApplicationUser;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long>, JpaSpecificationExecutor<ApplicationUser> {

    Optional<ApplicationUser> findApplicationUserByUsername(String username);

}
