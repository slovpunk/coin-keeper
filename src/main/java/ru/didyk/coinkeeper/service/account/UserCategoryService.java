package ru.didyk.coinkeeper.service.account;

import ru.didyk.coinkeeper.dto.AccountDTO;
import ru.didyk.coinkeeper.model.UserCategory;

import java.util.List;
import java.util.Optional;

public interface UserCategoryService {

    void addUserCategory(UserCategory userCategory);

    UserCategory saveAccount(AccountDTO account);

    Optional<UserCategory> findAccountById(Long aLong);

    void deleteAccount(Long id);

    List<UserCategory> getAll();
}
