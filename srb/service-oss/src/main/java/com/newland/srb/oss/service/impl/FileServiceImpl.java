package com.newland.srb.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.newland.srb.oss.service.IFileService;
import com.newland.srb.oss.util.OssProperties;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

/**
 * Author: leell
 * Date: 2022/8/10 20:55:08
 */
@Service
public class FileServiceImpl implements IFileService {
    @Override
    public String upload(InputStream inputStream, String module, String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(OssProperties.ENDPOINT, OssProperties.KEY_ID, OssProperties.KEY_SECRET);
        // 判断BUCKET_NAME是否存在
        if (!ossClient.doesBucketExist(OssProperties.BUCKET_NAME)) {
            ossClient.createBucket(OssProperties.BUCKET_NAME);
            ossClient.setBucketAcl(OssProperties.BUCKET_NAME, CannedAccessControlList.PublicRead);
        }

        String timeFolder = new DateTime().toString("/yyyy/MM/dd/");
        fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf((".")));
        String key = module + timeFolder + fileName;
        ossClient.putObject(OssProperties.BUCKET_NAME, key, inputStream);
        ossClient.shutdown();
        return "https://" + OssProperties.BUCKET_NAME + "." + OssProperties.ENDPOINT + "/" + key;
    }

    @Override
    public void removeFile(String url) {
        OSS ossClient=new OSSClientBuilder().build(OssProperties.ENDPOINT,OssProperties.KEY_ID,OssProperties.KEY_SECRET);
        String host = "https://" + OssProperties.BUCKET_NAME + "." + OssProperties.ENDPOINT + "/";
        String objectName = url.substring(host.length());
        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(OssProperties.BUCKET_NAME, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
