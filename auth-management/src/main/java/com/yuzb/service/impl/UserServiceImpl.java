package com.yuzb.service.impl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuzb.config.AliyunOssConfig;
import com.yuzb.mapper.UserMapper;
import com.yuzb.model.Authority;
import com.yuzb.model.User;
import com.yuzb.model.vo.PageBean;
import com.yuzb.model.vo.UserPage;
import com.yuzb.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;


/*
 * @Author: yuzb
 * @Date: 2021/5/22
 * @Description:
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, UserDetailsService {
    // 允许上传文件(图片)的格式
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg",
            ".jpeg", ".gif", ".png"};
    @Autowired
    private OSS ossClient;// 注入阿里云oss文件服务器客户端

    @Autowired
    private AliyunOssConfig aliyunOssConfig;// 注入阿里云OSS基本配置类

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("user_name", username));
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在!");
        }
        List<Authority> authorityList = baseMapper.findAuthorityByUserid(user.getUserId());
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorityList.forEach(new Consumer<Authority>() {
            @Override
            public void accept(Authority authority) {
                authorities.add(new SimpleGrantedAuthority(authority.getPermTag()));
            }
        });
        user.setAuthorities(authorities);
        return user;
    }

    @Override
    public PageBean<User> getUserList(UserPage userPage) {
        Page<User> page = new Page<>(userPage.getPageNo(), userPage.getPageSize());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_non_locked", userPage.getAccountNonLocked()).eq("enabled", userPage.getEnabled());
        page = this.page(page, queryWrapper);
        if (Objects.nonNull(page)) {
            return new PageBean<>(userPage.getPageNo(), userPage.getPageSize(), this.count(queryWrapper), page.getRecords());
        }
        return new PageBean<>(userPage.getPageNo(), userPage.getPageSize(), 0, Collections.emptyList());
    }

    /**
     * @Description: 将用户头像上传到oss
     * @Param: file
     * @return: String
     * @Date: 2021/5/24
     */
    @Override
    public String uploadUserHead(MultipartFile file) {
        // 获取oss的Bucket名称
        String bucketName = aliyunOssConfig.getBucketName();
        // 获取oss的地域节点
        String endpoint = aliyunOssConfig.getEndPoint();
        // 获取oss的AccessKeySecret
        String accessKeySecret = aliyunOssConfig.getAccessKeySecret();
        // 获取oss的AccessKeyId
        String accessKeyId = aliyunOssConfig.getAccessKeyId();
        // 获取oss目标文件夹
        String filehost = aliyunOssConfig.getFileHost();
        // 返回图片上传后返回的url
        String returnImgeUrl = "";

        // 校验图片格式
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }
        if (!isLegal) {// 如果图片格式不合法
            throw new RuntimeException("用户头像图片格式错误");
        }
        // 获取文件原名称
        String originalFilename = file.getOriginalFilename();
        // 获取文件类型
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 新文件名称
        String newFileName = UUID.randomUUID().toString() + fileType;
        // 构建日期路径, 例如：OSS目标文件夹/2021/05/16/文件名
        String filePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        // 文件上传的路径地址
        String uploadImgeUrl = filehost + "/" + filePath + "/" + newFileName;

        // 获取文件输入流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("用户头像上传失败");
        }
        /**
         * 下面两行代码是重点坑：
         * 现在阿里云OSS 默认图片上传ContentType是image/jpeg
         * 也就是说，获取图片链接后，图片是下载链接，而并非在线浏览链接，
         * 因此，这里在上传的时候要解决ContentType的问题，将其改为image/jpg
         */
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("image/jpg");

        //文件上传至阿里云OSS
        ossClient.putObject(bucketName, uploadImgeUrl, inputStream, meta);
        // 获取文件上传后的图片返回地址
        return "http://" + bucketName + "." + endpoint + "/" + uploadImgeUrl;
    }

}
