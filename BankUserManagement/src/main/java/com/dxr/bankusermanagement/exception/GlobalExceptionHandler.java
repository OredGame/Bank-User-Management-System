package com.dxr.bankusermanagement.exception;

import com.dxr.bankusermanagement.util.Response;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolationException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author DXR
 * @date 2025/11/21 20:46
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //业务异常
    @ExceptionHandler(BusinessException.class)
    public Response<?> handleBusinessException(BusinessException ex) {
        LOGGER.warning("业务异常: " + ex.getMessage());
        return Response.error(ex.getCode(), ex.getMessage());
    }

    //参数校验异常（@Valid）
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getFieldError().getDefaultMessage();
        LOGGER.warning("参数校验异常: " + errorMsg);
        return Response.error(400, "请求参数错误: " + errorMsg);
    }

    //捕获路径变量/请求参数校验失败@Validated + @PathVariable/@RequestParam）
    @ExceptionHandler(ConstraintViolationException.class)
    public Response<?> handleValidationExceptions(ConstraintViolationException ex) {
        String errorMsg =  ex.getConstraintViolations().iterator().next().getMessage();
        LOGGER.warning("参数校验失败（ConstraintViolation）: " + errorMsg);
        return Response.error(400, "参数错误: " + errorMsg);
    }


    @ExceptionHandler(Exception.class)
    public Response<?> handleException(Exception ex) {
        LOGGER.severe("未知异常: " + ex.getMessage());
        return Response.error(500, "服务器内部错误，请稍候再试");
    }
}