package ru.didyk.coinkeeper.service.productCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.didyk.coinkeeper.dto.ProductCategoryDTO;
import ru.didyk.coinkeeper.model.MoneyMovement;
import ru.didyk.coinkeeper.repository.UserCategoryRepository;
import ru.didyk.coinkeeper.repository.MoneyMovementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MoneyMovementServiceImpl implements MoneyMovementService {


    private MoneyMovementRepository moneyMovementRepository;
    private UserCategoryRepository userCategoryRepository;

    @Autowired
    public MoneyMovementServiceImpl(MoneyMovementRepository repository, UserCategoryRepository userCategoryRepository) {
        this.moneyMovementRepository = repository;
        this.userCategoryRepository = userCategoryRepository;
    }

    @Override
    public void addMoneyMovement(MoneyMovement moneyMovement) {
        moneyMovementRepository.save(moneyMovement);
    }

    /*
        При помощи этого метода пользователь добавляет категории товаров, привязывая их к своему счету.
        TODO: сделать основной аккаунт и переключатель между аккаунтами, если это нужно
         */
    @Override
    public void addProductCategory(ProductCategoryDTO productCategory, Long accountId) {

    }

    /*
    При помощи этого метода пользователь добавляет сумму совершенных покупок
    в желаемую категорию. Баланс в UserCategory и sum в ProductCategoryBot обновляются.
    В приложении будет выводиться сообщение со списком категорий пронумерованных, для добавления расходов
    надо будет выбрать цифру и сумму покупки.
     */
    @Override
    public void addPurchasesInCategory(ProductCategoryDTO productCategory, Long categoryId) {

    }

    /*
    При помощи этого метода пользователь удаляет категорию
     */
    @Override
    public void deleteCategory(Long id) {
        moneyMovementRepository.delete(moneyMovementRepository.getById(id));
    }

    /*
    При помощи этого метода пользователь может получить информацию о категории товара при помощи id
     */
    @Override
    public Optional<MoneyMovement> getCategoryById(Long id) {
        return moneyMovementRepository.findById(id);
    }

    @Override
    public List<MoneyMovement> getAllProductCategories() {
        return moneyMovementRepository.findAll();
    }

}
