package ru.didyk.coinkeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.didyk.coinkeeper.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
