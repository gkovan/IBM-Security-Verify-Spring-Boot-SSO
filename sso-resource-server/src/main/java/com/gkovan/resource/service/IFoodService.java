package com.gkovan.resource.service;

import com.gkovan.resource.persistence.model.Food;

public interface IFoodService {
    Iterable<Food> findAll();
}
