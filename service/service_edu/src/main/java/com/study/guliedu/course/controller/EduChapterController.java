package com.study.guliedu.course.controller;


import com.study.guliedu.course.service.IEduChapterService;
import com.study.guliedu.dto.EduChapterInfoDTO;
import com.study.guliedu.dto.EduCourseInfoDTO;
import com.study.guliedu.service_base.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author autoGenerator
 * @since 2021-07-04
 */
@RestController
@RequestMapping("/edu/chapter")
@Api(value = "课程章节管理")
public class EduChapterController {

    private IEduChapterService eduChapterServiceImpl;

    @Autowired
    public EduChapterController(IEduChapterService eduChapterServiceImpl) {
        this.eduChapterServiceImpl = eduChapterServiceImpl;
    }


    @ApiOperation(value = "新增章节")
    @PostMapping
    public R save(
            @ApiParam(name = "chapterVo", value = "章节对象", required = true)
            @RequestBody EduChapterInfoDTO chapter) {
        return eduChapterServiceImpl.save(chapter);
    }

    @ApiOperation(value = "根据ID查询章节")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id) {
        return eduChapterServiceImpl.getChapterById(id);
    }

    @ApiOperation(value = "根据ID修改章节")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id,
            @ApiParam(name = "chapter", value = "章节对象", required = true)
            @RequestBody EduChapterInfoDTO chapter) {

        chapter.setId(id);
        return eduChapterServiceImpl.updateChapterById(chapter);
    }

    @ApiOperation(value = "根据ID删除章节")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id) {
        return eduChapterServiceImpl.removeChapterById(id);
    }

    @ApiOperation(value = "嵌套章节数据列表")
    @GetMapping("chapterListByCourseId/{courseId}")
    public R chapterListByCourseId(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId) {
        return eduChapterServiceImpl.nestedList(courseId);
    }

}

