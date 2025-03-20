package com.webedu.ben_barber.config;

import com.webedu.ben_barber.services.HoursAvailable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public List<LocalDateTime> hours() {
        HoursAvailable hoursAvailable = new HoursAvailable();
        return hoursAvailable.hoursAvailable;
    }
}
