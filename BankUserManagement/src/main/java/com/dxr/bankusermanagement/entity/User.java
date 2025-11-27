package com.dxr.bankusermanagement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author dxr
 * @since 2025-11-20 
 */
@Getter
@Setter
@ToString
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 登录用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password", select = false)
    private String password;

    /**
     * 真实姓名
     */
    @TableField("full_name")
    private String fullName;

    /**
     * 身份证号
     */
    @TableField("id_card_number")
    private String idCardNumber;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 账户状态：1-正常，0-冻结，2-注销
     */
    @TableField("status")
    private Byte status;

    /**
     * 逻辑删除标志：0-未删，1-已删
     */
    @TableField("is_deleted")
    private Byte isDeleted;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 最后更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 乐观锁版本号
     */
    @Version
    @TableField("version")
    private Integer version;
}
