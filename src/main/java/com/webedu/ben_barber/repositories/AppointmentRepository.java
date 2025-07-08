package com.webedu.ben_barber.repositories;

import com.webedu.ben_barber.entities.Appointment;
import com.webedu.ben_barber.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.barber.id = :barberId AND a.scheduleHours.date = :date AND a.status != com.webedu.ben_barber.enums.AppointmentStatus.CANCELED")
    List<Appointment> findActiveAppointmentsByBarberAndDate(@Param("barberId") Long barberId, @Param("date") LocalDate date);

    List<Appointment> findByBarberIdAndScheduleHours_DateOrderByScheduleHours_HourTimeAsc(Long barberId, LocalDate date);

    List<Appointment> findByBarberIdAndScheduleHours_DateAfterAndStatusNot(
            Long barberId,
            LocalDate date,
            AppointmentStatus status
    );
}
