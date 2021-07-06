package com.study.guliedu.subject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.guliedu.service_base.result.R;
import com.study.guliedu.subject.entity.EduSubject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 课程管理
 * @author liao.peng
 * @since 2021/7/2 11:05
 */
public interface ISubjectService<T> extends IService<T> {

    /**
     * 导入课程分类
     * @param  file 上传请求
     * @return
     */
    R importSubjectClassify(MultipartFile file);

    /**
     * 课程分类树
     * @return
     */
    R subjectClassifyTree();

    /**
     * 获取课程的分类树
     * @return
     */
    R getSubjectClassifyTree();

}
