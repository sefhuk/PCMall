package com.team5.project2.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RootController {

    @GetMapping
    @ResponseBody
    public String index() {
        return "index,, comming soon!";
    }
}
