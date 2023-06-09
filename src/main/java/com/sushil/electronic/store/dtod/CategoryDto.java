package com.sushil.electronic.store.dtod;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private String categoryId;
    @NotBlank
    @Min(value = 4, message = "title must be of minimum 4 characters !! ")
    private String title;
    @NotBlank(message = "Description required!!")
    private String description;
    private String coverImage;
}
