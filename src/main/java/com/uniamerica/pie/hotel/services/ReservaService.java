package com.uniamerica.pie.hotel.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.uniamerica.pie.hotel.models.Hospede;
import com.uniamerica.pie.hotel.models.Hotel;
import com.uniamerica.pie.hotel.models.Quarto;
import com.uniamerica.pie.hotel.models.Reserva;
import com.uniamerica.pie.hotel.models.enums.StatusReserva;
import com.uniamerica.pie.hotel.repositories.HospedeRepository;
import com.uniamerica.pie.hotel.repositories.HotelRepository;
import com.uniamerica.pie.hotel.repositories.QuartoRepository;
import com.uniamerica.pie.hotel.repositories.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HospedeRepository hospedeRepository;

    public Reserva cadastrarReserva(Reserva reserva) {
        
        Hospede hospede = hospedeRepository.findById(reserva.getHospede().getId())
                .orElseThrow(() -> new IllegalArgumentException("Hóspede não encontrado."));

        
        Hotel hotel = hotelRepository.findById(reserva.getHotel().getId())
                .orElseThrow(() -> new IllegalArgumentException("Hotel não encontrado."));

       
        Quarto quarto = quartoRepository.findById(reserva.getQuarto().getId())
                .orElseThrow(() -> new IllegalArgumentException("Quarto não encontrado."));


        if (!quarto.getHotel().getId().equals(hotel.getId())) {
            throw new IllegalArgumentException("O quarto não pertence ao hotel especificado.");
        }

        
        if (reserva.getDataCheckIn().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data de check-in não pode ser no passado.");
        }
        if (!reserva.getDataCheckOut().isAfter(reserva.getDataCheckIn())) {
            throw new IllegalArgumentException("Data de check-out deve ser após o check-in.");
        }


        if (!verificarDisponibilidade(quarto, reserva.getDataCheckIn(), reserva.getDataCheckOut())) {
            throw new IllegalArgumentException("Quarto não está disponível nas datas selecionadas.");
        }


        if (reserva.getNumHospedes() < quarto.getCapacidadeMinima() || reserva.getNumHospedes() > quarto.getCapacidadeMaxima()) {
            throw new IllegalArgumentException(
                    String.format("Número de hóspedes inválido para o tipo de quarto '%s'. O número permitido é entre %d e %d.",
                            quarto.getTipo(), quarto.getCapacidadeMinima(), quarto.getCapacidadeMaxima()));
        }


        reserva.setStatusReserva(StatusReserva.CONFIRMADA); 
        reserva.setHospede(hospede);
        reserva.setHotel(hotel);
        reserva.setQuarto(quarto);

        
        quarto.setStatus("Ocupado");
        quartoRepository.save(quarto);

        return reservaRepository.save(reserva);
    }

    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
    }

    public List<Reserva> listarTodos() {
        return reservaRepository.findAll();
    }

    public Reserva atualizarReserva(Long id, Reserva reservaAtualizada) {
        Reserva reservaExistente = buscarPorId(id);

        if (reservaAtualizada.getDataCheckIn() == null || reservaAtualizada.getDataCheckOut() == null) {
            throw new IllegalArgumentException("Datas de check-in e check-out são obrigatórias.");
        }


        
        if (reservaAtualizada.getHospede() != null && reservaAtualizada.getHospede().getId() != null) {
            Hospede hospede = hospedeRepository.findById(reservaAtualizada.getHospede().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Hóspede não encontrado."));
            reservaExistente.setHospede(hospede);
        }

       
        if (reservaAtualizada.getHotel() != null && reservaAtualizada.getHotel().getId() != null) {
            Hotel hotel = hotelRepository.findById(reservaAtualizada.getHotel().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Hotel não encontrado."));
            reservaExistente.setHotel(hotel);
        }

        
        if (reservaAtualizada.getQuarto() != null && reservaAtualizada.getQuarto().getId() != null) {
            Quarto quarto = quartoRepository.findById(reservaAtualizada.getQuarto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Quarto não encontrado."));
            reservaExistente.setQuarto(quarto);
        }

        
        if (reservaAtualizada.getDataCheckIn() != null && reservaAtualizada.getDataCheckOut() != null) {
            if (!verificarDisponibilidadeParaAtualizacao(
                    reservaExistente.getId(),
                    reservaExistente.getQuarto(),
                    reservaAtualizada.getDataCheckIn(),
                    reservaAtualizada.getDataCheckOut())) {
                throw new IllegalArgumentException("Quarto não está disponível nas datas selecionadas.");
            }
            reservaExistente.setDataCheckIn(reservaAtualizada.getDataCheckIn());
            reservaExistente.setDataCheckOut(reservaAtualizada.getDataCheckOut());
        }

      
        if (reservaAtualizada.getNumHospedes() != null) {
            reservaExistente.setNumHospedes(reservaAtualizada.getNumHospedes());
        }
        
        
        
        if (reservaAtualizada.getStatusReserva() != null) {
            reservaExistente.setStatusReserva(reservaAtualizada.getStatusReserva());
            Quarto quarto = reservaExistente.getQuarto();
            if (quarto != null) {
                if (reservaAtualizada.getStatusReserva() == StatusReserva.ENCERRADA) {
                    quarto.setStatus("Disponível");
                } else if (reservaAtualizada.getStatusReserva() == StatusReserva.CONFIRMADA) {
                    quarto.setStatus("Ocupado");
                }
                quartoRepository.save(quarto);
            }
        }

        return reservaRepository.save(reservaExistente);
    }
   
	public void deletarReserva(Long id) {
        Reserva reserva = buscarPorId(id);
        if (reserva.getStatusReserva() != StatusReserva.ENCERRADA) {
            throw new IllegalArgumentException("Somente reservas com status 'Encerrada' podem ser excluídas.");
        }
        reservaRepository.delete(reserva);
    }

    private boolean verificarDisponibilidade(Quarto quarto, LocalDate dataCheckIn, LocalDate dataCheckOut) {
        List<Reserva> reservasExistentes = reservaRepository
                .findByQuartoAndDataCheckInLessThanEqualAndDataCheckOutGreaterThanEqual(quarto, dataCheckOut,
                        dataCheckIn);

        return reservasExistentes.isEmpty();
    }

    private boolean verificarDisponibilidadeParaAtualizacao(Long reservaId, Quarto quarto, LocalDate dataCheckIn,

            LocalDate dataCheckOut) {
        List<Reserva> reservasExistentes = reservaRepository
                .findByQuartoAndDataCheckInLessThanEqualAndDataCheckOutGreaterThanEqualAndIdNot(quarto, dataCheckOut,
                        dataCheckIn, reservaId);

        return reservasExistentes.isEmpty();
    }
    
    public boolean verificarDisponibilidadePorDatas(Long quartoId, LocalDate dataCheckIn, LocalDate dataCheckOut) {
        Quarto quarto = quartoRepository.findById(quartoId)
                .orElseThrow(() -> new IllegalArgumentException("Quarto não encontrado."));

        List<Reserva> reservasExistentes = reservaRepository.findByQuartoAndDataCheckInLessThanEqualAndDataCheckOutGreaterThanEqual(
                quarto, dataCheckOut, dataCheckIn);

        return reservasExistentes.isEmpty();
    }


    

}