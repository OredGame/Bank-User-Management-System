package com.dxr.bankusermanagement.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dxr.bankusermanagement.dto.LoginDTO;
import com.dxr.bankusermanagement.entity.User;
import com.dxr.bankusermanagement.mapper.UserMapper;
import com.dxr.bankusermanagement.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dxr
 * @since 2025-11-20 
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
   private UserMapper userMapper;
    public void login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).select("id", "username", "password","status");
        User user = userMapper.selectOne(queryWrapper);


    }
}
