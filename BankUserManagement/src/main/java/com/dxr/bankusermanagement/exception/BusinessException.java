package com.dxr.bankusermanagement.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author DXR
 * @date 2025/11/21 20:43
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    private Integer code;

    public BusinessException( String message) {
        super(message);
        this.code = 400;
    }
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
