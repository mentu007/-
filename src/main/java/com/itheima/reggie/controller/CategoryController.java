package com.itheima.reggie.controller;

import com.itheima.reggie.common.PageResult;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category){
        int isAdd = categoryService.save(category);
        if (isAdd == 1) {
            return R.success(null, "新增分类成功了");
        } else {
            return R.error("新增分类失败了");
        }
    }
    @GetMapping("/page")
    public R<PageResult> list(@RequestParam(defaultValue = "1",required = false) int page,
                              @RequestParam(defaultValue = "10",required = false) int pageSize){
        PageResult<Category> pageResult = categoryService.list(page,pageSize);
        return R.success(pageResult);
    }

}
