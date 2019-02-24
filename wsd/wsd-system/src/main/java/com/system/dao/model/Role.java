package com.system.dao.model;

import lombok.Data;
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
     * 创建时间
     *
     *
     */
    @Column(name = "ctime")
    private Date ctime;

    /**
     * 排序
     *
     *
     */
    @Column(name = "orders")
    private Long orders;

    private static final long serialVersionUID = 1L;
}
