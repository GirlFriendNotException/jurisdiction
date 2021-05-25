package com.yuzb.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yuzb.model.Authority;

import java.util.List;

/*
 * @Author: yuzb
 * @Date: 2021/5/22
 * @Description:
 **/
public interface AuthorityService extends IService<Authority> {

    List<Authority> findAllAuthority();
}
