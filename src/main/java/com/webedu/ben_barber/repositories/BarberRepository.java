package com.webedu.ben_barber.repositories;

import com.webedu.ben_barber.entities.Barber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {
//    UserDetails findByEmail(String email);
}
