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

    /*
    При помощи этого метода пользователь добавляет сумму совершенных покупок
    в категорию Продукты. Должен обновляться баланс в Account и sum в ProductCategory
     */
    @Override
    public void updateCategory(ProductCategory productCategory, Long categoryId) {
        ProductCategory newCategory = repository.getById(categoryId);
        Account account = accountRepository.getById(newCategory.getAccount().getId());
        account.setBalance(account.getBalance() - productCategory.getSum());
        newCategory.setAccount(account);
        newCategory.setId(productCategory.getId());
        newCategory.setTitle(productCategory.getTitle());
        newCategory.setSum(productCategory.getSum() + newCategory.getSum());
        repository.save(newCategory);
    }

    @Override
    public ProductCategory getById(Long id) {
        return repository.getById(id);
    }
}
