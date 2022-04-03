package ru.didyk.coinkeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.didyk.coinkeeper.model.UserCategory;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
}
