package com.yuzb.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*
 * @Author: yuzb
 * @Date: 2021/5/16
 * @Description: 阿里云 OSS 基本配置
 **/
@Data
@Configuration
public class AliyunOssConfig {

    @Value("${aliyun.oss.endPoint}")
    private String endPoint;// 地域节点
    @Value("${aliyun.vod.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.vod.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;// OSS的Bucket名称
    @Value("${aliyun.oss.urlPrefix}")
    private String urlPrefix;// Bucket 域名
    @Value("${aliyun.oss.fileHost}")
    private String fileHost;// 目标文件夹

    // 将OSS 客户端交给Spring容器托管
    @Bean
    public OSS OSSClient() {
        return new OSSClient(endPoint, accessKeyId, accessKeySecret);
    }
}
