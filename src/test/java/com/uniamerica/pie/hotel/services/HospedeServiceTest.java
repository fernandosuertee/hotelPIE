package com.uniamerica.pie.hotel.services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.uniamerica.pie.hotel.models.Hospede;
import com.uniamerica.pie.hotel.repositories.HospedeRepository;

public class HospedeServiceTest {

    @Mock
    private HospedeRepository hospedeRepository;

    @InjectMocks
    private HospedeService hospedeService;

    public HospedeServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarHospede_Sucesso() {
        Hospede hospede = new Hospede("João Silva", "joao@example.com");
        when(hospedeRepository.findByEmail(hospede.getEmail()))
            .thenReturn(Optional.empty());
        when(hospedeRepository.save(hospede)).thenReturn(hospede);

        Hospede resultado = hospedeService.cadastrarHospede(hospede);

        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        verify(hospedeRepository, times(1)).save(hospede);
    }

    @Test
    public void testCadastrarHospede_EmailJaExistente() {
        Hospede hospede = new Hospede("Maria Souza", "maria@example.com");
        when(hospedeRepository.findByEmail(hospede.getEmail()))
            .thenReturn(Optional.of(hospede));

        assertThrows(IllegalArgumentException.class, () -> {
            hospedeService.cadastrarHospede(hospede);
        });
    }
}