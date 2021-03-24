package com.gkovan.resource.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.gkovan.resource.persistence.model.Food;

public interface IFoodRepository extends PagingAndSortingRepository<Food, String> {

}
