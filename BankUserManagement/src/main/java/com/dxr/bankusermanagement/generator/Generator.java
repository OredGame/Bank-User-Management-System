package com.dxr.bankusermanagement.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.Collections;

/**
 * @author DXR
 * @date 2025/11/20 19:37
 **/
public class Generator {
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3307/bank-management?serverTimezone=UTC";
        String username="root";
        String password="123456";
        String packageName="com.dxr.bankusermanagement";

        String projectPath=System.getProperty("user.dir");

        FastAutoGenerator.create(url,username,password).globalConfig(builder->{
            builder.author("dxr")
                    .outputDir(projectPath+"/src/main/java")
                    .disableOpenDir()
                    .commentDate("yyyy-MM-dd ");
        }).packageConfig(builder->{
            builder.parent(packageName)
                    .entity("entity")
                    .mapper("mapper")
                    .service("service")
                    .serviceImpl("serviceImpl")
                    .controller("controller")
                    .pathInfo(Collections.singletonMap(OutputFile.xml,projectPath+"/src/main/resources/mapper"));
        }).strategyConfig(builder->{
            builder.addInclude("user")
                    .entityBuilder()
                    .enableLombok()
                    .enableTableFieldAnnotation()
                    .versionColumnName("version")
                    .logicDeleteColumnName("deleted")
                    .controllerBuilder()
                    .enableRestStyle()
                    .mapperBuilder()
                    .enableBaseResultMap()
                    .enableBaseColumnList();
        })
                .templateEngine(
                        new VelocityTemplateEngine()
                ).execute();
    }
}
