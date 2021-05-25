package com.yuzb.model.vo;


import lombok.Data;


import java.util.Date;

/*
 * @Author: yuzb
 * @Date: 2021/5/24
 * @Description:
 **/
@Data
public class UserPage {

    private Integer userId;
    private String username;
    private String password;
    private String phone;
    private Date birthday;
    private String headUrl;
    private String mail;
    private String address;
    private Boolean accountNonLocked = true;
    private Boolean enabled = true;

    private Integer pageNo = 1;
    private Integer pageSize = 10;
    private Integer offset;

    private Integer getOffset() {
        if (pageNo != null && pageSize != null) {
            return (pageNo - 1) * pageSize;
        }
        return offset == null ? 0 : offset;
    }
}
