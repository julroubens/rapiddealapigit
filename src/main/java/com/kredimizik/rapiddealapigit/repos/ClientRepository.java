package com.kredimizik.rapiddealapigit.repos;

import com.kredimizik.rapiddealapigit.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {
}
