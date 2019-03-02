package com.system.dao.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "t_user_organization")
public class UserOrganization implements Serializable{

    /**
     * 编号
     *
     *
     */
    @Id
    @KeySql(useGeneratedKeys =  true)
    @Column(name="userOrganizationId")
    private Integer userOrganizationId;

    /**
     * 用户编号
     *
     *
     */
    @Column(name = "userId")
    private Integer userId;

    /**
     * 组织编号
     *
     *
     */
    @Column(name = "organizationId")
    private Integer organizationId;

    private static final long serialVersionUID = 1L;
}
