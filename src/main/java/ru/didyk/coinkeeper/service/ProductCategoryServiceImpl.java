package ru.didyk.coinkeeper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.didyk.coinkeeper.model.Account;
import ru.didyk.coinkeeper.model.ProductCategory;
import ru.didyk.coinkeeper.repository.AccountRepository;
import ru.didyk.coinkeeper.repository.ProductCategoryRepository;

import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {


    private ProductCategoryRepository productCategoryRepository;
    private AccountRepository accountRepository;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository repository, AccountRepository accountRepository) {
        this.productCategoryRepository = repository;
        this.accountRepository = accountRepository;
    }

    /*
    При помощи этого метода пользователь добавляет категории товаров, привязывая их к своему счету.
     */
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
        productCategoryRepository.save(category);
    }

    /*
    При помощи этого метода пользователь добавляет сумму совершенных покупок
    в желаемую категорию. Баланс в Account и sum в ProductCategory обновляются.
     */
    @Override
    public void addPurchasesInCategory(ProductCategory productCategory, Long categoryId) {
        ProductCategory newCategory = productCategoryRepository.getById(categoryId);
        Account account = accountRepository.getById(newCategory.getAccount().getId());
        account.setBalance(account.getBalance() - productCategory.getSum());
        newCategory.setAccount(account);
        newCategory.setId(productCategory.getId());
        newCategory.setTitle(productCategory.getTitle());
        newCategory.setSum(productCategory.getSum() + newCategory.getSum());
        productCategoryRepository.save(newCategory);
    }

    /*
    При помощи этого метода пользователь удаляет категорию
     */
    @Override
    public void deleteCategory(Long id) {
        productCategoryRepository.delete(productCategoryRepository.getById(id));
    }

    /*
    При помощи этого метода пользователь может получить информацию о категории товара при помощи id
     */
    @Override
    public Optional<ProductCategory> getCategoryById(Long id) {
        return productCategoryRepository.findById(id);
    }
}
