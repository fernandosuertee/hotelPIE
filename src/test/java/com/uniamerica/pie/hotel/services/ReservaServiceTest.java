package com.uniamerica.pie.hotel.services;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.uniamerica.pie.hotel.models.Hospede;
import com.uniamerica.pie.hotel.models.Hotel;
import com.uniamerica.pie.hotel.models.Quarto;
import com.uniamerica.pie.hotel.models.Reserva;
import com.uniamerica.pie.hotel.models.enums.StatusReserva;
import com.uniamerica.pie.hotel.repositories.HospedeRepository;
import com.uniamerica.pie.hotel.repositories.HotelRepository;
import com.uniamerica.pie.hotel.repositories.QuartoRepository;
import com.uniamerica.pie.hotel.repositories.ReservaRepository;

public class ReservaServiceTest {

	/*
    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private QuartoRepository quartoRepository;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HospedeRepository hospedeRepository;

    @InjectMocks
    private ReservaService reservaService;

    public ReservaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    
    @Test
    public void testCadastrarReserva_Sucesso() {
        Hospede hospede = new Hospede("João Silva", "joao@example.com");
        hospede.setId(1L);
        Hotel hotel = new Hotel("Hotel Teste", "Endereço", "Descrição", 10);
        hotel.setId(1L);
        Quarto quarto = new Quarto("101", "Quarto Casal", "Disponível", 1, 2, hotel);
        quarto.setId(1L);

        Reserva reserva = new Reserva(hospede, hotel, quarto,
            LocalDate.now().plusDays(1), LocalDate.now().plusDays(2), 2, null);

        when(hospedeRepository.findById(1L)).thenReturn(Optional.of(hospede));
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(quartoRepository.findById(1L)).thenReturn(Optional.of(quarto));
        when(reservaRepository.findByQuartoAndDataCheckInLessThanEqualAndDataCheckOutGreaterThanEqual(
            any(), any(), any())).thenReturn(Collections.emptyList());
        when(reservaRepository.save(any())).thenReturn(reserva);
        when(quartoRepository.save(any())).thenReturn(quarto);

        Reserva resultado = reservaService.cadastrarReserva(reserva);

        assertNotNull(resultado);
        assertEquals(StatusReserva.CONFIRMADA, resultado.getStatusReserva());
        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    public void testCadastrarReserva_QuartoIndisponivel() {
        // Implementar teste quando o quarto está indisponível nas datas
        Hospede hospede = new Hospede("João Silva", "joao@example.com");
        hospede.setId(1L);
        Hotel hotel = new Hotel("Hotel Teste", "Endereço", "Descrição", 10);
        hotel.setId(1L);
        Quarto quarto = new Quarto("101", "Quarto Casal", "Disponível", 1, 2, hotel);
        quarto.setId(1L);

        Reserva reserva = new Reserva(hospede, hotel, quarto,
            LocalDate.now().plusDays(1), LocalDate.now().plusDays(2), 2, null);

        when(hospedeRepository.findById(1L)).thenReturn(Optional.of(hospede));
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(quartoRepository.findById(1L)).thenReturn(Optional.of(quarto));
        when(reservaRepository.findByQuartoAndDataCheckInLessThanEqualAndDataCheckOutGreaterThanEqual(
            any(), any(), any())).thenReturn(Collections.singletonList(reserva));

        assertThrows(IllegalArgumentException.class, () -> {
            reservaService.cadastrarReserva(reserva);
        });
    }

    @Test
    public void testAtualizarReserva_Sucesso() {
        // Implementar teste para atualizar uma reserva
        Reserva reservaExistente = mock(Reserva.class);
        Reserva reservaAtualizada = mock(Reserva.class);

        when(reservaRepository.findById(anyLong())).thenReturn(Optional.of(reservaExistente));
        when(reservaRepository.save(any())).thenReturn(reservaExistente);

        Reserva resultado = reservaService.atualizarReserva(1L, reservaAtualizada);

        assertNotNull(resultado);
        verify(reservaRepository, times(1)).save(reservaExistente);
    }

    @Test
    public void testDeletarReserva_Sucesso() {
        // Implementar teste para deletar uma reserva
        Reserva reserva = mock(Reserva.class);
        when(reservaRepository.findById(anyLong())).thenReturn(Optional.of(reserva));
        when(reserva.isEncerrada()).thenReturn(true);

        reservaService.deletarReserva(1L);

        verify(reservaRepository, times(1)).delete(reserva);
    }
    */

}
