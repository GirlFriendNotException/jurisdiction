package com.yuzb.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/*
 * @Author: yuzb
 * @Date: 2021/5/22
 * @Description: 权限表
 **/
@Data
@TableName("authority")
public class Authority {

    @TableId(type = IdType.AUTO,value = "id")
    private Integer auth_id;

    @TableField("perm_name")
    private String permName;

    @TableField("perm_tag")
    private String permTag;

    @TableField("url")
    private String url;

}
