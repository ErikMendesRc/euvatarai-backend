package com.euvatar.euvatarapi.service;


import com.euvatar.euvatarapi.dto.UserDTO;
import com.euvatar.euvatarapi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    Optional<UserDTO> getUserById(Long id);
    List<UserDTO> getAllUsers();
    Optional<User> findByUsername(String username);
    Optional<String> login(String username, String password);
}