package com.webedu.ben_barber.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.webedu.ben_barber.enums.AgendateStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_agendates")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Agendate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agendate_seq")
    @SequenceGenerator(name = "agendate_seq", sequenceName = "agendate_seq", allocationSize = 1)
    private Long id;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long idDate;

    @OneToOne
    @JoinColumn(name = "hour_id")
    private ScheduleHours scheduleHours;

    private AgendateStatus status;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long idClient;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long idBarber;

    @ManyToOne
    @JoinColumn(name = "barber_id")
    private Barber barber;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long idOption;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private Option option;

    public Agendate(Long id, ScheduleHours chosenDate, AgendateStatus status, Client client, Barber barber, Option option) {
        this.id = id;
        this.scheduleHours = chosenDate;
        this.status = status;
        this.client = client;
        this.barber = barber;
        this.option = option;
    }
}
