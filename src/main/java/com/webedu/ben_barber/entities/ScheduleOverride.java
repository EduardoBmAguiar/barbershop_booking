package com.webedu.ben_barber.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tb_schedule_overrides")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ScheduleOverride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "barber_id")
    private Barber barber;

    @Column(nullable = false)
    private LocalDate overrideDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    public ScheduleOverride(Barber barber, LocalDate overrideDate, LocalTime startTime, LocalTime endTime) {
        this.barber = barber;
        this.overrideDate = overrideDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}