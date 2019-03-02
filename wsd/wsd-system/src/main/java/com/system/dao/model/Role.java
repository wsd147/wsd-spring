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
@Table(name = "t_role")
public class Role implements Serializable{

    /**
     * 编号
     *
     *
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name ="roleId")
    private Integer roleId;

    /**
     * 角色名称
     *
     *
     */
    @Column(name = "name")
    private String name;

    /**
     * 角色标题
     *
     *
     */
    @Column(name = "title")
    private String title;

    /**
     * 角色描述
     *
     *
     */
    @Column(name = "description")
    private String description;

    /**
     * 是否授权
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 创建时间
     *
     *
     */
    @Column(name = "ctime")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date ctime;

    /**
     * 修改时间
     */
    @Column(name = "utime")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date utime;


    /**
     * 排序
     *
     *
     */
    @Column(name = "orderNum")
    private Long orderNum;

    private static final long serialVersionUID = 1L;
}
