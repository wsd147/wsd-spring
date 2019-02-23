package com.system.dao.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "t_user_permission")
public class UserPermission implements Serializable{

    /**
     * 编号
     *
     * @mbg.generated
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "userPermissionId")
    private Integer userPermissionId;

    /**
     * 用户编号
     *
     * @mbg.generated
     */
    @Column(name = "userId")
    private Integer userId;

    /**
     * 权限编号
     *
     * @mbg.generated
     */
    @Column(name = "permissionId")
    private Integer permissionId;

    /**
     * 权限类型(-1:减权限,1:增权限)
     *
     * @mbg.generated
     */
    @Column(name = "type")
    private int type;

    private static final long serialVersionUID = 1L;
}
