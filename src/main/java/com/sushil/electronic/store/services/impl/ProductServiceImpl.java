package com.sushil.electronic.store.services.impl;

import com.sushil.electronic.store.dtod.PageableResponse;
import com.sushil.electronic.store.dtod.ProductDto;
import com.sushil.electronic.store.dtod.UserDto;
import com.sushil.electronic.store.entities.Category;
import com.sushil.electronic.store.entities.Product;
import com.sushil.electronic.store.entities.User;
import com.sushil.electronic.store.exceptions.ResourceNotFoundException;
import com.sushil.electronic.store.repositories.CategoryRepository;
import com.sushil.electronic.store.repositories.ProductRepository;
import com.sushil.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);
        Product product = modelMapper.map(productDto, Product.class);
        product.setAddedDate(new Date());
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);

    }

    @Override
    public ProductDto updateProduct(String productId, ProductDto productDto) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found with this : " + productId + " id"));
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setStock(productDto.isStock());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setTitle(productDto.getTitle());
        product.setLive(productDto.isLive());
        product.setProductImage(productDto.getProductImage());
        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDto.class);

    }

    @Override
    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found with this : " + productId + " id"));
        productRepository.delete(product);
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page page = productRepository.findAll(pageable);
        List<Product> products = page.getContent();
        List<ProductDto> productDtos = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        PageableResponse<ProductDto> response = new PageableResponse<>();
        response.setContent(productDtos);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElement(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }

    @Override
    public ProductDto getProductById(String id) {
        Product foundProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product not found with this : " + id + " id"));
        return modelMapper.map(foundProduct, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page page = productRepository.findByTitleContaining(subTitle, pageable);
        List<Product> products = page.getContent();
        List<ProductDto> productDtos = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        PageableResponse<ProductDto> response = new PageableResponse<>();
        response.setContent(productDtos);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElement(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }

    @Override
    public PageableResponse<ProductDto> getAllLiveTrue(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page page = productRepository.findByLiveTrue(pageable);
        List<Product> products = page.getContent();
        List<ProductDto> productDtos = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        PageableResponse<ProductDto> response = new PageableResponse<>();
        response.setContent(productDtos);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElement(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with : " + categoryId));
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);
        Product product = modelMapper.map(productDto, Product.class);
        product.setAddedDate(new Date());
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProductCategory(String productId, String categoryId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with this id : " + productId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with this is :" + categoryId));
        product.setCategory(category);
        Product updatedProduct = productRepository.save(product);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return productDto;
    }

    @Override
    public PageableResponse<ProductDto> getAllProductByCategoryId(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with this is :" + categoryId));
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByCategory(category, pageable);
        List<Product> products = page.getContent();
        List<ProductDto> productDtos = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        PageableResponse<ProductDto> response = new PageableResponse<>();
        response.setContent(productDtos);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElement(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }
}
