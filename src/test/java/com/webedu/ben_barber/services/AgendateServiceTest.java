package com.webedu.ben_barber.services;

import static org.mockito.ArgumentMatchers.any;

class AgendateServiceTest {

//    @Mock
//    private AppointmentRepository agendateRepository;
//
//    @Mock
//    private ClientRepository clientRepository;
//
//    @Mock
//    private OptionRepository optionRepository;
//
//    @Mock
//    private HoursGeneratorService scheduleHoursAvailable;
//
//    @InjectMocks
//    private AppointmentService agendateService;
//
//    private Appointment agendate;
//    private Client client;
//    private Option option;
//    private LocalDateTime validDate;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        client = new Client();
//        client.setId(1L);
//        client.setName("EduTest");
//
//        option = new Option();
//        option.setId(1L);
//        option.setName("Barba");
//
//        agendate = new Appointment();
//        agendate.setId(1L);
//        agendate.setIdClient(client.getId());
//        agendate.setIdOption(option.getId());
//
//        validDate = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().plusDays(2).getDayOfMonth(), 9, 0);
//
//        scheduleHoursAvailable.scheduleHoursAvailable = new ArrayList<>();
//    }

//    @Test
//    @DisplayName("Added agendate success")
//    void addAgendateSuccess() {
//
//        when(clientRepository.findById(agendate.getIdClient())).thenReturn(Optional.of(client));
//        when(optionRepository.findById(agendate.getIdOption())).thenReturn(Optional.of(option));
//        when(agendateRepository.save(any(Appointment.class))).thenReturn(agendate);
//
////        scheduleHoursAvailable.scheduleHoursAvailable.add(validDate);
//
////        Appointment result = agendateService.addAgendate(agendate, validDate);
//
////        assertNotNull(result);
////        assertEquals(client, result.getClient());
////        assertEquals(option, result.getOption());
////        assertEquals(validDate, result.getChosenDate());
////        verify(agendateRepository, times(1)).save(agendate);
//    }

//    @Test
//    @DisplayName("Client Not Found")
//    void addAgendateUserNotFound() {
//
//        when(clientRepository.findById(agendate.getIdClient())).thenReturn(Optional.empty());
//
//        try {
//            agendateService.addAgendate(agendate, validDate);
//            fail("Should have launched ResourceNotFoundException");
//        } catch (ResourceNotFoundException e) {
//            assertEquals("Resource not found: Client Not Found", e.getMessage());
//        }
//    }
//
//    @Test
//    @DisplayName("Option Not Found")
//    void testAddAgendateOptionNotFound() {
//
//        when(clientRepository.findById(agendate.getIdClient())).thenReturn(Optional.of(client));
//        when(optionRepository.findById(agendate.getIdOption())).thenReturn(Optional.empty());
//
//        try {
//            agendateService.addAgendate(agendate, validDate);
//            fail("Should have launched ResourceNotFoundException");
//        } catch (ResourceNotFoundException e) {
//            assertEquals("Resource not found: Option Not Found", e.getMessage());
//        }
//    }
//
//    @Test
//    @DisplayName("Date Unavailable")
//    void testAddAgendateInvalidDate() {
//
//        when(clientRepository.findById(agendate.getIdClient())).thenReturn(Optional.of(client));
//        when(optionRepository.findById(agendate.getIdOption())).thenReturn(Optional.of(option));
//
//        try {
//            agendateService.addAgendate(agendate, validDate);
//            fail("Should have launched InvalidDateException");
//        } catch (InvalidDateException e) {
//            assertEquals("Date is not on our agenda", e.getMessage());
//        }
//    }
}