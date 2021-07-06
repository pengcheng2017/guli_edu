package com.study.guliedu.teacher.controller;




import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.guliedu.teacher.entity.Teacher;
import com.study.guliedu.teacher.service.ITeacherService;
import com.study.guliedu.dto.TeacherDTO;
import com.study.guliedu.service_base.result.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author autoGenerator
 * @since 2021-06-20
 */
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController {

    @Autowired
    private ITeacherService teacherServiceImpl;

    @ApiOperation(value = "新增讲师")
    @PostMapping
    public R save(
        @ApiParam(name = "teacher", value = "讲师对象", required = true)
        @RequestBody Teacher teacher){
        if(teacherServiceImpl.save(teacher)){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public R getById(
        @ApiParam(name = "id", value = "讲师ID", required = true)
        @PathVariable String id){
        return R.ok().data("item",teacherServiceImpl.getById(id));
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("{id}")
    public R updateById(
        @ApiParam(name = "id", value = "讲师ID", required = true)
        @PathVariable String id,
        @ApiParam(name = "teacher", value = "讲师对象", required = true)
        @RequestBody Teacher teacher){
        teacher.setId(id);
        teacherServiceImpl.updateById(teacher);
        return R.ok();
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping("{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
                    TeacherDTO teacherQuery
    ){
        Page<Teacher> pageParam = new Page<>(page, limit);
        teacherServiceImpl.pageQuery(pageParam, teacherQuery);
        return R.ok().data("rows",pageParam.getRecords()).data("total",pageParam.getTotal());
    }

    @GetMapping("/findAll")
    public List<Teacher> findAll(){
        return teacherServiceImpl.list();
    }

    @DeleteMapping("{id}")
    public R removeById(@PathVariable String id){
         if(teacherServiceImpl.removeById(id)){
             return R.ok();
         }else{
             return R.error();
         }
    }

}

