package com.uniamerica.pie.hotel.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.uniamerica.pie.hotel.models.Hotel;
import com.uniamerica.pie.hotel.models.Quarto;
import com.uniamerica.pie.hotel.repositories.QuartoRepository;

public class QuartoServiceTest {

    @Mock
    private QuartoRepository quartoRepository;

    @InjectMocks
    private QuartoService quartoService;

    public QuartoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarQuarto_Sucesso() {
        Hotel hotel = new Hotel("Hotel Teste", "Endereço", "Descrição", 10);
        hotel.setId(1L);

        Quarto quarto = new Quarto("101", "Quarto Casal", "Disponível", 1, 2, hotel);
        when(quartoRepository.existsByNumeroAndHotelId("101", 1L))
            .thenReturn(false);
        when(quartoRepository.save(quarto)).thenReturn(quarto);

        Quarto resultado = quartoService.cadastrarQuarto(quarto);

        assertNotNull(resultado);
        assertEquals("101", resultado.getNumero());
        verify(quartoRepository, times(1)).save(quarto);
    }

    @Test
    public void testCadastrarQuarto_NumeroJaExistente() {
        Hotel hotel = new Hotel("Hotel Teste", "Endereço", "Descrição", 10);
        hotel.setId(1L);

        Quarto quarto = new Quarto("101", "Quarto Casal", "Disponível", 1, 2, hotel);
        when(quartoRepository.existsByNumeroAndHotelId("101", 1L))
            .thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            quartoService.cadastrarQuarto(quarto);
        });
    }
}
