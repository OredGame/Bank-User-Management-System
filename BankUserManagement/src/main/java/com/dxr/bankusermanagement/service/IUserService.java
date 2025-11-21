package com.dxr.bankusermanagement.service;

import com.dxr.bankusermanagement.dto.LoginDTO;
import com.dxr.bankusermanagement.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dxr
 * @since 2025-11-20 
 */
public interface IUserService extends IService<User> {
    public void login(LoginDTO loginDTO);
}
