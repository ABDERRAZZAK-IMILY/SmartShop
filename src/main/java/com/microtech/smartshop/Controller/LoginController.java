package com.microtech.smartshop.Controller;

import com.microtech.smartshop.dto.UserDtoRequest;
import com.microtech.smartshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDtoRequest request, HttpSession session) {
        Map<String, Object> result = userService.login(request.getUsername(), request.getPassword(), session);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/logout")
    public String logout(HttpSession session) {
        userService.logout(session);
        return "Logged out successfully";
    }
}
