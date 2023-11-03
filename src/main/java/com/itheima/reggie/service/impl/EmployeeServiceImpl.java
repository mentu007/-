package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.PageResult;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.exception.BusinessException;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * 员工业务逻辑处理层
 */
@Service
@Transactional
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public R<Employee> login(HttpSession session, Employee employee) {
        //获取传入数据
        String password = employee.getPassword();
        String username = employee.getUsername();

        //传入密码做加密处理
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

        //创建查询对象
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Employee::getUsername,username);

        Employee em = employeeMapper.selectOne(wrapper);

    // 业务逻辑1：校验账号与密码

        //分支逻辑没有进入分支，则说明数据库存在该账号
        if (em == null){
            return R.error("登录失败，账号或密码错误");
        }
        //
        String dbPassword = em.getPassword();
        //分支逻辑：如果 输入密码与数据库密码不匹配，则返回错误信息
        if (!md5Password.equals(dbPassword)){
            return R.error("登录失败，账号或密码错误");
        }

    // 业务逻辑2：校验 用户状态
        Integer status = em.getStatus();
        // 分支逻辑：登录员工状态字段为0则，返回禁用信息
        if (status == 0){
            return R.error("登录失败，您的账号已被禁用");
        }

        //
        /*
        * 顺序流程代码逻辑：
        * 不满足分支逻辑1：账号存在
        * 不满足分支逻辑2：密码匹配
        * 不满足分支逻辑3：账号未被禁用
        * 1先将用户数据存入session
        * 2调用成功方法，传入em
        * */
        session.setAttribute("employee",em);
        return R.success(em);

    }

    @Override
    public void save(HttpSession session, Employee employee) {

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employee.setStatus(1);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        Employee sEmployee = (Employee) session.getAttribute("employee");
        Long id = sEmployee.getId();
        employee.setCreateUser(id);
        employee.setUpdateUser(id);
        int insert = employeeMapper.insert(employee);
        log.debug("insert");


    }
}









