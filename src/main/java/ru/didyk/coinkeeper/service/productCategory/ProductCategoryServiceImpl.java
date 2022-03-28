package ru.didyk.coinkeeper.service.productCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.didyk.coinkeeper.dto.ProductCategoryDTO;
import ru.didyk.coinkeeper.exception.AccountNotFoundException;
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
    TODO: сделать основной аккаунт и переключатель между аккаунтами, если это нужно
     */
    @Override
    public void addProductCategory(ProductCategoryDTO productCategory, Long accountId) {
        Account account = accountRepository.findById(accountId).get();
        if (account.getId() == null) {
            throw new AccountNotFoundException("Account not found");
        }
        if (productCategory.getTitle().equals(
                account.getProductCategoryList()
                        .stream()
                        .filter(title -> title.getTitle().equals(productCategory.getTitle()))
                        .findFirst()
                        .get()
                        .getTitle())) {
            ProductCategory productCategory1 = ProductCategory.builder()
                    .id(productCategory.getId())
                    .sum(productCategory.getSum())
                    .title(productCategory.getTitle())
                    .account(account)
                    .build();
            productCategoryRepository.save(productCategory1);
            return;
        } else {
            Long id = account.getProductCategoryList().stream()
                    .max((a, b) -> (int) (a.getId() - b.getId()))
                    .get()
                    .getId();
            account.setBalance(account.getBalance() - productCategory.getSum());
            ProductCategory productCategory1 = ProductCategory.builder()
                    .id(id + 1)
                    .sum(productCategory.getSum())
                    .title(productCategory.getTitle())
                    .account(account)
                    .build();
            productCategoryRepository.save(productCategory1);

        }
    }

    /*
    При помощи этого метода пользователь добавляет сумму совершенных покупок
    в желаемую категорию. Баланс в Account и sum в ProductCategory обновляются.
    В приложении будет выводиться сообщение со списком категорий пронумерованных, для добавления расходов
    надо будет выбрать цифру и сумму покупки.
     */
    @Override
    public void addPurchasesInCategory(ProductCategoryDTO productCategory, Long categoryId) {
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
