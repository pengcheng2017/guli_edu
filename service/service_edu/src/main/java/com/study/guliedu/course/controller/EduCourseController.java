package com.study.guliedu.course.controller;


import com.study.guliedu.course.service.IEduCourseService;
import com.study.guliedu.dto.EduCourseInfoDTO;
import com.study.guliedu.dto.EduCourseInfoListDTO;
import com.study.guliedu.service_base.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author autoGenerator
 * @since 2021-07-04
 */
@Api(value = "课程管理")
@RestController
@RequestMapping("edu/course")
public class EduCourseController {

    private IEduCourseService eduCourseServiceImpl;

    @Autowired
    public EduCourseController(IEduCourseService eduCourseServiceImpl) {
        this.eduCourseServiceImpl = eduCourseServiceImpl;
    }


    @ApiOperation(value = "新增课程")
    @PostMapping("addCourse")
    public R addCourse(
            @ApiParam(name = "courseInfo", value = "课程基本信息", required = true)
            @RequestBody EduCourseInfoDTO courseInfo) {
        return eduCourseServiceImpl.addCourse(courseInfo);
    }

    @ApiOperation(value = "修改课程")
    @PutMapping("updateCourse")
    public R updateCourse(@ApiParam(name = "courseInfo", value = "课程基本信息", required = true)
                          @RequestBody EduCourseInfoDTO courseInfo) {
        return eduCourseServiceImpl.updateCourse(courseInfo);
    }

    @ApiOperation(value = "上传课程封面图")
    @PostMapping("uploadCourseCover")
    public R uploadCourseCover(MultipartFile file) {
        return eduCourseServiceImpl.uploadCourseCover(file);
    }

    @ApiOperation(value = "获取课程信息")
    @GetMapping("getCourseInfoById/{id}")
    public R getCourseInfoById(@PathVariable String id) {
        return eduCourseServiceImpl.getCourseInfo(id);
    }


    @ApiOperation(value = "分页课程列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Integer page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Integer limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    EduCourseInfoListDTO courseQuery) {
        courseQuery.setPage(page);
        courseQuery.setLimit(limit);
       return  eduCourseServiceImpl.pageQuery(courseQuery);
    }

}

