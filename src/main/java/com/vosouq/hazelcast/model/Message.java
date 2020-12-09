package com.vosouq.hazelcast.model;

/*
 Author: MohammadReza Ahmadi,  "m.reza79ahmadi@gmail.com"
 7/31/2020, 10:44 PM
*/


import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Duration;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class Message implements Serializable {
    private String value;
    private Duration duration;

    public Message(String value) {
        this.value = value;
    }
}
