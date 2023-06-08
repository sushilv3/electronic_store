package com.sushil.electronic.store.dtod;

import com.sushil.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;
    @Size(min = 3, max = 15, message = "Invalid Name")
    private String name;
    @Size(min = 3, max = 6, message = "Invalid gender")
    private String gender;
    //    @Email(message = "Invalid User Email")
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid User Email")
    private String email;

    @NotBlank(message = "Password is required ")
    private String password;
    @NotBlank(message = "Invalid about !! Write about something yourself.")
    private String about;
    //pattern
    //custom validator
    @ImageNameValid
    private String imageName;
}
