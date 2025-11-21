package com.dxr.bankusermanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.dxr.bankusermanagement.mapper")
@SpringBootApplication
public class BankUserManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankUserManagementApplication.class, args);
    }

}
