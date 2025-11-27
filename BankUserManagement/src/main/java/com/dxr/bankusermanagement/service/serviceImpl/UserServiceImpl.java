package com.dxr.bankusermanagement.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dxr.bankusermanagement.dto.*;
import com.dxr.bankusermanagement.entity.User;
import com.dxr.bankusermanagement.exception.BusinessException;
import com.dxr.bankusermanagement.mapper.UserMapper;
import com.dxr.bankusermanagement.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dxr.bankusermanagement.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @ dxr
 * @since author2025-11-20
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final String USER_NAME="username";
    private static final String PASSWORD="password";
    private static final String ID="id";
    private static final String STATUS="status";
    private static final String PHONE="phone";
    private static final String IDCARDNUMBER="id_card_number";
    private static final String FULLNAME="full_name";



    private final UserMapper userMapper;

    public Response<User> login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(USER_NAME, username).select(ID,USER_NAME, PASSWORD, STATUS, PHONE,IDCARDNUMBER,FULLNAME);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(400, "用户名或密码错误！");
        }
        if (user.getPassword().equals(password)) {
            if (user.getStatus() != 1) {
                throw new BusinessException(403, "用户无权登录！");
            }
            user.setPassword(null);
            System.out.println(user);
            return Response.success(user);
        }
        throw new BusinessException(400, "用户名或密码错误！");
    }

    @Override
    public Response<User> register(RegisterDTO registerDTO) {
        String username = registerDTO.getUsername();
        String password = registerDTO.getPassword();
        String comfirmPassword = registerDTO.getConfirmPassword();
        String phone = registerDTO.getPhone();
        if (!password.equals(comfirmPassword)) {
            throw new BusinessException(400, "两次密码不一致！");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            throw new BusinessException(400, "用户名已存在，请重新输入！");
        }
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        userMapper.insert(user);
        user.setPassword(null);
        return Response.success(user);
    }

    @Override
    public Response<User> resetPassword(ResetPasswordDTO resetPasswordDTO) {
        String phone = resetPasswordDTO.getPhone();
        String username = resetPasswordDTO.getUsername();
        String password = resetPasswordDTO.getPassword();
        String comfirmPassword = resetPasswordDTO.getConfirmPassword();
        if (!password.equals(comfirmPassword)) {
            throw new BusinessException(400, "两次密码不一致！");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(USER_NAME, username).select(ID,USER_NAME, PASSWORD, PHONE);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(500, "服务器错误，请稍后再试！");
        }
        if (user.getPhone() == null || !user.getPhone().equals(phone)) {
            throw new BusinessException(400, "手机号错误，身份验证失败！");
        }
        if(user.getPassword().equals(password))
        {
            throw new BusinessException(400,"不要使用老密码！");
        }
        user.setPassword(password);
        userMapper.updateById(user);
        user.setPassword(null);
        return Response.success(user);
    }

    @Override
    public Response<User> getUserById(Long id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ID,id).select(ID,USER_NAME, PASSWORD, STATUS, PHONE,IDCARDNUMBER,FULLNAME);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(500, "服务器错误，请稍后再试！");
        }
        System.out.println(user);
        return Response.success(user);
    }

    @Override
    public Response<User> identifyUser(Long id, IdentificationDTO identificationDTO) {
        String idCardNumber=identificationDTO.getIdCardNumber();
        String fullName = identificationDTO.getFullName();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ID, id);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(500,"服务器错误，请稍后再试！");
        }
        user.setIdCardNumber(idCardNumber);
        user.setFullName(fullName);
        userMapper.updateById(user);
        return Response.success(user);
    }

    @Override
    public Response<User> updateContactInfo(Long id, ContactInfoDTO contactInfoDTO) {
        String phone = contactInfoDTO.getPhone();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ID, id);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(500,"服务器错误，请稍后再试！");
        }
        if (user.getPhone() != null && user.getPhone().equals(phone)) {
            throw new BusinessException(400,"与当前手机号相同！");
        }
        user.setPhone(phone);
        userMapper.updateById(user);
        return Response.success(user);
    }

    @Override
    public Response<User> deleteUser(Long id) {
        // selectById 会自动排除 is_deleted=1 的记录
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(500, "用户不存在或已注销！");
        }

        userMapper.deleteById(id);

        return Response.success(user);
    }
}