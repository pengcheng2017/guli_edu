package com.study.guliedu.course.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.guliedu.course.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.guliedu.dto.EduCourseInfoDTO;
import com.study.guliedu.dto.EduCourseInfoListDTO;
import com.study.guliedu.service_base.result.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author autoGenerator
 * @since 2021-07-04
 */
public interface IEduCourseService extends IService<EduCourse> {

    /**
     * 新增课程信息
     * @param courseInfo
     *
     * @return
     */
    R addCourse(EduCourseInfoDTO courseInfo);

    R updateCourse(EduCourseInfoDTO courseInfo);

    /**
     * 上传课程封面图
     * @param file 课程封面图
     * @return
     */
    R uploadCourseCover(MultipartFile file);

    /**
     * 查询课程信息
     * @param id
     * @return
     */
    R getCourseInfo(String id);

    /**
     * 课程分页列表
     *
     * @param listDTO
     * @return
     */
    R pageQuery(EduCourseInfoListDTO listDTO);

}
