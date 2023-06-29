package com.antgroup.antchain.xbuilders.web.controller;

/**
 * @Author 彦筠
 * @Date 2023/5/8 16:52
 * @Version 2.0.6
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "Greetings from X-Builders!";
    }

}