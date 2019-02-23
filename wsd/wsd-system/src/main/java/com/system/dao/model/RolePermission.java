package com.system.dao.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "t_role_permission")
public class RolePermission implements Serializable {

    /**
     * 编号
     *
     * @mbg.generated
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name ="rolePermissionId")
    private Integer rolePermissionId;

    /**
     * 角色编号
     *
     * @mbg.generated
     */
    @Column(name = "roleId")
    private Integer roleId;

    /**
     * 权限编号
     *
     * @mbg.generated
     */
    @Column(name = "permissionId")
    private Integer permissionId;

    private static final long serialVersionUID = 1L;
}
