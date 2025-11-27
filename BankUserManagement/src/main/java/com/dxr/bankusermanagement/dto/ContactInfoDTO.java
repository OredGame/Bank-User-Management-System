package com.dxr.bankusermanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DXR
 * @date 2025/11/24 17:31
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfoDTO {
    @NotBlank(message = "手机号不能为空")
    private String phone;
}
