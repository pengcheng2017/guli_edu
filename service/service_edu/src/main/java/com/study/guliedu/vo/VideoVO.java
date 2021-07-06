package com.study.guliedu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liao.peng
 * @since 2021/7/6 10:02
 */
@Data
@ApiModel(value = "课时信息")
public class VideoVO implements Serializable {

    private static final long serialVersionUID = 23L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "课时名称")
    private String title;

    @ApiModelProperty(value = "是否免费观看")
    private Boolean free;

}
