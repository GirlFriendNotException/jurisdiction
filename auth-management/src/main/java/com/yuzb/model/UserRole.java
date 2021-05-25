package com.yuzb.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.PrintWriter;

/*
 * @Author: yuzb
 * @Date: 2021/5/23
 * @Description: 用户角色表
 **/
@Data
@TableName("user_role")
public class UserRole {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("role_id")
    private Integer roleId;
}
