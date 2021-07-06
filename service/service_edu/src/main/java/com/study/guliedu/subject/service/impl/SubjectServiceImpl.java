package com.study.guliedu.subject.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.guliedu.service_base.result.R;
import com.study.guliedu.subject.entity.EduSubject;
import com.study.guliedu.subject.entity.ExcelSubjectClassifyData;
import com.study.guliedu.subject.listener.SubjectClassifyExcelListener;
import com.study.guliedu.subject.mapper.EduSubjectMapper;
import com.study.guliedu.subject.service.ISubjectService;
import com.study.guliedu.subject.vo.SubjectNestedVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liao.peng
 * @since 2021/7/2 11:09
 */
@Slf4j
@Service
public class SubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements ISubjectService<EduSubject> {


    @Autowired
    private EduSubjectMapper eduSubjectMapper;

    @Autowired
    private SubjectClassifyExcelListener subjectClassifyExcelListener;


    @Override
    public R importSubjectClassify(MultipartFile file) {
        try {
            //1 获取文件输入流
            InputStream inputStream = file.getInputStream();

            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
            EasyExcel.read(inputStream, ExcelSubjectClassifyData.class, subjectClassifyExcelListener).sheet().doRead();
            return R.ok();
        }catch(Exception e) {
            log.error("导入课程分类出错,错误信息为"+e.getMessage(),e);
            return R.error();
        }
    }

    @Override
    public R subjectClassifyTree() {
        return null;
    }

    @Override
    public R getSubjectClassifyTree() {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        List<EduSubject> list = eduSubjectMapper.selectList(wrapper);
        if(CollectionUtils.isEmpty(list)){
            return R.ok().data("items",new ArrayList<SubjectNestedVO>());
        }
        List<SubjectNestedVO> all = list.stream().map(eduSubject -> {
            SubjectNestedVO subjectNestedVO = new SubjectNestedVO();
            subjectNestedVO.setTitle(eduSubject.getTitle());
            subjectNestedVO.setId(eduSubject.getId());
            subjectNestedVO.setParentId(eduSubject.getParentId());
            return subjectNestedVO;
        }).collect(Collectors.toList());

        //从root开始设置子分类
        List<SubjectNestedVO> subjectNestedVOList = all.stream().filter(eduSubject -> "0".equals(eduSubject.getParentId()))
                .peek(subjectNestedVO -> subjectNestedVO.setChildren(getChildren(subjectNestedVO,all)))
                .collect(Collectors.toList());

        return R.ok().data("items",subjectNestedVOList);
    }

    /**
     * 获取子分类列表并设置子分类的下级子分类
     * @param root
     * @param all
     * @return
     */
    private List<SubjectNestedVO> getChildren(SubjectNestedVO root,List<SubjectNestedVO> all){
         return all.stream().filter(subjectNestedVO->root.getId().equals(subjectNestedVO.getParentId()))
                            .peek(subjectNestedVO-> subjectNestedVO.setChildren(getChildren(subjectNestedVO,all)))
                            .collect(Collectors.toList());
    }
}
