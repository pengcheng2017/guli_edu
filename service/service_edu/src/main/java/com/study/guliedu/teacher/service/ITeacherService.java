package com.study.guliedu.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.guliedu.teacher.entity.Teacher;
import com.study.guliedu.dto.TeacherDTO;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author autoGenerator
 * @since 2021-06-20
 */
public interface ITeacherService extends IService<Teacher> {

    /**
     * 分页查询讲师列表
     * @param pageParam
     * @param teacherQuery
     */
    void pageQuery(Page<Teacher> pageParam, TeacherDTO teacherQuery);

}
