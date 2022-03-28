package ru.didyk.coinkeeper.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.didyk.coinkeeper.dto.UserDTO;
import ru.didyk.coinkeeper.model.Account;
import ru.didyk.coinkeeper.model.User;
import ru.didyk.coinkeeper.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addOrUpdateUser(UserDTO user) {
        List<Account> accounts = new ArrayList<>();


        User newUser = User.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
        userRepository.save(newUser);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
