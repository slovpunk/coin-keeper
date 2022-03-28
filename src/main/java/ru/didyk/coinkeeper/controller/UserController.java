package ru.didyk.coinkeeper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.didyk.coinkeeper.dto.UserDTO;
import ru.didyk.coinkeeper.model.User;
import ru.didyk.coinkeeper.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping(AccountController.PATH)
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users/{id}")
    public User getUserById(@PathVariable(name = "id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("users")
    public void updateUser(@RequestBody UserDTO userDTO) {
        userService.addOrUpdateUser(userDTO);
    }

    @PostMapping("users")
    public void addUser(@RequestBody UserDTO userDTO) {
        userService.addOrUpdateUser(userDTO);
    }

    @DeleteMapping("users/delete/{id}")
    public void deleteById(@PathVariable(name = "id") Long id) {
        userService.deleteById(id);
    }
}
