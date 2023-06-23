package com.example.storetablereservation.review.repository;

import com.example.storetablereservation.review.entity.Review;
import com.example.storetablereservation.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<List<Review>> findByStore(Store store);
}
