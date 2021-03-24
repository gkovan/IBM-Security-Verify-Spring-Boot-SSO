package com.gkovan.resource.service.impl;

import org.springframework.stereotype.Service;

import com.gkovan.resource.persistence.model.Food;
import com.gkovan.resource.persistence.repository.IFoodRepository;
import com.gkovan.resource.service.IFoodService;

@Service
public class FoodServiceImpl implements IFoodService {
	private IFoodRepository foodRepository;

	public FoodServiceImpl(IFoodRepository foodRepository) {
		this.foodRepository = foodRepository;
	}

	@Override
	public Iterable<Food> findAll() {
		return foodRepository.findAll();
	}

}
