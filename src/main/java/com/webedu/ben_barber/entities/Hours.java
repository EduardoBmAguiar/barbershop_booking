package com.webedu.ben_barber.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "tb_hours")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Hours {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hour_seq")
    @SequenceGenerator(name = "hour_seq", sequenceName = "hour_seq", allocationSize = 1)
    private Long id;

    private LocalDate date;
    private LocalTime hour;
    private Boolean available = true;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "barber_id", nullable = false)
    private Barber barber;
}
