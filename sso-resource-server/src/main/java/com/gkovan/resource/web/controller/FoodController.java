package com.gkovan.resource.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gkovan.resource.persistence.model.Food;
import com.gkovan.resource.service.IFoodService;
import com.gkovan.resource.web.dto.FoodDto;

@RestController
public class FoodController {
	
    private IFoodService foodService;

    public FoodController(IFoodService foodService) {
        this.foodService = foodService;
    }
    
    @GetMapping("/api/favorite-food")
    public Collection<FoodDto> findAll(@AuthenticationPrincipal Jwt principal) {
    	
    	String loggedInUser = principal.getClaimAsString("preferred_username");
    	
        Iterable<Food> foods = this.foodService.findAll();
        
        List<Food> filterFoodsByUser = StreamSupport.stream(foods.spliterator(), false)
        	      .filter(food -> food.getUser().equals(loggedInUser))
        	      .collect(Collectors.toList());
        
        List<FoodDto> foodDtos = new ArrayList<>();
        filterFoodsByUser.forEach(food -> foodDtos.add(convertToDto(food)));
        return foodDtos;
    }
    
    protected FoodDto convertToDto(Food entity) {
        FoodDto dto = new FoodDto(entity.getId(), entity.getUser(), entity.getFood());
        return dto;
    }

}
