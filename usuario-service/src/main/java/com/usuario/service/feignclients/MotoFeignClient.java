package com.usuario.service.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.usuario.service.modelos.Moto;

@FeignClient(name="moto-service",url="http://localhost:8083")
@RequestMapping("/moto")
public interface MotoFeignClient {
	
	@PostMapping
	public Moto save(Moto moto);
	
	@GetMapping
	public List<Moto> findAll();
	
	//nota apuntar al endpoint del moto service obtenerlista de motos de usuario
	@GetMapping("/usuario/{usuarioId}")
	public List<Moto> findMotosUsuario(@PathVariable("usuarioId")int usuarioId);

}
