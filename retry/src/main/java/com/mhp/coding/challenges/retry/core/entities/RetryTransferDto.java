package com.mhp.coding.challenges.retry.core.entities;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RetryTransferDto {

    //retry count
    public Integer count = 0;
    //retry content
    public String message;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
