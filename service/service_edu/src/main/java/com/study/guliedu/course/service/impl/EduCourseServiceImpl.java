package com.study.guliedu.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.guliedu.common_utils.DateUtils;
import com.study.guliedu.course.entity.EduCourse;
import com.study.guliedu.course.entity.EduCourseDescription;
import com.study.guliedu.course.mapper.EduCourseMapper;
import com.study.guliedu.course.service.IEduCourseDescriptionService;
import com.study.guliedu.course.service.IEduCourseService;
import com.study.guliedu.dto.EduCourseInfoDTO;
import com.study.guliedu.dto.EduCourseInfoListDTO;
import com.study.guliedu.file.service.UploadServiceImpl;
import com.study.guliedu.service_base.result.R;
import com.study.guliedu.vo.EduCourseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author autoGenerator
 * @since 2021-07-04
 */
@Slf4j
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements IEduCourseService {

    @Value("${service.file.dir}")
    private String fileDir;

    private IEduCourseDescriptionService eduCourseDescriptionServiceImpl;

    private UploadServiceImpl uploadServiceImpl;

    @Autowired
    public EduCourseServiceImpl(IEduCourseDescriptionService eduCourseDescriptionServiceImpl, UploadServiceImpl uploadServiceimpl) {
        this.eduCourseDescriptionServiceImpl = eduCourseDescriptionServiceImpl;
        this.uploadServiceImpl = uploadServiceimpl;
    }

    @Override
    public R addCourse(EduCourseInfoDTO courseInfo) {
        try {
            //保存课程基本信息
            EduCourse course = new EduCourse();
            course.setStatus(EduCourse.COURSE_DRAFT);
            BeanUtils.copyProperties(courseInfo, course);
            boolean resultCourseInfo = this.save(course);
            if (!resultCourseInfo) {
                return R.error().message("课程信息保存失败");
            }

            //保存课程详情信息
            EduCourseDescription courseDescription = new EduCourseDescription();
            courseDescription.setDescription(courseInfo.getDescription());
            courseDescription.setId(course.getId());
            boolean resultDescription = eduCourseDescriptionServiceImpl.save(courseDescription);
            if (!resultDescription) {
                return R.error().message("课程详情信息保存失败");
            }

            return R.ok().data("data", course.getId());
        } catch (Exception e) {
            log.error("保存课程信息出错,错误信息为" + e.getMessage(), e);
            return R.error().message("保存课程信息出错,错误信息为" + e.getMessage());
        }
    }

    @Override
    public R updateCourse(EduCourseInfoDTO courseInfo) {
        try {
            //保存课程基本信息
            EduCourse course = new EduCourse();
            course.setStatus(EduCourse.COURSE_DRAFT);
            BeanUtils.copyProperties(courseInfo, course);
            boolean resultCourseInfo = this.updateById(course);
            if (!resultCourseInfo) {
                return R.error().message("课程信息保存失败");
            }

            //保存课程详情信息
            EduCourseDescription courseDescription = new EduCourseDescription();
            courseDescription.setDescription(courseInfo.getDescription());
            courseDescription.setId(course.getId());
            boolean resultDescription = eduCourseDescriptionServiceImpl.updateById(courseDescription);
            if (!resultDescription) {
                return R.error().message("课程详情信息保存失败");
            }
            return R.ok().data("data", course.getId());
        } catch (Exception e) {
            log.error("保存课程信息出错,错误信息为" + e.getMessage(), e);
            return R.error().message("保存课程信息出错,错误信息为" + e.getMessage());
        }
    }


    @Override
    public R uploadCourseCover(MultipartFile file) {
        try {
            //保存课程封面图
            String name = DateUtils.parseDate(LocalDateTime.now(), "yyyyMMddHHmmss") + ".png";
            String path = fileDir + "//course" + "//cover//" + name;
            uploadServiceImpl.upload(file, path);
            return R.ok().message("课程封面图上传成功").data("url", path);
        } catch (Exception e) {
            log.error("上传课程封面图出错,错误信息为" + e.getMessage());
            return R.error().message("上传课程封面图出错,错误信息为" + e.getMessage());
        }
    }

    @Override
    public R getCourseInfo(String id) {
        try {
            if (StringUtils.isEmpty(id)) {
                throw new NullPointerException("传入的课程id为空");
            }
            QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
            wrapper.eq("id", id);
            EduCourse eduCourse = this.getOne(wrapper);

            QueryWrapper<EduCourseDescription> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            EduCourseDescription description = eduCourseDescriptionServiceImpl.getOne(queryWrapper);

            EduCourseVO eduCourseVO = new EduCourseVO();
            BeanUtils.copyProperties(eduCourse, eduCourseVO);
            eduCourseVO.setDescription(description.getDescription());
            return R.ok().data("item", eduCourseVO);
        } catch (Exception e) {
            log.error("获取课程信息出错,错误信息为" + e.getMessage(), e);
            return R.error().message("获取课程信息出错,错误信息为" + e.getMessage());
        }

    }

    @Override
    public R pageQuery(EduCourseInfoListDTO listDTO) {
        try {
            Page<EduCourse> page = new Page<>(listDTO.getPage(), listDTO.getLimit());
            QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("gmt_create");
            if (StringUtils.isEmpty(listDTO.getTitle()) && StringUtils.isEmpty(listDTO.getSubjectId())
                    && StringUtils.isEmpty(listDTO.getSubjectParentId()) && StringUtils.isEmpty(listDTO.getTeacherId())
                    && StringUtils.isEmpty(listDTO.getStatus())){
                baseMapper.selectPage(page, queryWrapper);
                return R.ok().data("rows",page.getRecords()).data("total",page.getTotal());
            }
            String title = listDTO.getTitle();
            String teacherId = listDTO.getTeacherId();
            String subjectParentId = listDTO.getSubjectParentId();
            String subjectId = listDTO.getSubjectId();
            if (!StringUtils.isEmpty(title)) {
                queryWrapper.like("title", title);
            }
            if (!StringUtils.isEmpty(teacherId) ) {
                queryWrapper.eq("teacher_id", teacherId);
            }
            if (!StringUtils.isEmpty(subjectParentId)) {
                queryWrapper.ge("subject_parent_id", subjectParentId);
            }
            if (!StringUtils.isEmpty(subjectId)) {
                queryWrapper.ge("subject_id", subjectId);
            }
            if (!StringUtils.isEmpty(listDTO.getStatus())) {
                queryWrapper.ge("status", listDTO.getStatus());
            }
            baseMapper.selectPage(page, queryWrapper);
            return R.ok().data("rows",page.getRecords()).data("total",page.getTotal());
        }catch (Exception e){
            log.error("查询课程分页列表出错,错误信息为"+e.getMessage(),e);
            return R.error().message("查询课程分页列表出错,错误信息为"+e.getMessage());
        }
    }
}
