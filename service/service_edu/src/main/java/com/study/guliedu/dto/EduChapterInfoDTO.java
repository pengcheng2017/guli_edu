package com.study.guliedu.dto;

import com.study.guliedu.vo.EduVideoVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liao.peng
 * @since 2021/7/6 11:24
 */
@Data
public class EduChapterInfoDTO {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "课程主键")
    private String courseId;

    @ApiModelProperty(value = "章节名称")
    private String title;

    private List<EduVideoVO> children = new ArrayList<>();

}
