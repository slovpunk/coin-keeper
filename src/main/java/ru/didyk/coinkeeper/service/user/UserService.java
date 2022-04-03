package ru.didyk.coinkeeper.service.user;

import ru.didyk.coinkeeper.dto.UserDTO;
import ru.didyk.coinkeeper.model.User;

import java.util.List;

public interface UserService {

    void addUser(User user);
    void addOrUpdateUser(UserDTO user);
    User getUserById(Long id);
    List<User> getAllUsers();
    void deleteById(Long id);
}
