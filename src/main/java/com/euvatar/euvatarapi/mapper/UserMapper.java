package com.euvatar.euvatarapi.mapper;

import com.euvatar.euvatarapi.dto.UserDTO;
import com.euvatar.euvatarapi.model.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles());
        userDTO.setEnabled(user.isEnabled());
        return userDTO;
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setRoles(userDTO.getRoles());
        user.setEnabled(userDTO.isEnabled());

        return user;
    }
}