package com.keeghan.eShop.repositories;

import com.keeghan.eShop.domain.entities.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long>, PagingAndSortingRepository<Review,Long> {
}
