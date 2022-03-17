package ru.didyk.coinkeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.didyk.coinkeeper.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
