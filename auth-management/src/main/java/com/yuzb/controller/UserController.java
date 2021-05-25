package com.yuzb.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuzb.model.User;
import com.yuzb.model.vo.UserPage;
import com.yuzb.model.vo.UserVo;
import com.yuzb.service.UserService;
import com.yuzb.utils.CommonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/*
 * @Author: yuzb
 * @Date: 2021/5/22
 * @Description:
 **/
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * @Description: 新增用户
     * @Param: user
     * @return: CommonResult
     * @Date: 2021/5/22
     */
    @PostMapping("/save")
    public CommonResult saveUser(@RequestBody UserVo userVo) {
        String encode = passwordEncoder.encode(userVo.getPassword());
        if (userVo.getFile() != null) {
            try {
                String headUrl = userService.uploadUserHead(userVo.getFile());
                userVo.setHeadUrl(headUrl);
            } catch (Exception e) {
                return CommonResult.failed(e.getMessage());
            }
        }
        userVo.setPassword(encode);
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        if (!userService.save(user)) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }

    /**
     * @Description: 登录接口
     * @Param: username, password
     * @return: CommonResult
     * @Date: 2021/5/23
     */
    @GetMapping("/add")
    public String loginUser() {
        return "ces";
    }


    @GetMapping("/test")
    public String test() {
        return "test";
    }

    /**
     * @Description: 分页查询
     * @Param:
     * @return: CommonResult
     * @Date: 2021/5/24
     */
    @PostMapping("/queryList")
    public CommonResult queryList(@RequestBody UserPage userPage) {
        return CommonResult.success(userService.getUserList(userPage));
    }

    /**
     * @Description: 根据用户名查找用户
     * @Param: username
     * @return: CommonResult
     * @Date: 2021/5/24
     */
    @GetMapping("/findUserByusername")
    public CommonResult findUserByusername(@RequestParam String username) {
        return CommonResult.success(userService.getOne(new QueryWrapper<User>().eq("user_name", username)));
    }

    /**
     * @Description: 根据电话查找用户
     * @Param: username
     * @return: CommonResult
     * @Date: 2021/5/24
     */
    @GetMapping("/findUserByPhone")
    public CommonResult findUserByPhone(@RequestParam String phone) {
        return CommonResult.success(userService.getOne(new QueryWrapper<User>().eq("phone", phone)));
    }
}
