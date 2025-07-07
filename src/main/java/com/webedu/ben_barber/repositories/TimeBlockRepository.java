package com.webedu.ben_barber.repositories;

import com.webedu.ben_barber.entities.TimeBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeBlockRepository extends JpaRepository<TimeBlock, Long> {

    @Query("SELECT tb FROM TimeBlock tb WHERE tb.blockDate = :date AND (tb.barber.id = :barberId OR tb.barber IS NULL)")
    List<TimeBlock> findByDateAndBarberOrGlobal(@Param("date") LocalDate date, @Param("barberId") Long barberId);
}