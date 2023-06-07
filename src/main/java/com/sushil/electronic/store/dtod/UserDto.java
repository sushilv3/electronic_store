package com.sushil.electronic.store.dtod;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;
    private String name;
    private String gender;
    private String email;
    private String password;
    private String about;
    private String imageName;
}
