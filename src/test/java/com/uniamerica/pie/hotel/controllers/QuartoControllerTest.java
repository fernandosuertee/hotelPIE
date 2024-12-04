package com.uniamerica.pie.hotel.controllers;


import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class QuartoControllerTest {

	/*
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private HotelRepository hotelRepository;

    private Hotel hotel;

    @BeforeEach
    public void setup() {
        hotel = new Hotel("Hotel Teste", "Rua Teste", "Descrição Teste", 10);
        hotel = hotelRepository.save(hotel);
    }

    @Test
    public void testListarTodosOsQuartos() {
        Quarto quarto = new Quarto("104", "quarto casal", "Disponível", 1, 2, hotel);
        quartoRepository.save(quarto);

        ResponseEntity<Quarto[]> response = restTemplate.getForEntity("/quartos", Quarto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    public void testCadastrarQuarto() {
        Quarto quarto = new Quarto("105", "quarto triplo", "Disponível", 1, 3, hotel);
        ResponseEntity<Quarto> response = restTemplate.postForEntity("/quartos", quarto, Quarto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("105", response.getBody().getNumero());
    }
    */

}
