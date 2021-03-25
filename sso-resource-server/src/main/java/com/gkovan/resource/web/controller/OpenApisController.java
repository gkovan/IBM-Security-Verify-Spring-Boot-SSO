package com.gkovan.resource.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gkovan.resource.web.dto.FoodDto;

@RestController
public class OpenApisController {

    @GetMapping("/open/api/test")
    public FoodDto test() {
    	FoodDto food = new FoodDto(Long.valueOf(1), "gk", "carrots");
    	return food;
    }
}
