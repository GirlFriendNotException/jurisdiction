package com.yuzb.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/*
 * @Author: yuzb
 * @Date: 2021/5/23
 * @Description:
 **/
@Data
@TableName("role_auth")
public class RoleAuth {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("auth_id")
    private Integer authId;

    @TableField("role_id")
    private Integer roleId;
}
