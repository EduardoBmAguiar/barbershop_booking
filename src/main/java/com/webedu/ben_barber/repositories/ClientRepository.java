package com.webedu.ben_barber.repositories;

import com.webedu.ben_barber.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
//    UserDetails findByEmail(String email);
}
