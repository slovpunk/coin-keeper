package ru.didyk.coinkeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.didyk.coinkeeper.model.UserCategory;

import java.util.Optional;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    Optional<UserCategory> findByName(String name);
}
