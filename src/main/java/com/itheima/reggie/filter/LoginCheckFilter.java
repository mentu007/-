package com.itheima.reggie.filter;

import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.utils.PathMatcherUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录功能处理
 *
 * 过滤器使用
 * 1.创建一个过滤器类LoginCheckFilter
 * 2.过滤器类实现Filter接口(包：javax.servlet.Filter)
 * 3.filter接口中doFilter方法重写（alt+enter ->enter）
 * 4.过滤器类上一定加上@WebFilter
 * 5.启动类上加上@ServletCompentScan 开关
 */
@WebFilter
@Slf4j //lombok日志注解
public class LoginCheckFilter implements Filter {
    /**
     * 核心业务：
     *      1.如果用户没有登录，但访问的路径是需要登录的，则跳转登录页面
     *      2.不需要登录的资源，直接放行
     *      3.如果用户已经登录，访问的路径是需要登录的，直接放行
     * @param servletRequest
     * @param servletResponse
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        System.out.println("过滤器被执行了。。。。。");
        //1.将ServletRequest、ServletResponse 进行强制转换
        HttpServletRequest request = (HttpServletRequest)servletRequest;//请求对象
        HttpServletResponse response = (HttpServletResponse)servletResponse;//响应对象
        //2.获取页面请求的uri路径
        //String requestURL = request.getRequestURL().toString();
        String requestURI = request.getRequestURI();
        //log.debug("requestURL="+requestURL);//ctrl+d
        log.debug("requestURI="+requestURI);
        //3.判断本地URI是否需要放行
        String[] urls = new String[]{
                "/**/login.html","/**/api/**","/**/images/**","/**/js/**",
                "/**/plugins/**","/**/styles/**","favicon.ico","/**/fonts/**",
                "/employee/login","/employee/logout"
        };
        boolean check = PathMatcherUtil.check(urls, requestURI);//check true:能放行 false：不能放行
        if(check){
            //4.如果匹配上数组中的路径，则放行
            chain.doFilter(servletRequest,servletResponse);
            return;
        }
        //5.判断用户是否登录，如果登录则放行
        HttpSession session = request.getSession();
        Employee employee = (Employee)session.getAttribute("employee");
        if(employee != null){
            //6.用户登录了，可以访问后续资源，直接放行
            chain.doFilter(servletRequest,servletResponse);
            return;
        }
        //7.跳转登录页面
        response.sendRedirect("/backend/page/login/login.html");//请求重定向
    }
}
