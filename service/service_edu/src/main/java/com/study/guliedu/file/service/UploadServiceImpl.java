package com.study.guliedu.file.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 文件上传等操作
 * @author liao.peng
 * @since 2021/7/5 14:12
 */
@Slf4j
@Service
public class UploadServiceImpl {

    public void upload(MultipartFile file,String path) throws Exception{
        int index;
        byte[] bytes = new byte[1024];
        try(InputStream is = file.getInputStream();
            FileOutputStream downloadFile = new FileOutputStream(new File(path))){
            //1 获取文件输入流
            while ((index = is.read(bytes)) != -1) {
                downloadFile.write(bytes, 0, index);
                downloadFile.flush();
            }
        } catch (Exception e) {
            log.error("文件上传失败，错误信息为"+e.getMessage(),e);
            throw new Exception("文件上传失败，错误信息为"+e.getMessage());
        }
    }

}
