package com.system.dao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="t_user")
public class User implements Serializable{

    /**
     * 编号
     *
     * @mbg.generated
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name="userId")
    private Integer userId;

    /**
     * 帐号
     *
     * @mbg.generated
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码MD5(密码+盐)
     *
     * @mbg.generated
     */
    @Column(name="password")
    private String password;

    /**
     * 盐
     *
     * @mbg.generated
     */
    @Column(name="salt")
    private String salt;

    /**
     * 姓名
     *
     * @mbg.generated
     */
    @Column(name="realname")
    private String realname;

    /**
     * 头像
     *
     * @mbg.generated
     */
    @Column(name="avatar")
    private String avatar;

    /**
     * 电话
     *
     * @mbg.generated
     */
    @Column(name="phone")
    private String phone;

    /**
     * 邮箱
     *
     * @mbg.generated
     */
    @Column(name="email")
    private String email;

    /**
     * 性别(0:女，1：男)
     *
     * @mbg.generated
     */
    @Column(name="sex")
    private Integer sex;

    /**
     * 状态(0:正常,1:锁定)
     *
     * @mbg.generated
     */
    @Column(name="locked")
    private Integer locked;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    @Column(name="ctime")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date ctime;

    private static final long serialVersionUID = 1L;
}
