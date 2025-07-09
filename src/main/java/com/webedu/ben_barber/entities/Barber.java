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
public class Barber /*implements UserDetails*/ {

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

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "barber", cascade = CascadeType.REFRESH)
    private List<Appointment> appointment = new ArrayList<>();

//    private UserRole role;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        if (this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_BARBER"));
//        else return List.of(new SimpleGrantedAuthority("ROLE_BARBER"), new SimpleGrantedAuthority("ROLE_USER"));
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
////        return UserDetails.super.isAccountNonExpired();
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
////        return UserDetails.super.isAccountNonLocked();
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
////        return UserDetails.super.isCredentialsNonExpired();
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
////        return UserDetails.super.isEnabled();
//        return true;
//    }
}
