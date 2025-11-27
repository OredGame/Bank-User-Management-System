package com.dxr.bankusermanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DXR
 * @date 2025/11/24 17:30
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "手机号不能为空")
    private String phone;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
}
