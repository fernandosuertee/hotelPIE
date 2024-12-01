package com.uniamerica.pie.hotel.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
  
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
import org.springframework.web.bind.annotation.RestController;

import com.uniamerica.pie.hotel.models.Reserva;
import com.uniamerica.pie.hotel.services.ReservaService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservaController {

	
    @Autowired
    private ReservaService reservaService;

    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> cadastrarReserva(@Valid @RequestBody Reserva reserva) {
        try {
            Reserva novaReserva = reservaService.cadastrarReserva(reserva);
            return ResponseEntity.status(201).body(novaReserva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<Reserva>> listarTodasAsReservas() {
        List<Reserva> reservas = reservaService.listarTodos();
        return ResponseEntity.ok(reservas);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarReservaPorId(@PathVariable Long id) {
        try {
            Reserva reserva = reservaService.buscarPorId(id);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarReserva(@PathVariable Long id, @Valid @RequestBody Reserva reservaAtualizada) {
        try {
            Reserva reserva = reservaService.atualizarReserva(id, reservaAtualizada);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarReserva(@PathVariable Long id) {
        try {
            reservaService.deletarReserva(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}