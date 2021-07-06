package com.study.guliedu.subject.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author liao.peng
 * @since 2021/7/2 11:16
 */
@Data
public class ExcelSubjectClassifyData {

    /**
     * 一级课程分类名称
     */
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    /**
     * 二级课程分类名称
     */
    @ExcelProperty(index = 1)
    private String twoSubjectName;

}
