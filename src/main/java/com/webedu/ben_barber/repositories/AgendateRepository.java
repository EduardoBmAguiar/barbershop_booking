package com.webedu.ben_barber.repositories;

import com.webedu.ben_barber.entities.Agendate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface AgendateRepository extends JpaRepository<Agendate, Long> {

    boolean existsByChosenDate(Instant chosenDate);

}
