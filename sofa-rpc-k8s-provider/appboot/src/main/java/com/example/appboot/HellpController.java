
package com.example.appboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HellpController {

    @GetMapping("/get")
    public String get(){
        return "HELLO";
    }
}