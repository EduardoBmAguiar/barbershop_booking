package com.webedu.ben_barber.repositories;

import com.webedu.ben_barber.entities.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long> {

    Optional<WorkingHours> findByBarberIdAndDayOfWeek(Long barberId, DayOfWeek dayOfWeek);

    List<WorkingHours> findByBarberIdOrderByDayOfWeekAsc(Long barberId);
}