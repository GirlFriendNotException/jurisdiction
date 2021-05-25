package com.yuzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuzb.model.User;
import com.yuzb.model.vo.PageBean;
import com.yuzb.model.vo.UserPage;
import org.springframework.web.multipart.MultipartFile;


public interface UserService extends IService<User> {

    PageBean<User> getUserList(UserPage userPage);

    String uploadUserHead(MultipartFile file);
}
