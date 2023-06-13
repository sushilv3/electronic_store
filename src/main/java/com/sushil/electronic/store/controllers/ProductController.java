package com.sushil.electronic.store.controllers;

import com.sushil.electronic.store.dtod.ImageResponse;
import com.sushil.electronic.store.dtod.ProductDto;
import com.sushil.electronic.store.dtod.UserDto;
import com.sushil.electronic.store.services.FileService;
import com.sushil.electronic.store.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;
    @Value("${product.image.path}")
    private String imagePath;

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

    //upload image
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(@PathVariable String productId, @RequestParam("productImage") MultipartFile image) throws IOException {
        String fileName = fileService.uploadFile(image, imagePath);
        ProductDto productDto = productService.getProductById(productId);
        logger.info("prduct file name : "+fileName);
        productDto.setProductImage(fileName);
        logger.info("set file name : "+productDto.getProductImage());
       ProductDto updatedProduct= productService.updateProduct(productId,productDto);
       ImageResponse response= ImageResponse.builder().imageName(updatedProduct.getProductImage()).success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    //serve image
    @GetMapping("/image/{productId}")
    public void serveProductImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto productDto = productService.getProductById(productId);
        logger.info("inside serve product image name : " + productDto.getProductImage());
        InputStream resource = fileService.getResource(imagePath, productDto.getProductImage());
        logger.info("get served product name : "+productDto.getProductImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
