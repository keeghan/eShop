package com.keeghan.eShop.repositories;

import com.keeghan.eShop.domain.entities.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>, PagingAndSortingRepository<Order,Long> {
}
