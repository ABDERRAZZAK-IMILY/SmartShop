package com.microtech.smartshop.service;


import com.microtech.smartshop.enums.Role;
import com.microtech.smartshop.exception.ResourceNotFoundException;
import com.microtech.smartshop.mapper.UserMapper;
import com.microtech.smartshop.model.User;
import com.microtech.smartshop.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements  UserService {

    private  final   UserMapper userMapper;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }



    @Override
    public Map<String, Object> login(String username, String password, HttpSession session) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        session.setAttribute("USER_ID", user.getId());
        session.setAttribute("USER_ROLE", user.getRole());
        session.setAttribute("USERNAME", user.getUsername());


        if(user.getRole() == Role.CLIENT && user.getClient() != null){
            session.setAttribute("CLIENT_ID", user.getClient().getId());
        }

        String jsessionId = session.getId();

        Map<String, Object> response = new HashMap<>();
        response.put("user", userMapper.toDto(user));
        response.put("JSESSIONID", jsessionId);

        return response;
    }



    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
