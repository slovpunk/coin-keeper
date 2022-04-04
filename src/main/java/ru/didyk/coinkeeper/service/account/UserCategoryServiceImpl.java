package ru.didyk.coinkeeper.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.didyk.coinkeeper.dto.AccountDTO;
import ru.didyk.coinkeeper.exception.AccountNotFoundException;
import ru.didyk.coinkeeper.model.UserCategory;
import ru.didyk.coinkeeper.repository.UserCategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserCategoryServiceImpl implements UserCategoryService {


    private UserCategoryRepository userCategoryRepository;

    @Autowired
    public UserCategoryServiceImpl(UserCategoryRepository userCategoryRepository) {
        this.userCategoryRepository = userCategoryRepository;
    }

    @Override
    public Optional<UserCategory> findByName(String name) {
        return userCategoryRepository.findByName(name);
    }

    @Override
    public void addUserCategory(UserCategory userCategory) {
        userCategoryRepository.save(userCategory);
    }

    /*
        При помощи этого метода пользователь добавляет счёт (UserCategory) или обновляет с новыми данными
         */
    @Override
    public UserCategory saveAccount(AccountDTO account) {
//        if(userCategory.getId() == null) {
//            throw new AccountIsNull("UserCategory is null");
//        }
        UserCategory newUserCategory = UserCategory.builder()
                .id(account.getId())
                .build();
        return userCategoryRepository.save(newUserCategory);
    }

    /*
    При помощи этого метода пользователь получает данные о счёте (UserCategory) по id
     */
    @Override
    public Optional<UserCategory> findAccountById(Long aLong) {
        if(userCategoryRepository.findById(aLong).isEmpty()) {
            throw new AccountNotFoundException("UserCategory not found");
        }
        return userCategoryRepository.findById(aLong);
    }

    /*
    При помощи этого метода пользователь может удалить счёт (UserCategory)
     */
    @Override
    public void deleteAccount(Long id) {
        if(userCategoryRepository.findById(id).isEmpty()) {
            throw new AccountNotFoundException("UserCategory not found");
        }
        userCategoryRepository.delete(userCategoryRepository.getById(id));
    }

    @Override
    public List<UserCategory> getAll() {
        return userCategoryRepository.findAll();
    }
}
