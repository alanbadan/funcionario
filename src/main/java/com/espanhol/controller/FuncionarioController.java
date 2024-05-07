package com.espanhol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.espanhol.model.Funcionario;
import com.espanhol.service.FuncionarioService;

@RestController
@RequestMapping("/api/funcionario")
public class FuncionarioController {
	
	@Autowired
	FuncionarioService service;
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Funcionario salveFuncionario(@RequestBody Funcionario funcionario) {
		return service.saveFuncionario(funcionario);
	}
	
	@GetMapping
	public List<Funcionario> listarFuncionarios(){
		return service.getAllFuncionario();
	}
	
	@GetMapping("/{id}")
   public ResponseEntity<Funcionario> buscaFuncionarioPorId(@PathVariable ("id") Long funcionarioId){
		return service.getfuncionarioById(funcionarioId)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Funcionario> atualizaFuncionario(@PathVariable("id") Long funcionarioId, @RequestBody Funcionario funcionario){
		return service.getfuncionarioById(funcionarioId)
                       .map(funcionarioSalvo -> {
                    	 funcionarioSalvo.setNome(funcionario.getNome());
                    	 funcionarioSalvo.setSobreNome(funcionario.getSobreNome());
                    	 funcionarioSalvo.setEmail(funcionario.getEmail());
                    	 
                    	 Funcionario funcionarioAtualizado = service.updateFuncionario(funcionarioSalvo);
                    	 return new ResponseEntity<>(funcionarioAtualizado, HttpStatus.OK);
                    		     
                       })
                       .orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletarFuncionario(@PathVariable("id") Long funcioanrioId){
		service.deteleFuncionario(funcioanrioId);
		return new ResponseEntity<String>("Funcionario deletado com sucesso", HttpStatus.OK);
	}
}
