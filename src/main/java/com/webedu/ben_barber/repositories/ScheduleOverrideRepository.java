package com.webedu.ben_barber.repositories;

import com.webedu.ben_barber.entities.ScheduleOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ScheduleOverrideRepository extends JpaRepository<ScheduleOverride, Long> {

    Optional<ScheduleOverride> findByBarberIdAndOverrideDate(Long barberId, LocalDate date);
}