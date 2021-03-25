package com.gkovan.client.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import com.gkovan.client.web.model.FoodModel;

@Controller
public class FoodClientController {
	
    @Value("${resourceserver.food.url}")
    private String foodApiUrl;

    @Autowired
    private WebClient webClient;
    
    @GetMapping("/favorite-foods")
    public String getFavoriteFoods(Model model) {
    	List<FoodModel> favoriteFoods = this.webClient.get()
                .uri(foodApiUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<FoodModel>>() {
                })
                .block();
                
        model.addAttribute("foods", favoriteFoods);
        return "favorite-foods";
    }
}
