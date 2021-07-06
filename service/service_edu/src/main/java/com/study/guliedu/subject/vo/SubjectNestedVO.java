package com.study.guliedu.subject.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liao.peng
 * @since 2021/7/2 14:30
 */
@Data
public class SubjectNestedVO {

    @ApiModelProperty(name = "主键")
    private String id;

    @ApiModelProperty(name = "课程分类名称")
    private String title;

    @ApiModelProperty(name = "课程分类名称")
    private String parentId;

    @ApiModelProperty(name = "课程子分类集合")
    private List<SubjectNestedVO> children = new ArrayList<>();

}
