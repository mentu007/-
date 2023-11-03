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

}    