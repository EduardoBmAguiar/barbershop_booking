package com.webedu.ben_barber.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webedu.ben_barber.enums.AgendateStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "tb_agendates")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Agendate {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @Id
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant chosenDate;

    private AgendateStatus status;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;
}
