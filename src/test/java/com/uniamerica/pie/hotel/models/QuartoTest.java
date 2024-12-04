package com.uniamerica.pie.hotel.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class QuartoTest {

    @Test
    public void testQuartoModel() {
        Hotel hotel = new Hotel("Hotel Teste", "Rua Teste", "Descrição Teste", 10);
        Quarto quarto = new Quarto("101", "quarto solteiro", "Disponível", 1, 1, hotel);

        
        assertEquals("101", quarto.getNumero());
        assertEquals("quarto solteiro", quarto.getTipo());
        assertEquals("Disponível", quarto.getStatus());
        assertEquals(1, quarto.getCapacidadeMinima());
        assertEquals(1, quarto.getCapacidadeMaxima());
        assertEquals(hotel, quarto.getHotel());
    }
}
