package com.webedu.ben_barber.services;

import com.webedu.ben_barber.entities.Agendate;
import com.webedu.ben_barber.entities.Option;
import com.webedu.ben_barber.entities.User;
import com.webedu.ben_barber.exceptions.InvalidDateException;
import com.webedu.ben_barber.exceptions.ResourceNotFoundException;
import com.webedu.ben_barber.repositories.AgendateRepository;
import com.webedu.ben_barber.repositories.OptionRepository;
import com.webedu.ben_barber.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AgendateServiceTest {

    @Mock
    private AgendateRepository agendateRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OptionRepository optionRepository;

    @Mock
    private HoursAvailable hoursAvailable;

    @InjectMocks
    private AgendateService agendateService;

    private Agendate agendate;
    private User user;
    private Option option;
    private LocalDateTime validDate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("EduTest");

        option = new Option();
        option.setId(1L);
        option.setName("Barba");

        agendate = new Agendate();
        agendate.setId(1L);
        agendate.setIdClient(user.getId());
        agendate.setIdOption(option.getId());

        validDate = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().plusDays(2).getDayOfMonth(), 9, 0);

        hoursAvailable.hoursAvailable = new ArrayList<>();
    }

    @Test
    @DisplayName("Added agendate success")
    void addAgendateSuccess() {

        when(userRepository.findById(agendate.getIdClient())).thenReturn(Optional.of(user));
        when(optionRepository.findById(agendate.getIdOption())).thenReturn(Optional.of(option));
        when(agendateRepository.save(any(Agendate.class))).thenReturn(agendate);

        hoursAvailable.hoursAvailable.add(validDate);

        Agendate result = agendateService.addAgendate(agendate, validDate);

        assertNotNull(result);
        assertEquals(user, result.getClient());
        assertEquals(option, result.getOption());
        assertEquals(validDate, result.getChosenDate());
        verify(agendateRepository, times(1)).save(agendate);
    }

    @Test
    @DisplayName("User Not Found")
    void addAgendateUserNotFound() {

        when(userRepository.findById(agendate.getIdClient())).thenReturn(Optional.empty());

        try {
            agendateService.addAgendate(agendate, validDate);
            fail("Should have launched ResourceNotFoundException");
        } catch (ResourceNotFoundException e) {
            assertEquals("Resource not found: User Not Found", e.getMessage());
        }
    }

    @Test
    @DisplayName("Option Not Found")
    void testAddAgendateOptionNotFound() {

        when(userRepository.findById(agendate.getIdClient())).thenReturn(Optional.of(user));
        when(optionRepository.findById(agendate.getIdOption())).thenReturn(Optional.empty());

        try {
            agendateService.addAgendate(agendate, validDate);
            fail("Should have launched ResourceNotFoundException");
        } catch (ResourceNotFoundException e) {
            assertEquals("Resource not found: Option Not Found", e.getMessage());
        }
    }

    @Test
    @DisplayName("Date Unavailable")
    void testAddAgendateInvalidDate() {

        when(userRepository.findById(agendate.getIdClient())).thenReturn(Optional.of(user));
        when(optionRepository.findById(agendate.getIdOption())).thenReturn(Optional.of(option));

        try {
            agendateService.addAgendate(agendate, validDate);
            fail("Should have launched InvalidDateException");
        } catch (InvalidDateException e) {
            assertEquals("Date is not on our agenda", e.getMessage());
        }
    }
}