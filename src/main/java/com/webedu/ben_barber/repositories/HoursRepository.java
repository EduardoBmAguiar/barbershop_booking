package com.webedu.ben_barber.repositories;

import com.webedu.ben_barber.entities.ScheduleHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoursRepository extends JpaRepository<ScheduleHours, Long> {
}
