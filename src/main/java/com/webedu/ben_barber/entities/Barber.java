package com.webedu.ben_barber.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "tb_barbers")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Barber {

    public Barber(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "barber_seq")
    @SequenceGenerator(name = "barber_seq", sequenceName = "barber_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "barber")
    private List<Agendate> agendate = new ArrayList<>();
}
