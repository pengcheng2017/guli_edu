package com.study.guliedu.course.service;

import com.study.guliedu.course.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.guliedu.dto.EduChapterInfoDTO;
import com.study.guliedu.dto.EduCourseInfoDTO;
import com.study.guliedu.service_base.result.R;
import com.study.guliedu.vo.EduChapterVO;

import java.util.List;

/**
 * <p>
 * 课程章节 服务类
 * </p>
 *
 * @author autoGenerator
 * @since 2021-07-04
 */
public interface IEduChapterService extends IService<EduChapter> {
    /**
     * 章节列表
     * @param courseId
     * @return
     */
    R nestedList(String courseId);

    /**
     * 新增章节
     * @param chapter
     * @return
     */
    R save(EduChapterInfoDTO chapter);

    /**
     * 查询章节信息
     * @param id
     * @return
     */
    R getChapterById(String id);

    /**
     * 修改章节信息
     * @param chapter
     * @return
     */
    R updateChapterById(EduChapterInfoDTO chapter);

    /**
     * 删除章节信息
     * @param id
     * @return
     */
    R removeChapterById(String id);
}
