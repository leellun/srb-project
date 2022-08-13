package com.newland.srb.oss.service;

import java.io.InputStream;

/**
 * Author: leell
 * Date: 2022/8/10 20:54:51
 */
public interface IFileService {
    String upload(InputStream inputStream,String module,String fileName);
    void  removeFile(String url);
}
