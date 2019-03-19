package com.wsd.admin.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name="t_user_role")
public class UserRole implements Serializable {
    /**
     * 编号
     *
     *
     */
    @Id
    @Column(name ="userRoleId")
    @KeySql(useGeneratedKeys = true)
    private Integer userRoleId;

    /**
     * 用户编号
     *
     *
     */
    @Column(name ="userId")
    private Integer userId;

    /**
     * 角色编号
     *
     *
     */
    @Column(name ="roleId")
    private Integer roleId;
}
