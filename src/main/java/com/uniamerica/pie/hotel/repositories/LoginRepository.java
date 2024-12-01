package com.uniamerica.pie.hotel.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uniamerica.pie.hotel.models.Usuario;


public interface LoginRepository extends JpaRepository<Usuario, Long>{

	public Optional<Usuario> findByUsername(String login);
	
}
