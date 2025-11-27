package com.dxr.bankusermanagement.controller;

import com.dxr.bankusermanagement.dto.*;
import com.dxr.bankusermanagement.entity.User;
import com.dxr.bankusermanagement.service.serviceImpl.UserServiceImpl;
import com.dxr.bankusermanagement.util.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dxr
 * @since 2025-11-20
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/login")
    public Response<User> login(@Valid @RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

   @PostMapping("/register")
    public Response<User> register(@Valid @RequestBody RegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }

    @PatchMapping("/passwordreset")
    public Response<User> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        return userService.resetPassword( resetPasswordDTO);
    }

    @GetMapping("/{id}")
    public Response<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PatchMapping("/{id}/identification")
    public Response<User> identifyUser(
            @PathVariable Long id,
            @Valid @RequestBody IdentificationDTO identificationDTO) {
        return userService.identifyUser(id, identificationDTO);
    }

    @PatchMapping("/{id}/contact-info")
    public Response<User> updateContactInfo(
            @PathVariable Long id,
            @Valid @RequestBody ContactInfoDTO contactInfoDTO) {
        return userService.updateContactInfo(id, contactInfoDTO);
    }


    @DeleteMapping("/{id}")
    public Response<User> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}

