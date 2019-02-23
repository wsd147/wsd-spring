package com.system.dao.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "t_permission")
public class Permission {

    /**
     * 编号
     *
     * @mbg.generated
     */
    @Id
    @KeySql(useGeneratedKeys =  true)
    @Column(name="permissionId")
    private Integer permissionId;

    /**
     * 所属系统
     *
     * @mbg.generated
     */
    @Column(name="systemId")
    private Integer systemId;

    /**
     * 所属上级
     *
     * @mbg.generated
     */
    @Column(name="pid")
    private Integer pid;

    /**
     * 名称
     *
     * @mbg.generated
     */
    @Column(name="name")
    private String name;

    /**
     * 类型(1:目录,2:菜单,3:按钮)
     *
     * @mbg.generated
     */
    @Column(name="type")
    private Byte type;

    /**
     * 权限值
     *
     * @mbg.generated
     */
    @Column(name="permissionValue")
    private String permissionValue;

    /**
     * 路径
     *
     * @mbg.generated
     */
    @Column(name="uri")
    private String uri;

    /**
     * 图标
     *
     * @mbg.generated
     */
    @Column(name="icon")
    private String icon;

    /**
     * 状态(0:禁止,1:正常)
     *
     * @mbg.generated
     */
    @Column(name="status")
    private Byte status;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    @Column(name="ctime")
    private Date ctime;

    /**
     * 排序
     *
     * @mbg.generated
     */
    @Column(name="orders")
    private Long orders;

    private static final long serialVersionUID = 1L;
}
