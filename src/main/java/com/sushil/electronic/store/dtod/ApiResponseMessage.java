package com.sushil.electronic.store.dtod;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ApiResponseMessage {
    private String message;
    private boolean success;
    private HttpStatus status;

}
