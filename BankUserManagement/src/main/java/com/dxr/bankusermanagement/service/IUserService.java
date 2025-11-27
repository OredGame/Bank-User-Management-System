package com.dxr.bankusermanagement.service;

import com.dxr.bankusermanagement.dto.*;
import com.dxr.bankusermanagement.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dxr.bankusermanagement.util.Response;
import jakarta.validation.Valid;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dxr
 * @since 2025-11-20 
 */
public interface IUserService extends IService<User> {
    public Response<User> login(LoginDTO loginDTO);

    Response<User> register(@Valid RegisterDTO registerDTO);

    Response<User> resetPassword(ResetPasswordDTO resetPasswordDTO);

    Response<User> getUserById(Long id);

    Response<User> identifyUser(Long id, IdentificationDTO identificationDTO);

    Response<User> updateContactInfo(Long id, @Valid ContactInfoDTO contactInfoDTO);

    Response<User> deleteUser(Long id);
}
