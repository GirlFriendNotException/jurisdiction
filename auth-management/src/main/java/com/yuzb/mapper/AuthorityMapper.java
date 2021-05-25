package com.yuzb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuzb.model.Authority;

import java.util.List;

public interface AuthorityMapper  extends BaseMapper<Authority> {
    List<Authority> findAllAuthority();
}
