package com.kredimizik.rapiddealapigit.repos;

import com.kredimizik.rapiddealapigit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
