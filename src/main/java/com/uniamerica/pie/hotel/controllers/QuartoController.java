package com.uniamerica.pie.hotel.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uniamerica.pie.hotel.models.Quarto;
import com.uniamerica.pie.hotel.repositories.QuartoRepository;
import com.uniamerica.pie.hotel.services.QuartoService;
import com.uniamerica.pie.hotel.services.ReservaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/quartos")
@CrossOrigin(origins = "http://localhost:4200")
public class QuartoController {

    @Autowired
    private QuartoService quartoService;
    
    @Autowired
    private QuartoRepository quartoRepository;
    
    @Autowired
    private ReservaService reservaService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> cadastrarQuarto(@Valid @RequestBody Quarto quarto) {
        try {
            Quarto novoQuarto = quartoService.cadastrarQuarto(quarto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoQuarto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @GetMapping("/{quartoId}/disponibilidade")
    public ResponseEntity<Boolean> verificarDisponibilidade(
        @PathVariable Long quartoId,
        @RequestParam LocalDate dataCheckIn,
        @RequestParam LocalDate dataCheckOut) {
        boolean disponivel = reservaService.verificarDisponibilidadePorDatas(quartoId, dataCheckIn, dataCheckOut);
        return ResponseEntity.ok(disponivel);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @GetMapping("/hotel/{hotelId}/todos")
    public ResponseEntity<List<Quarto>> getTodosQuartosPorHotel(@PathVariable Long hotelId) {
        List<Quarto> quartos = quartoRepository.findByHotelId(hotelId);
        return ResponseEntity.ok(quartos);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Quarto>> getQuartosByHotel(@PathVariable Long hotelId) {
        List<Quarto> quartos = quartoRepository.findByHotelIdAndStatus(hotelId, "Disponível");
        return ResponseEntity.ok(quartos);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @GetMapping("/disponiveis")
    public ResponseEntity<List<Quarto>> listarQuartosDisponiveis() {
        List<Quarto> quartos = quartoService.listarQuartosDisponiveis();
        return ResponseEntity.ok(quartos);
    }


    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<Quarto>> listarTodosOsQuartos() {
        List<Quarto> quartos = quartoService.listarTodos();
        return ResponseEntity.ok(quartos);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Quarto> buscarQuartoPorId(@PathVariable Long id) {
        Quarto quarto = quartoService.buscarPorId(id);
        return ResponseEntity.ok(quarto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Quarto> atualizarQuarto(@PathVariable Long id, @Valid @RequestBody Quarto quartoAtualizado) {
        Quarto quarto = quartoService.atualizarQuarto(id, quartoAtualizado);
        return ResponseEntity.ok(quarto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarQuarto(@PathVariable Long id) {
        quartoService.deletarQuarto(id);
        return ResponseEntity.noContent().build();
    }
    
}
