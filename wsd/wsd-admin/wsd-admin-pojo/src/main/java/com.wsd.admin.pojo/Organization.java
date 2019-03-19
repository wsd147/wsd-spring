package com.wsd.admin.pojo;

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
@Table(name = "t_organization")
public class Organization implements Serializable {

    /**
     * id
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    /**
     * 编号
     *
     *
     */
    @Column(name="organizationId")
    private String organizationId;

    /**
     * 所属上级
     *
     *
     */
    @Column(name = "pid")
    private Integer pid;

    /**
     * 组织名称
     *
     *
     */
    @Column(name = "name")
    private String name;

    /**
     * 组织描述
     *
     *
     */
    @Column(name = "description")
    private String description;

    /**
     * 排序
     */
    @Column(name = "orderNum")
    private Integer orderNum;
    /**
     * 状态(0:禁用,1:启用)
     */
    @Column(name = "delFlag")
    private Integer delFlag;

    /**
     * 创建时间
     *
     */
    @Column(name="ctime")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date ctime;

    /**
     * 修改时间
     *
     */
    @Column(name="utime")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date utime;

    private static final long serialVersionUID = 1L;
}
