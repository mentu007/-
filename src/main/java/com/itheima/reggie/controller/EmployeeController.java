package com.itheima.reggie.controller;

import com.itheima.reggie.common.PageResult;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpSession session, @RequestBody Employee employee){
        R<Employee> r = this.employeeService.login(session,employee);
        return r;
    }

    @PostMapping("/logout")
    public R<String> logout(HttpSession session){
        session.invalidate();
        return R.success("退出成功");
    }
    @PostMapping
    public R<String> logout(HttpSession session,@RequestBody Employee employee){
        employeeService.save(session,employee);
        return R.success("新增员工成功成功");
    }

    /*
    * Get请求的处理截止
    * 1. 参数接受机制1
    * 这里可以不用使用注解做参数接收，但是：必须保证形参名称 要与页面传递的参数名称一致
    * 2. 参数接收机制2
    * 使用注解对参数做接收
    * */
    @GetMapping("/page")
    public R<PageResult> page(
            @RequestParam(defaultValue = "1",required = false) Integer page,
            @RequestParam(defaultValue = "10",required = false) Integer pagesize

            ){
            // 这个注解是怎么用的，为什么要这么写？
        PageResult pageResult = employeeService.page(page, pagesize);

        return R.success(pageResult);

    }



}    