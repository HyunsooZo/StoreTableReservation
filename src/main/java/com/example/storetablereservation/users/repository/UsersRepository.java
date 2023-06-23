package com.example.storetablereservation.users.repository;


import com.example.storetablereservation.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    long countByEmail(String email);
    Optional<Users> findByEmail(String email);
}
