package com.yuzb.model.vo;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/*
 * @Author: yuzb
 * @Date: 2021/5/24
 * @Description:
 **/
@Data
public class UserVo {

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
    private MultipartFile file;

}
