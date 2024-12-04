package com.uniamerica.pie.hotel.controllers;


import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ReservaControllerTest {

	/*
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    private Hotel hotel;
    private Quarto quarto;

    @BeforeEach
    public void setup() {
        hotel = hotelRepository.save(new Hotel("Hotel Teste", "Rua Teste", "Descrição Teste", 10));
        quarto = quartoRepository.save(new Quarto("101", "quarto solteiro", "Disponível", 1, 1, hotel));
    }

    @Test
    public void testCadastrarReserva() {
        Reserva reserva = new Reserva(null, hotel, quarto, LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), 1, null);
        ResponseEntity<Reserva> response = restTemplate.postForEntity("/reservas", reserva, Reserva.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(quarto.getId(), response.getBody().getQuarto().getId());
    }

    @Test
    public void testListarTodasAsReservas() {
        ResponseEntity<Reserva[]> response = restTemplate.getForEntity("/reservas", Reserva[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    */

}
