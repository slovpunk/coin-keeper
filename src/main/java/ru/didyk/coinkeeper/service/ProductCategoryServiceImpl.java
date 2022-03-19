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
    public void addProductCategory(ProductCategory productCategory, Long accountId) {
        Account account = accountRepository.findById(accountId).get();
        account.setBalance(account.getBalance() - productCategory.getSum());
        ProductCategory category = ProductCategory.builder()
                .id(productCategory.getId())
                .sum(productCategory.getSum())
                .title(productCategory.getTitle())
                .account(account)
                .build();
        repository.save(category);
    }

    @Override
    public ProductCategory getById(Long id) {
        return repository.getById(id);
    }
}
