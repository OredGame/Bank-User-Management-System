package com.dxr.bankusermanagement.util;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DXR
 * @date 2025/11/21 20:40
 **/

@Data
@NoArgsConstructor
public class Response<T> {
    private Integer code;
    private String message;
    private T data;

  public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setCode(200);
        response.setMessage("success");
        response.setData(data);
        return response;
  }
  public  static <T> Response<T> error(Integer coed,String message) {
        Response<T> response = new Response<>();
        response.setCode(coed);
        response.setMessage(message);
        return response;
  }
}
