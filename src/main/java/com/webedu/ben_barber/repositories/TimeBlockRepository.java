package com.webedu.ben_barber.repositories;

import com.webedu.ben_barber.entities.TimeBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeBlockRepository extends JpaRepository<TimeBlock, Long> {

    @Query("SELECT tb FROM TimeBlock tb " +
            "LEFT JOIN FETCH tb.barber b " +
            "WHERE tb.active = true " +
            "AND tb.blockDate = :date " +
            "AND (b.id = :barberId OR tb.barber IS NULL)")
    List<TimeBlock> findAllActiveBySpecificDate(@Param("date") LocalDate date, @Param("barberId") Long barberId);

    @Query("SELECT tb FROM TimeBlock tb " +
            "LEFT JOIN FETCH tb.barber b " +
            "WHERE tb.active = true " +
            "AND tb.blockDate >= :startDate " +
            "AND (b.id = :barberId OR tb.barber IS NULL)")
    List<TimeBlock> findActiveByDateAndBarberOrGlobal(@Param("startDate") LocalDate startDate, @Param("barberId") Long barberId);

    @Modifying
    @Query("UPDATE TimeBlock tb SET tb.active = false WHERE tb.blockDate < :date AND tb.active = true")
    void deactivateBlocksBefore(@Param("date") LocalDate date);
}