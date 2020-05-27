package com.jxa.eduservice.controller;


import com.jxa.eduservice.entity.EduTeacher;
import com.jxa.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-27
 * http://localhost:8001//eduservice/eduteacher
 */
@RestController
@RequestMapping("/eduservice/eduteacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /*查询所有用户*/
    @ApiOperation("查询所有教师")
    @GetMapping("findAll")
    public List<EduTeacher> getAllTeacher(){
        List<EduTeacher> eduTeachers = eduTeacherService.list(null);
        return eduTeachers;
    }

    /*逻辑删除老师*/
    @ApiOperation("根据id删除教师")
    @DeleteMapping("{id}")
    public boolean deleteTeacher(@PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        return flag;
    }
}

