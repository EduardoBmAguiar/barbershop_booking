package com.webedu.ben_barber.services;

import com.webedu.ben_barber.repositories.TimeBlockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Component
public class TimeBlockArchiver {

    private final TimeBlockRepository timeBlockRepository;

    public TimeBlockArchiver(TimeBlockRepository timeBlockRepository) {
        this.timeBlockRepository = timeBlockRepository;
    }

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void archiveOldTimeBlocks() {
        log.info("Starting job to archive old TimeBlocks...");
        LocalDate today = LocalDate.now();
        timeBlockRepository.deactivateBlocksBefore(today);
        log.info("TimeBlocks archiving job completed.");
    }
}