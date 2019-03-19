package com.wsd.admin.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
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
     *
     */
    @Id
    @KeySql(useGeneratedKeys =  true)
    @Column(name="permissionId")
    private Integer permissionId;

    /**
     * 所属系统
     *
     *
     */
    @Column(name="systemId")
    private Integer systemId;

    /**
     * 所属上级
     *
     *
     */
    @Column(name="pid")
    private Integer pid;

    /**
     * 名称
     *
     *
     */
    @Column(name="name")
    private String name;

    /**
     * 类型(1:目录,2:菜单,3:按钮)
     *
     *
     */
    @Column(name="type")
    private Integer type;

    /**
     * 权限值
     *
     *
     */
    @Column(name="permissionValue")
    private String permissionValue;

    /**
     * 路径
     *
     *
     */
    @Column(name="uri")
    private String uri;

    /**
     * 图标
     *
     *
     */
    @Column(name="icon")
    private String icon;

    /**
     * 状态(0:禁止,1:正常)
     *
     *
     */
    @Column(name="status")
    private Integer status;

    /**
     * 创建时间
     *
     *
     */
    @Column(name="ctime")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date ctime;

    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name="utime")
    private Date utime;
    /**
     * 排序
     *
     *
     */
    @Column(name="orderNum")
    private Long orderNum;

    private static final long serialVersionUID = 1L;
}
