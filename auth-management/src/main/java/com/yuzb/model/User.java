package com.yuzb.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/*
 * @Author: yuzb
 * @Date: 2021/5/22
 * @Description:
 **/
@Data
@TableName("user")
public class User implements UserDetails {

    @TableId(type = IdType.AUTO,value = "id")
    private Integer userId;

    @TableField("user_name")
    private String username;

    @TableField("password")
    private String password;

    @TableField("phone")
    private String phone;

    @TableField("birthday")
    private Date birthday;

    @TableField("head_url")
    private String headUrl;

    @TableField("mail")
    private String mail;

    @TableField("address")
    private String address;

    @Getter(value = AccessLevel.NONE)
    @TableField("account_non_locked")
    private Boolean accountNonLocked = true;

    @Getter(value = AccessLevel.NONE)
    @TableField("enabled")
    private Boolean enabled = true;

    @TableField(exist = false)
    // 用户所有权限
    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
