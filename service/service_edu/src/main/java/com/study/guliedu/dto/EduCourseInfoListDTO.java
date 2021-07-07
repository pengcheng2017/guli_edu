package com.study.guliedu.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 课程搜索
 * @author liao.peng
 * @since 2021/7/5 16:08
 */
@Data
public class EduCourseInfoListDTO implements Serializable {

    private static final long serialVersionUID = 123L;

    @ApiModelProperty(value = "页码")
    private Integer page = 1;

    @ApiModelProperty(value = "每页条数")
    private Integer limit = 10;

    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "课程分类ID")
    private String subjectId;

    @ApiModelProperty(value = "课程父级分类ID")
    private String subjectParentId;


    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
    private String status;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;


    @ApiModelProperty(value = "课程简介")
    private String description;
}
