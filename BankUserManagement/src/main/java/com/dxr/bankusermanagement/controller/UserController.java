package com.dxr.bankusermanagement.controller;

import com.dxr.bankusermanagement.dto.LoginDTO;
import com.dxr.bankusermanagement.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dxr
 * @since 2025-11-20 
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @PostMapping("/login")
    public String login(LoginDTO loginDTO) {
        userService.login(loginDTO);
        return "login";
    }

}
