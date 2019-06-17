package com.ego.manage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @Auther:pcb
 * @Date:19/5/28
 * @Description:com.ego.manage.service
 * @version:1.0
 */
public interface PicService {
    /**
     * 文件上传
     * @param file
     * @return
     */
    Map<String,Object> upload(MultipartFile file) throws IOException;
}
