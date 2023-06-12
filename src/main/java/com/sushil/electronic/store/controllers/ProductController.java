package com.sushil.electronic.store.controllers;

import com.sushil.electronic.store.dtod.ProductDto;
import com.sushil.electronic.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity createProduct(@RequestBody ProductDto productDto) {
        return new ResponseEntity(productService.createProduct(productDto), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity updateProduct(@PathVariable String productId, @RequestBody ProductDto productDto) {
        return new ResponseEntity(productService.updateProduct(productId, productDto), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity getAllProduct(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                        @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                        @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return new ResponseEntity(productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity getProductById(@PathVariable String productId) {
        return new ResponseEntity(productService.getProductById(productId), HttpStatus.OK);
    }

    @GetMapping("/getProductAlive/")
    public ResponseEntity getAllLiveProducts(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                             @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                             @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                             @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return new ResponseEntity(productService.getAllLiveTrue(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }
    @GetMapping("/getProductByTitle/{subTitle}")
    public ResponseEntity getAllProductByTitle(@PathVariable String subTitle, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                               @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                               @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                               @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return new ResponseEntity(productService.searchByTitle(subTitle, pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);

    }

}
