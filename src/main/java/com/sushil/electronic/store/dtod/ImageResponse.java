package com.sushil.electronic.store.dtod;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ImageResponse {
    private String imageName;
    private boolean success;
    private HttpStatus status;

}
