package com.webedu.ben_barber.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.webedu.ben_barber.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_appointment")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_seq")
    @SequenceGenerator(name = "appointment_seq", sequenceName = "appointment_seq", allocationSize = 1)
    private Long id;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long idDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_hours_id", referencedColumnName = "id", nullable = false)
    private ScheduleHours scheduleHours;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status = com.webedu.ben_barber.enums.AppointmentStatus.SCHEDULED;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "barber_id")
    private Barber barber;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private Option option;

    public Appointment(Long id, ScheduleHours chosenDate, AppointmentStatus status, Client client, Barber barber, Option option) {
        this.id = id;
        this.scheduleHours = chosenDate;
        this.status = status;
        this.client = client;
        this.barber = barber;
        this.option = option;
    }
}
