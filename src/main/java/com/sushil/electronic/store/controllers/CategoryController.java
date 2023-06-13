package com.sushil.electronic.store.controllers;

import com.sushil.electronic.store.dtod.*;
import com.sushil.electronic.store.services.CategoryService;
import com.sushil.electronic.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        CategoryDto savedCategoryDto = categoryService.createCategory(categoryDto);
        return new ResponseEntity(savedCategoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity updateCategory(@PathVariable String categoryId, @RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategoryDto = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity(savedCategoryDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getAll(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                 @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                 @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return new ResponseEntity(categoryService.getAll(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/getCategoryById/{catId}")
    public ResponseEntity getUserById(@PathVariable String catId) {
        CategoryDto categoryDto = categoryService.getCategoryById(catId);
        return new ResponseEntity(categoryDto, HttpStatus.OK);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity deleteUser(@PathVariable String catId) {
        categoryService.deleteCategory(catId);
        ApiResponseMessage message = ApiResponseMessage.builder().message("Category is deleted successfully ").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity(message, HttpStatus.OK);
    }

    //create product with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity createProductWithCategory(@PathVariable String categoryId, @RequestBody ProductDto productDto) {
        ProductDto savedProductDtoWithCategory = productService.createWithCategory(productDto, categoryId);
        return new ResponseEntity(savedProductDtoWithCategory, HttpStatus.CREATED);
    }

    //update category of the product
    @PutMapping("/{categoryId}/{productId}")
    public ResponseEntity updateCategoryOfProduct(@PathVariable String categoryId, @PathVariable String productId) {
        return new ResponseEntity(productService.updateProductCategory(productId, categoryId), HttpStatus.OK);
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity getProductOfCategories(@PathVariable String categoryId,
                                                 @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                 @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                 @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
      PageableResponse<ProductDto> response  =productService.getAllProductByCategoryId(categoryId,pageNumber,pageSize,sortBy,sortDir);
       return new ResponseEntity(response, HttpStatus.OK);
    }
}
