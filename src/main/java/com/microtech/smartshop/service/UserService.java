package com.microtech.smartshop.service;


import jakarta.servlet.http.HttpSession;

import java.util.Map;

public interface UserService {

    Map<String, Object> login(String username, String password, HttpSession session);

    void logout(HttpSession session);

}
