package com.webedu.ben_barber.repositories;

import com.webedu.ben_barber.entities.ScheduleHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HoursRepository extends JpaRepository<ScheduleHours, Long> {

    Optional<ScheduleHours> findByDateAndHourTime(LocalDate date, LocalTime hourTime);

    List<ScheduleHours> findByBarberIdAndDate(Long barberId, LocalDate date);
}
