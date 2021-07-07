package com.study.guliedu.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.guliedu.course.entity.EduChapter;
import com.study.guliedu.course.entity.EduVideo;
import com.study.guliedu.course.mapper.EduChapterMapper;
import com.study.guliedu.course.service.IEduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.guliedu.course.service.IEduVideoService;
import com.study.guliedu.dto.EduChapterInfoDTO;
import com.study.guliedu.dto.EduCourseInfoDTO;
import com.study.guliedu.service_base.result.R;
import com.study.guliedu.vo.EduChapterVO;
import com.study.guliedu.vo.EduVideoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements IEduChapterService {


    private IEduVideoService eduVideoServiceImpl;

    @Autowired
    public EduChapterServiceImpl(IEduVideoService eduVideoServiceImpl) {
        this.eduVideoServiceImpl = eduVideoServiceImpl;
    }


    @Override
    public R nestedList(String courseId) {
        try {
            //最终要的到的数据列表
            ArrayList<EduChapterVO> chapterVoArrayList = new ArrayList<>();

            //获取章节信息
            QueryWrapper<EduChapter> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("course_id", courseId);
            queryWrapper1.orderByAsc("sort", "id");
            List<EduChapter> chapters = baseMapper.selectList(queryWrapper1);
            //获取课时信息
            QueryWrapper<EduVideo> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("course_id", courseId);
            queryWrapper2.orderByAsc("sort", "id");
            List<EduVideo> videos = eduVideoServiceImpl.list(queryWrapper2);

            //填充章节vo数据
            int count1 = chapters.size();
            for (int i = 0; i < count1; i++) {
                EduChapter chapter = chapters.get(i);
                //创建章节vo对象
                EduChapterVO chapterVo = new EduChapterVO();
                BeanUtils.copyProperties(chapter, chapterVo);
                chapterVoArrayList.add(chapterVo);

                //填充课时vo数据
                ArrayList<EduVideoVO> videoVoArrayList = new ArrayList<>();
                int count2 = videos.size();
                for (int j = 0; j < count2; j++) {

                    EduVideo video = videos.get(j);
                    if (chapter.getId().equals(video.getChapterId())) {
                        //创建课时vo对象
                        EduVideoVO videoVo = new EduVideoVO();
                        BeanUtils.copyProperties(video, videoVo);
                        videoVoArrayList.add(videoVo);
                    }
                }
                chapterVo.setChildren(videoVoArrayList);
            }
            return R.ok().data("items", chapterVoArrayList);
        } catch (Exception e) {
            log.error("查询课程章节列表出错,错误信息为" + e.getMessage(), e);
            return R.error().message("查询课程章节列表出错,错误信息为" + e.getMessage());
        }

    }

    @Override
    public R save(EduChapterInfoDTO chapter) {
        try {
            EduChapter eduChapter = new EduChapter();
            BeanUtils.copyProperties(chapter, eduChapter);
            if(!this.save(eduChapter)){
                return R.error().message("执行保存课程章节信息操作返回false");
            }
            return R.ok();
        }catch (Exception e){
            log.error("保存课程章节信息出错,错误信息为"+e.getMessage(),e);
            return R.error().message("保存课程章节信息出错,错误信息为"+e.getMessage());
        }
    }

    @Override
    public R getChapterById(String id) {
        try {
            EduChapter chapter = this.getById(id);
            return R.ok().data("item",chapter);
        }catch (Exception e){
            log.error("查询课程章节信息出错,错误信息为"+e.getMessage(),e);
            return R.error().message("查询课程章节信息出错,错误信息为"+e.getMessage());
        }
    }

    @Override
    public R updateChapterById(EduChapterInfoDTO chapter) {
        try {
            EduChapter eduChapter = new EduChapter();
            BeanUtils.copyProperties(chapter, eduChapter);
            if(!this.updateById(eduChapter)){
                return R.error().message("执行修改课程章节信息操作返回false");
            }
            return R.ok().data("item",eduChapter);
        }catch (Exception e){
            log.error("修改课程章节信息出错,错误信息为"+e.getMessage(),e);
            return R.error().message("修改课程章节信息出错,错误信息为"+e.getMessage());
        }
    }

    @Override
    public R removeChapterById(String id) {
        //根据id查询是否存在视频，如果有则提示用户尚有子节点
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        if(eduVideoServiceImpl.count(wrapper) > 0){
            return R.error().message("该章节下存在视频课程，请先删除视频课程");
        }
        try {
            if(!this.removeById(id)){
                return R.error().message("执行删除课程章节操作返回false");
            }
            return R.ok();
        }catch (Exception e){
            log.error("删除课程章节失败,错误信息为"+e.getMessage());
            return R.error().message("删除课程章节失败,错误信息为"+e.getMessage());
        }
    }
}
