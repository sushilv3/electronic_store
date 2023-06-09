package com.sushil.electronic.store.controllers;

import com.sushil.electronic.store.dtod.ApiResponseMessage;
import com.sushil.electronic.store.dtod.ImageResponse;
import com.sushil.electronic.store.dtod.PageableResponse;
import com.sushil.electronic.store.dtod.UserDto;
import com.sushil.electronic.store.services.FileService;
import com.sushil.electronic.store.services.UserService;
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
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/")
    public ResponseEntity createUser(@RequestBody @Valid UserDto userDto) {
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable String userId, @RequestBody UserDto userDto) {
        UserDto user = userService.updateUser(userDto, userId);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage.builder().message("user is deleted successfully ").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity(message, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllUser(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                     @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                     @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
                                     @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return new ResponseEntity(userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/getUserByUserId/{userId}")
    public ResponseEntity getUserById(@PathVariable String userId) {
        UserDto user = userService.getUserBy(userId);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity getUserByEmail(@PathVariable String email) {
        UserDto user = userService.getUserByEmail(email);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity getUserByNAme(@PathVariable String keyword, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                        @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
                                        @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<UserDto> users = userService.searchUser(keyword, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity(users, HttpStatus.OK);
    }

    //upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@PathVariable String userId, @RequestParam("userImage") MultipartFile image) throws IOException {
        String imageName = fileService.uploadFile(image, imageUploadPath);
        UserDto userDto = userService.getUserBy(userId);
        userDto.setImageName(imageName);
        userService.updateUser(userDto, userId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    //serve user image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto userDto = userService.getUserBy(userId);
        logger.info("user image name : " + userDto.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, userDto.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
