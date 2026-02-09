package com.actisys.authservice.repository;

import com.actisys.authservice.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthRepository extends JpaRepository<Credential, UUID> {
    Optional<Credential> findByEmail(String email);

    Optional<Credential> findByLogin(String str);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);
}
