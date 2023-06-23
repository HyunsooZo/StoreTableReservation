package com.example.storetablereservation.store.repository;


import com.example.storetablereservation.store.entity.Store;
import com.example.storetablereservation.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {
    List<Store> findByStoreNameContains(String storeName);
    Optional<Store> findByUser(Users user);
}
