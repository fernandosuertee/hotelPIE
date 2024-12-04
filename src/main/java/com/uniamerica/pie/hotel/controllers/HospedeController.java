package com.uniamerica.pie.hotel.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.uniamerica.pie.hotel.models.Hospede;
import com.uniamerica.pie.hotel.services.HospedeService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/hospedes")
@CrossOrigin(origins = "*")
public class HospedeController {

	
    @Autowired
    private HospedeService hospedeService;

    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @PostMapping
    public ResponseEntity<Hospede> cadastrarHospede(@Valid @RequestBody Hospede hospede) {
        Hospede novoHospede = hospedeService.cadastrarHospede(hospede);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoHospede);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Hospede> buscarHospedePorId(@PathVariable Long id) {
        Hospede hospede = hospedeService.buscarPorId(id);
        return ResponseEntity.ok(hospede);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<Hospede>> listarTodosOsHospedes() {
        List<Hospede> hospedes = hospedeService.listarTodos();
        return ResponseEntity.ok(hospedes);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Hospede> atualizarHospede(@PathVariable Long id, @Valid @RequestBody Hospede hospedeAtualizado) {
        Hospede hospede = hospedeService.atualizarHospede(id, hospedeAtualizado);
        return ResponseEntity.ok(hospede);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarHospede(@PathVariable Long id) {
        hospedeService.deletarHospede(id);
        return ResponseEntity.noContent().build();
    }
}