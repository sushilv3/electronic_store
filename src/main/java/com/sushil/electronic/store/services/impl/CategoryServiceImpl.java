package com.sushil.electronic.store.services.impl;

import com.sushil.electronic.store.dtod.CategoryDto;
import com.sushil.electronic.store.dtod.PageableResponse;
import com.sushil.electronic.store.dtod.UserDto;
import com.sushil.electronic.store.entities.Category;
import com.sushil.electronic.store.exceptions.ResourceNotFoundException;
import com.sushil.electronic.store.repositories.CategoryRepository;
import com.sushil.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        String catId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(catId);
        Category category = modelMapper.map(categoryDto, Category.class);
        Category saveCategory = categoryRepository.save(category);
        return modelMapper.map(saveCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found exception "));
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        category.setTitle(categoryDto.getTitle());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found exception "));
        categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? (Sort.by(sortBy).ascending()) : (Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        List<CategoryDto> categoryDtos = page.getContent().stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        PageableResponse<CategoryDto> response = new PageableResponse<>();
        response.setLastPage(page.isLast());
        response.setContent(categoryDtos);
        response.setTotalPages(page.getTotalPages());
        response.setTotalElement(page.getTotalElements());
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        return response;
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found exception "));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public PageableResponse<UserDto> searchCategory(String keyword, int pageNumber, int pageSize, String sortBy, String sortDir) {
        return null;
    }
}
