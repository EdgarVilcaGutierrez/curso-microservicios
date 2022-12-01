package com.usuario.service.servicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.feignclients.CarroFeignClient;
import com.usuario.service.feignclients.MotoFeignClient;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.repositorio.UsuarioRepository;

@Service
public class UsuarioService {
	
	//restTemplate
	@Autowired
	private RestTemplate restTemplate;
	
	//feignClient
	@Autowired
	private CarroFeignClient carroFeignClient;
	
	//feignClient
	@Autowired
	private MotoFeignClient motoFeignClient;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	//comunicacion con microservicio carro accediendo a su endpoint getCarroByUsuario
	public List<Carro> getCarros(int usuarioId){
		List<Carro> carros = restTemplate.getForObject("http://localhost:8082/carro/usuario/" + usuarioId, List.class);
		return carros;
	}
	
	//comunicacion con microservicio moto accediendo a su endpoint getMotoByUsuario
	public List<Moto> getMotos(int usuarioId){
		List<Moto> motos = restTemplate.getForObject("http://localhost:8083/moto/usuario/" + usuarioId, List.class);
		return motos;
	}
	
	//metodo guardarCarro con feignClient	
	public Carro saveCarro(int usuarioId, Carro carro) {
		carro.setUsuarioId(usuarioId);
		Carro nuevoCarro = carroFeignClient.save(carro);
		return nuevoCarro;
	}
	
	//metodo listarCarrosDeUsuario con feignClient
	public List<Carro> listarCarrosDeUsuario(int usuarioId){
		List<Carro> carros = carroFeignClient.listarCarroDeUsuario(usuarioId);
		return carros;
	}
	
	//metodo guardarMoto con feignClient
	public Moto saveMoto(int usuarioId, Moto moto) {
		moto.setUsuarioId(usuarioId);
		Moto nuevaMoto = motoFeignClient.save(moto);
		return nuevaMoto;
	}
	
	//metodo listarMotos con feignClient
	public List<Moto> listarMotos(){
		List<Moto> motos = motoFeignClient.findAll();
		return motos;
	}
	//metodo listarMotosDeUsuario con feignClient
	public List<Moto> listarMotosDeUsuario(int usuarioId){
		List<Moto> motos = motoFeignClient.findMotosUsuario(usuarioId);
		return motos;
	}
	
	public List<Usuario> getAll(){
		return usuarioRepository.findAll();
	}
	
	public Usuario getUsuarioById(int id) {
		return usuarioRepository.findById(id).orElse(null);
	}
	
	public Usuario save(Usuario usuario) {
		Usuario nuevoUsuario = usuarioRepository.save(usuario);
		return nuevoUsuario;
	}
	
	
	//metodo para obtener el usuario , carros y motos de un usuario por su id
	public Map<String,Object> getUsuarioAndVehiculos(int usuarioId){
		Map<String, Object> resultado = new HashMap<>();
		Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
		if(usuario == null) {
			resultado.put("Mensaje", "El usuario no existe");
		}
		
		resultado.put("Usuario", usuario);
		List<Carro> carros = carroFeignClient.listarCarroDeUsuario(usuarioId);
		if(carros.isEmpty()) {
			resultado.put("Carros", "El usuario no tiene carros");
		}else {
			resultado.put("Carros", carros);
		}
		
		List<Moto> motos = motoFeignClient.findMotosUsuario(usuarioId);
		if(motos.isEmpty()) {
			resultado.put("Motos", "El usuario no tiene motos");
		}else {
			resultado.put("Motos",motos);
		}
		
		return resultado;
	}
}
