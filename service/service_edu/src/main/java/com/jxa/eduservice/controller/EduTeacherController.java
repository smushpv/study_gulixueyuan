package com.jxa.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jxa.commonutils.R;
import com.jxa.eduservice.entity.EduTeacher;
import com.jxa.eduservice.entity.vo.TeacherQuery;
import com.jxa.eduservice.service.EduTeacherService;
import com.jxa.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/eduteacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /*查询所有用户*/
    @ApiOperation("查询所有教师")
    @GetMapping("findAll")
    public R getAllTeacher(){
        List<EduTeacher> eduTeachers = eduTeacherService.list(null);

        return R.ok().data("items",eduTeachers);
    }

    /*逻辑删除老师*/
    @ApiOperation("根据id删除教师")
    @DeleteMapping("{id}")
    public R deleteTeacher(@PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /*分页查询讲师
    * current 当前页
    * limit 每页记录数
    * */
    @ApiOperation("查询所有教师")
    @GetMapping("pageTeachers/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit){
        Page<EduTeacher> pageTeachers = new Page<>(current,limit);
        eduTeacherService.page(pageTeachers,null);
        long total = pageTeachers.getTotal();
        List<EduTeacher> records = pageTeachers.getRecords();
        /*Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);*/
        return R.ok().data("total",total).data("row",records);
    }

    /*条件分页查询*/
    @ApiOperation("根据条件查询所有教师")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建Page对象
        Page<EduTeacher> pageTeachers = new Page<>(current,limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // mybatis学过 动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }

        eduTeacherService.page(pageTeachers,wrapper);
        long total = pageTeachers.getTotal();//总记录数
        List<EduTeacher> records = pageTeachers.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }

    /*添加一个讲师*/
    @ApiOperation("添加一个新的讲师")
    @PostMapping("saveTeacher")
    public R saveTeacher(@RequestBody EduTeacher eduTeacher){
        boolean isSave = eduTeacherService.save(eduTeacher);
        if (isSave){
            return R.ok();
        }else {
            return R.error();
        }
    }


    /*根据讲师id进行查询*/
    @ApiOperation("根据讲师id进行查询")
    @GetMapping("getTeacher/{id}")
    public R findTeacherById(@PathVariable Integer id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }


    /*修改讲师内容*/
    @ApiOperation("修改讲师内容")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

}

