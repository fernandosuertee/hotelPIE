package com.uniamerica.pie.hotel.services;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniamerica.pie.hotel.models.Hotel;
import com.uniamerica.pie.hotel.models.Quarto;
import com.uniamerica.pie.hotel.models.Reserva;
import com.uniamerica.pie.hotel.repositories.QuartoRepository;
import com.uniamerica.pie.hotel.repositories.ReservaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class QuartoService {

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @PersistenceContext
    private EntityManager entityManager;
    
    private static final Logger logger = LoggerFactory.getLogger(QuartoService.class);

    public List<Quarto> listarTodos() {
        List<Quarto> quartos = quartoRepository.findAll();
        quartos.forEach(quarto -> {
            Hibernate.initialize(quarto.getHotel());
            
            if (quarto.getHotel() != null) {
                Hibernate.initialize(quarto.getHotel().getQuartos());
            }
        });
        return quartos;
    }

    public List<Quarto> listarQuartosDisponiveis() {
        return quartoRepository.findByStatus("Disponível");
    }

    public Quarto buscarPorId(Long id) {
        Quarto quarto = quartoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quarto não encontrado"));
        Hibernate.initialize(quarto.getHotel());
        
        if (quarto.getHotel() != null) {
            Hibernate.initialize(quarto.getHotel().getQuartos());
        }
        return quarto;
    }

    public Quarto cadastrarQuarto(Quarto quarto) {
       
    	Hotel hotel = quarto.getHotel();
        if (hotel == null || hotel.getId() == null) {
            throw new IllegalArgumentException("Hotel é obrigatório para cadastrar um quarto.");
        }
    	
        if (quarto.getNumero() == null || quarto.getNumero().length() < 3 || quarto.getNumero().length() > 4) {
            throw new IllegalArgumentException("O número do quarto deve ter entre 3 e 4 caracteres.");
        }

        if (quarto.getTipo() == null || quarto.getTipo().isBlank()) {
            throw new IllegalArgumentException("Tipo de quarto não pode ser vazio.");
        }

        validarCapacidadePorTipo(quarto);

        if (quartoRepository.existsByNumeroAndHotelId(quarto.getNumero(), quarto.getHotel().getId())) {
            throw new IllegalArgumentException("Já existe um quarto com este número neste hotel.");
        }

        logger.info("Quarto recebido para salvar: {}", quarto);

        Quarto quartoSalvo = quartoRepository.save(quarto);

        Hibernate.initialize(quartoSalvo.getHotel());

        return quartoSalvo;
    }

    public Quarto atualizarQuarto(Long id, Quarto quartoAtualizado) {
        Quarto quartoExistente = buscarPorId(id);

        if (!quartoExistente.getNumero().equals(quartoAtualizado.getNumero())) {
            if (quartoRepository.existsByNumeroAndHotelId(quartoAtualizado.getNumero(), quartoExistente.getHotel().getId())) {
                throw new IllegalArgumentException("Já existe um quarto com este número neste hotel.");
            }
        }

        validarCapacidadePorTipo(quartoAtualizado);

        quartoExistente.setNumero(quartoAtualizado.getNumero());
        quartoExistente.setTipo(quartoAtualizado.getTipo());
        quartoExistente.setStatus(quartoAtualizado.getStatus());
        quartoExistente.setCapacidadeMinima(quartoAtualizado.getCapacidadeMinima());
        quartoExistente.setCapacidadeMaxima(quartoAtualizado.getCapacidadeMaxima());

        return quartoRepository.save(quartoExistente);
    }
  
    private void validarCapacidadePorTipo(Quarto quarto) {
        switch (quarto.getTipo().toLowerCase()) {
            case "quarto solteiro":
                if (quarto.getCapacidadeMinima() != 1 || quarto.getCapacidadeMaxima() != 1) {
                    throw new IllegalArgumentException("Capacidade para Quarto Solteiro deve ser 1.");
                }
                break;
            case "quarto casal":
                if (quarto.getCapacidadeMinima() != 1 || quarto.getCapacidadeMaxima() != 2) {
                    throw new IllegalArgumentException("Capacidade para Quarto Casal deve ser de 1 a 2.");
                }
                break;
            case "quarto triplo":
                if (quarto.getCapacidadeMinima() != 1 || quarto.getCapacidadeMaxima() != 3) {
                    throw new IllegalArgumentException("Capacidade para Quarto Triplo deve ser de 1 a 3.");
                }
                break;
            case "apartamento":
                if (quarto.getCapacidadeMinima() != 1 || quarto.getCapacidadeMaxima() != 7) {
                    throw new IllegalArgumentException("Capacidade para Apartamento deve ser de 1 a 7.");
                }
                break;
            case "master deluxe casal":
                if (quarto.getCapacidadeMinima() != 1 || quarto.getCapacidadeMaxima() != 2) {
                    throw new IllegalArgumentException("Capacidade para Master Deluxe Casal deve ser de 1 a 2.");
                }
                break;
            default:
                throw new IllegalArgumentException("Tipo de quarto inválido.");
        }
    }

    public void deletarQuarto(Long id) {
        Quarto quarto = buscarPorId(id);

        if ("Ocupado".equalsIgnoreCase(quarto.getStatus())) {
            throw new IllegalArgumentException("Não é possível excluir um quarto ocupado.");
        }

        LocalDate hoje = LocalDate.now();
        List<Reserva> reservasAtivas = reservaRepository.findByQuartoIdAndDataCheckOutAfter(id, hoje);

        if (!reservasAtivas.isEmpty()) {
            throw new IllegalArgumentException("Não é possível excluir um quarto com reservas ativas ou futuras.");
        }

        quartoRepository.delete(quarto);
    }
}
