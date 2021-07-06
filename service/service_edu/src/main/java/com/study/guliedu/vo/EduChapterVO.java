package com.study.guliedu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liao.peng
 * @since 2021/7/6 10:01
 */
@Data
@ApiModel(value = "章节信息")
public class EduChapterVO implements Serializable {

    private static final long serialVersionUID = 1234L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "章节名称")
    private String title;

    private List<VideoVO> children = new ArrayList<>();

}
