package com.jxa.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherQuery {
    @ApiModelProperty(value = "教师名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1:高级教师; 2:首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间",example = "2019-01-01 14:42:41")
    private String begin;//使用String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间",example = "2020-12-12 21:42:41")
    private String end;
}
