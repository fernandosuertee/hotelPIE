package com.uniamerica.pie.hotel.repositories;


import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class ReservaRepositoryTest {
/*
    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private HotelRepository hotelRepository;

    private Hotel hotel;
    private Quarto quarto;

    @BeforeEach
    public void setup() {
        hotel = hotelRepository.save(new Hotel("Hotel Teste", "Rua Teste", "Descrição Teste", 10));
        quarto = quartoRepository.save(new Quarto("101", "quarto solteiro", "Disponível", 1, 1, hotel));
    }

    @Test
    public void testFindByQuartoAndDataCheckInLessThanEqualAndDataCheckOutGreaterThanEqual() {
        Reserva reserva = reservaRepository.save(new Reserva(null, hotel, quarto, LocalDate.now(), LocalDate.now().plusDays(3), 1, null));
        List<Reserva> reservas = reservaRepository.findByQuartoAndDataCheckInLessThanEqualAndDataCheckOutGreaterThanEqual(
                quarto, LocalDate.now().plusDays(2), LocalDate.now());

        assertFalse(reservas.isEmpty());
        assertEquals(1, reservas.size());
    }

    @Test
    public void testFindByHotelIdAndQuartoIdAndDataCheckInLessThanEqualAndDataCheckOutGreaterThanEqual() {
        Reserva reserva = reservaRepository.save(new Reserva(null, hotel, quarto, LocalDate.now(), LocalDate.now().plusDays(3), 1, null));
        List<Reserva> reservas = reservaRepository.findByHotelIdAndQuartoIdAndDataCheckInLessThanEqualAndDataCheckOutGreaterThanEqual(
                hotel.getId(), quarto.getId(), LocalDate.now().plusDays(3), LocalDate.now());

        assertFalse(reservas.isEmpty());
        assertEquals(1, reservas.size());
    }
    */

}
