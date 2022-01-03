package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-20
 */
@Api(description = "讲师模块")
@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    //1.查询讲师表中的所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAll(){
        //调用service的方法实现查询所有的操作
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }
    //2.逻辑删除讲师的方法
    @ApiOperation(value = "根据ID逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){   //@PathVariable注解 :得到路径中的值
        boolean flag = teacherService.removeById(id);
        if (flag){
            return R.ok();
        }
        else {
            return R.error();
        }
    }
    //3.分页查询讲师的方法
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(
            @ApiParam(name = "current", value = "当前页", required = true) @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable long limit){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //调用方法实现分页
        //调用方法的时候,底层封装,把分页所有数据封装到pageTeacher对象里面
        teacherService.page(pageTeacher, null);
        //总记录数
        long total = pageTeacher.getTotal();
        //数据list集合

        try {
            int a = 1/0;
        } catch (Exception e) {
            //执行自定义异常
            throw new GuliException(20001,"执行了自定义异常处理!!!");
        }

        List<EduTeacher> records = pageTeacher.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("records", records);
        return R.ok().data(map);
    }
    //4.条件查询带分页的方法
    @ApiOperation(value = "条件查询讲师带分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(
            @ApiParam(name = "current", value = "当前页", required = true) @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable long limit,
            @ApiParam(name = "teacherQuery", value = "条件信息")
            @RequestBody(required = false) TeacherQuery teacherQuery) {  //使用@RequestBody时,请求方式必须是@PostMapping
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)){
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create", end);
        }
        //调用方法实现条件查询分页
        teacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }
    //5.添加讲师
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@ApiParam(name = "eduTeacher", value = "讲师信息", required = false)@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }
    //6.根据ID进行讲师查询
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)@PathVariable long id){
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }
    //7.修改讲师
    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(
            @ApiParam(name = "eduteacher", value = "讲师信息",required = false)@RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }
}

