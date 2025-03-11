package com.webedu.ben_barber.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_options")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Option {

    public Option(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "options")
    @SequenceGenerator(name = "options_seq", sequenceName = "options", allocationSize = 1)
    private Long id;

    private String name;

    private BigDecimal price;

    @JsonIgnore
    @OneToMany(mappedBy = "option")
    private List<Agendate> agendate = new ArrayList<>();
}
