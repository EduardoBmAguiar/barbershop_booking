package com.webedu.ben_barber.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tb_time_block")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TimeBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "time_block_seq")
    @SequenceGenerator(name = "time_block_seq", sequenceName = "time_block_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "barber_id")
    private Barber barber;

    @Column(nullable = false)
    private LocalDate blockDate;

    private LocalTime startTime;
    private LocalTime endTime;

    @Column(nullable = false)
    private String reason;
}