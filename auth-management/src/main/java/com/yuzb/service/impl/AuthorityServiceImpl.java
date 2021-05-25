package com.yuzb.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuzb.mapper.AuthorityMapper;
import com.yuzb.model.Authority;
import com.yuzb.service.AuthorityService;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @Author: yuzb
 * @Date: 2021/5/22
 * @Description:
 **/
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {
    @Override
    public List<Authority> findAllAuthority() {
        return baseMapper.findAllAuthority();
    }
}
