package com.uniamerica.pie.hotel.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniamerica.pie.hotel.models.Hotel;
import com.uniamerica.pie.hotel.models.Quarto;
import com.uniamerica.pie.hotel.models.Reserva;
import com.uniamerica.pie.hotel.repositories.HotelRepository;
import com.uniamerica.pie.hotel.repositories.QuartoRepository;
import com.uniamerica.pie.hotel.repositories.ReservaRepository;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public Hotel cadastrarHotel(Hotel hotel) {
        if (hotelRepository.existsByNomeIgnoreCase(hotel.getNome())) {
            throw new IllegalArgumentException("Já existe um hotel com o nome '" + hotel.getNome() + "'");
        }
        hotel.setNome(hotel.getNome().toUpperCase());
        return hotelRepository.save(hotel);
    }


    public Hotel buscarPorId(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel não encontrado"));
    }

    public Hotel atualizarHotel(Long id, Hotel hotelAtualizado) {
        Hotel hotelExistente = buscarPorId(id);

        if (!hotelExistente.getNome().equalsIgnoreCase(hotelAtualizado.getNome())) {
            if (hotelRepository.existsByNomeIgnoreCase(hotelAtualizado.getNome())) {
                throw new IllegalArgumentException("Já existe um hotel com este nome.");
            }
        }

        hotelExistente.setNome(hotelAtualizado.getNome());
        hotelExistente.setEndereco(hotelAtualizado.getEndereco());
        hotelExistente.setDescricao(hotelAtualizado.getDescricao());
        hotelExistente.setNumeroDeQuartos(hotelAtualizado.getNumeroDeQuartos());
        return hotelRepository.save(hotelExistente);
    }

    public void deletarHotel(Long id) {
        Hotel hotel = buscarPorId(id);

       
        LocalDate hoje = LocalDate.now();
        List<Quarto> quartosDoHotel = quartoRepository.findByHotel(hotel);

      
        boolean possuiQuartosOcupados = quartosDoHotel.stream()
            .anyMatch(quarto -> "Ocupado".equalsIgnoreCase(quarto.getStatus()));
        if (possuiQuartosOcupados) {
            throw new IllegalArgumentException("Não é possível excluir um hotel que possui quartos ocupados.");
        }

        
        for (Quarto quarto : quartosDoHotel) {
            List<Reserva> reservasAtivas = reservaRepository.findByQuartoIdAndDataCheckOutAfter(quarto.getId(), hoje);
            if (!reservasAtivas.isEmpty()) {
                throw new IllegalArgumentException("Não é possível excluir o hotel. Existem reservas ativas ou futuras em um ou mais quartos.");
            }
        }

        quartoRepository.deleteAllByHotel(hotel);

        hotelRepository.delete(hotel);
    }


    public List<Hotel> listarTodos() {
        return hotelRepository.findAll();
    }
}
