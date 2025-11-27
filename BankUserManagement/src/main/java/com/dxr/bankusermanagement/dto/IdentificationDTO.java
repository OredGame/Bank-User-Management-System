package com.dxr.bankusermanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class IdentificationDTO {
    @NotBlank(message = "身份证号不能为空")
    @Size(min = 18,max = 18,message = "身份证号长度必须为18位")
    private String idCardNumber;
    @NotBlank(message = "姓名不能为空")
    private String fullName;
}
