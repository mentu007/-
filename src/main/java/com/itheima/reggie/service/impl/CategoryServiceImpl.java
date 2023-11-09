package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.PageResult;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public int save(Category category) {
        return categoryMapper.insert(category);
    }

    @Override
    public PageResult<Category> list(int page, int pageSize) {

        Page<Category> wPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
//按Category下的sort字段 做升序排序
        queryWrapper.orderByAsc(Category::getSort);
        categoryMapper.selectPage(wPage,queryWrapper);
        log.debug("分页结果{}********总记录数{}",wPage.getRecords(),wPage.getTotal());
        return new PageResult<>(wPage.getTotal(),wPage.getRecords());
    }
}
