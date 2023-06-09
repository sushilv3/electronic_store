package com.sushil.electronic.store.services;

import com.sushil.electronic.store.dtod.CategoryDto;
import com.sushil.electronic.store.dtod.PageableResponse;
import com.sushil.electronic.store.dtod.UserDto;

public interface CategoryService {


    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, String categoryId);

    void deleteCategory(String categoryId);

    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    CategoryDto getCategoryById(String categoryId);

    PageableResponse<UserDto> searchCategory(String keyword, int pageNumber, int pageSize, String sortBy, String sortDir);
}
