package com.webedu.ben_barber.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ScheduleHours {

    public ScheduleHours(LocalDate date, LocalTime hour) {
        this.date = date;
        this.hourTime = hour;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hour_seq")
    @SequenceGenerator(name = "hour_seq", sequenceName = "hour_seq", allocationSize = 1)
    private Long id;

    private LocalDate date;

    private LocalTime hourTime;

    private Boolean available = true;

    @JsonIgnore
    @OneToOne(mappedBy = "scheduleHours")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "barber_id")
    private Barber barber;
}
