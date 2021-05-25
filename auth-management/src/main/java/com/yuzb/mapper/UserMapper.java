package com.yuzb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuzb.model.Authority;
import com.yuzb.model.Role;
import com.yuzb.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    List<Authority> findAuthorityByUserid(@Param("userId") Integer userId);
}
