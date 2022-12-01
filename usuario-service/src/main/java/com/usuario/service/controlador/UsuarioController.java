package com.usuario.service.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.servicio.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsuarios(){
		List<Usuario> usuarios = usuarioService.getAll();
		if(usuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usuarios);
	}
	
	@GetMapping("/{usuarioId}")
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("usuarioId")int usuarioId){
		Usuario usuario = usuarioService.getUsuarioById(usuarioId);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
		Usuario usuarioNuevo = usuarioService.save(usuario);
		return ResponseEntity.ok(usuarioNuevo);
	}
	
	@GetMapping("/carros/{usuarioId}")
	public ResponseEntity<List<Carro>> listarCarros(@PathVariable("usuarioId")int usuarioId){
		Usuario usuario = usuarioService.getUsuarioById(usuarioId);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		List<Carro> carros = usuarioService.getCarros(usuarioId);
		return ResponseEntity.ok(carros);
	}
	
	@GetMapping("/motos/{usuarioId}")
	public ResponseEntity<List<Moto>> listarMotos(@PathVariable("usuarioId")int usuarioId){
		Usuario usuario = usuarioService.getUsuarioById(usuarioId);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		
		List<Moto> motos = usuarioService.getMotos(usuarioId);
		return ResponseEntity.ok(motos);
	}
	
	//metodo feignClientCarro guardarCarro
	@PostMapping("/carro/{usuarioId}")
	public ResponseEntity<Carro> guardarCarro(@PathVariable("usuarioId") int usuarioId, @RequestBody Carro carro ){
		Carro nuevoCarro = usuarioService.saveCarro(usuarioId, carro);
		return ResponseEntity.ok(nuevoCarro);
	}
	
	//metodo feignClientCarro listarCarroDeUsuario
	@GetMapping("/carro/usuario/{usuarioId}")
	public ResponseEntity<List<Carro>> obtenerCarrosDeUsuario(@PathVariable("usuarioId")int usuarioId){
		List<Carro> carros = usuarioService.listarCarrosDeUsuario(usuarioId);
		if(carros.isEmpty() || carros == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(carros);
	}
	
	//metodo feignClientMoto guardarMoto
	@PostMapping("/moto/{usuarioId}")
	public ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId")int usuarioId, @RequestBody Moto moto){
		Moto nuevaMoto = usuarioService.saveMoto(usuarioId, moto);
		return ResponseEntity.ok(nuevaMoto);
	}
	
	//metodo feignClient LISTAR TODAS LAS MOTOS
	@GetMapping("/motos")
	public ResponseEntity<List<Moto>> listarMotos(){
		List<Moto> motos = usuarioService.listarMotos();
		if(motos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(motos);
	}
	
	//metodo feignClient LISTAR MOTOS DE UN USUARIO
	@GetMapping("/moto/usuario/{usuarioId}")
	public ResponseEntity<List<Moto>> obtenerMotosDeUsuario(@PathVariable("usuarioId")int usuarioId){
		List<Moto> motos = usuarioService.listarMotosDeUsuario(usuarioId);
		System.out.println(motos.size());
		if(motos.isEmpty() || motos.size() == 0) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(motos);
	}
	
	@GetMapping("/todos/{usuarioId}")
	public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable("usuarioId")int usuarioId){
		Map<String, Object> resultado = usuarioService.getUsuarioAndVehiculos(usuarioId);
		return ResponseEntity.ok(resultado);
	}

}
