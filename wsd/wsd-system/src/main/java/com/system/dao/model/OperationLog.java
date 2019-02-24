package com.system.dao.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "t_oeration_log")
public class OperationLog implements Serializable{
    /**
     * 编号
     *
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "logId")
    private Integer logId;

    /**
     * 操作描述
     *
     *
     */
    @Column(name = "description")
    private String description;

    /**
     * 操作用户
     *
     *
     */
    @Column(name = "username")
    private String username;

    /**
     * 操作时间
     *
     *
     */
    @Column(name = "startTime")
    private Date startTime;

    /**
     * 消耗时间
     *
     *
     */
    @Column(name = "spendTime")
    private Integer spendTime;

    /**
     * 根路径
     *
     *
     */
    @Column(name = "basePath")
    private String basePath;

    /**
     * URI
     *
     *
     */
    @Column(name = "uri")
    private String uri;

    /**
     * URL
     *
     *
     */
    @Column(name = "url")
    private String url;

    /**
     * 请求类型
     *
     *
     */
    @Column(name = "method")
    private String method;

    /**
     * 用户标识
     *
     *
     */
    @Column(name = "userAgent")
    private String userAgent;

    /**
     * IP地址
     *
     *
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 权限值
     *
     *
     */
    @Column(name = "permissions")
    private String permissions;

    @Column(name = "parameter")
    private String parameter;

    @Column(name = "result")
    private String result;

    private static final long serialVersionUID = 1L;
}
