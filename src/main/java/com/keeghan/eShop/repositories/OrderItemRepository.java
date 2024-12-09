package com.keeghan.eShop.repositories;

import com.keeghan.eShop.domain.entities.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Long>, PagingAndSortingRepository<OrderItem,Long> {
}
