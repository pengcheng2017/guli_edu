package com.study.guliedu.subject.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.guliedu.subject.entity.EduSubject;
import com.study.guliedu.subject.entity.ExcelSubjectClassifyData;
import com.study.guliedu.subject.service.ISubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author liao.peng
 * @since 2021/7/2 11:19
 */
@Slf4j
@Component
public class SubjectClassifyExcelListener extends AnalysisEventListener<ExcelSubjectClassifyData> {


    @Resource(name = "subjectServiceImpl")
    private ISubjectService<EduSubject> subjectServiceImpl;


    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("表头信息："+headMap);
    }




    @Override
    public void invoke(ExcelSubjectClassifyData excelSubjectData, AnalysisContext analysisContext) {

        //导入一级分类
        //判断一级分类是否存在
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",excelSubjectData.getOneSubjectName());
        EduSubject oldOne = subjectServiceImpl.getOne(wrapper);
        String pid;
        if(oldOne == null){
            //导入
            EduSubject eduSubject = new EduSubject();
            eduSubject.setTitle(excelSubjectData.getOneSubjectName());
            eduSubject.setParentId("0");
            subjectServiceImpl.save(eduSubject);
            pid = eduSubject.getId();
        }else{
            pid = oldOne.getId();
        }

        //导入二级分类
        //判断二级分类是否存在
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("title",excelSubjectData.getTwoSubjectName());
        eduSubjectQueryWrapper.eq("parent_id",pid);
        EduSubject oldEduSubject = subjectServiceImpl.getOne(eduSubjectQueryWrapper);
        if(oldEduSubject == null){
            EduSubject eduSubject = new EduSubject();
            eduSubject.setTitle(excelSubjectData.getTwoSubjectName());
            //设置上级分类id
            eduSubject.setParentId(pid);
            //导入
            subjectServiceImpl.save(eduSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
