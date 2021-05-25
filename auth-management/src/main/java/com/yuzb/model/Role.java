package com.yuzb.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/*
 * @Author: yuzb
 * @Date: 2021/5/22
 * @Description:
 **/
@Data
@TableName("role")
public class Role {

    @TableId(type = IdType.AUTO,value = "id")
    private  Integer roleId;

    @TableField("role_name")
    private String roleName;
}
