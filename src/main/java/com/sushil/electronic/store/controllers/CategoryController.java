package com.sushil.electronic.store.controllers;

import com.sushil.electronic.store.dtod.CategoryDto;
import com.sushil.electronic.store.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategoryDto = categoryService.createCategory(categoryDto);
        return new ResponseEntity(savedCategoryDto, HttpStatus.CREATED);
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity updateCategory(@PathVariable,@RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategoryDto = categoryService.createCategory(categoryDto);
        return new ResponseEntity(savedCategoryDto, HttpStatus.CREATED);
    }
}
