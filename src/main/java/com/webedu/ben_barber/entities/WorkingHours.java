package com.webedu.ben_barber.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "tb_working_hours")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class WorkingHours {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "working_hours_seq")
    @SequenceGenerator(name = "working_hours_seq", sequenceName = "working_hours_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "barber_id", nullable = false)
    private Barber barber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;
}