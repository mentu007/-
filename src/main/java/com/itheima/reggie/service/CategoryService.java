package com.itheima.reggie.service;

import com.itheima.reggie.entity.Category;
import org.springframework.stereotype.Service;


public interface CategoryService {
    int save(Category category);
}