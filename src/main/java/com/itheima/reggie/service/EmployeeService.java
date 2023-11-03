package com.itheima.reggie.service;

import com.itheima.reggie.common.PageResult;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;

import javax.servlet.http.HttpSession;

public interface EmployeeService {


    R<Employee> login(HttpSession session, Employee employee);

    void save(HttpSession session, Employee employee);
}