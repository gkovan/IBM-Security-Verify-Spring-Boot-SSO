package com.gkovan.resource.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.gkovan.resource.persistence.model.Foo;

public interface IFooRepository extends PagingAndSortingRepository<Foo, Long> {
}
