package ru.didyk.coinkeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.didyk.coinkeeper.model.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
