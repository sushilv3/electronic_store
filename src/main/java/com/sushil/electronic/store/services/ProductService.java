package com.sushil.electronic.store.services;

import com.sushil.electronic.store.dtod.PageableResponse;
import com.sushil.electronic.store.dtod.ProductDto;
import com.sushil.electronic.store.entities.Product;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(String productId,ProductDto productDto);

    void deleteProduct(String productId);
    PageableResponse<ProductDto> getAllProduct(int pageNumber,int pageSize, String sortBy,String sortDir);

    ProductDto getProductById(String id);

    PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize, String sortBy,String sortDir);
    PageableResponse<ProductDto> getAllLiveTrue(int pageNumber,int pageSize, String sortBy,String sortDir);

    //create product with category
    ProductDto createWithCategory(ProductDto productDto,String categoryId);
    //update category of the product
    ProductDto updateProductCategory(String productId,String categoryId);

    PageableResponse<ProductDto> getAllProductByCategoryId(String categoryId,int pageNumber,int pageSize,String sortBy, String sortDir);

}
