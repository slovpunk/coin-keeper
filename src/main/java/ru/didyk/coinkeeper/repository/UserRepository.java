package ru.didyk.coinkeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.didyk.coinkeeper.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
