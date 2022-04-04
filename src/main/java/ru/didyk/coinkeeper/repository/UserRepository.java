package ru.didyk.coinkeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.didyk.coinkeeper.model.User;
import ru.didyk.coinkeeper.model.UserCategory;

public interface UserRepository extends JpaRepository<User, Long> {
}
