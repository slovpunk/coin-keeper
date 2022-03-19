package ru.didyk.coinkeeper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.didyk.coinkeeper.model.Account;
import ru.didyk.coinkeeper.model.ProductCategory;
import ru.didyk.coinkeeper.repository.AccountRepository;
import ru.didyk.coinkeeper.repository.ProductCategoryRepository;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {


    private ProductCategoryRepository repository;
    private AccountRepository accountRepository;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void addProductCategory(ProductCategory productCategory) {
        repository.save(productCategory);
    }

    @Override
    public ProductCategory getById(Long id) {
        return repository.getById(id);
    }
}
