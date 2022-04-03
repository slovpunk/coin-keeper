package ru.didyk.coinkeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.didyk.coinkeeper.model.MoneyMovement;
import ru.didyk.coinkeeper.model.UserCategory;

import java.util.List;

public interface MoneyMovementRepository extends JpaRepository<MoneyMovement, Long> {
}
