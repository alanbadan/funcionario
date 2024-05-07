package com.espanhol.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espanhol.exception.ResourceNotFoundException;
import com.espanhol.model.Funcionario;
import com.espanhol.repository.FuncionarioRepository;
import com.espanhol.service.FuncionarioService;

import net.bytebuddy.build.Plugin.Engine.Dispatcher.ForParallelTransformation.WithThrowawayExecutorService;


@Service
public class FuncionarioServiceImpl implements FuncionarioService{
	
	@Autowired
	FuncionarioRepository repository;

	@Override
	public Funcionario saveFuncionario(Funcionario funcionario) {
		Optional<Funcionario> funcionarioCadastrado = repository.findByEmail(funcionario.getEmail());
		if(funcionarioCadastrado.isPresent()) {
			throw new ResourceNotFoundException("O funcionario cadsatrado ja possui email na base: " + funcionario.getEmail()); 
		}
		return repository.save(funcionario);
	}

	@Override
	public List<Funcionario> getAllFuncionario() {
		
		return repository.findAll();
	}

	@Override
	public Optional<Funcionario> getfuncionarioById(Long id) {
		
		return repository.findById(id);
	}

	@Override
	public Funcionario updateFuncionario(Funcionario funcionarioAtualizado) {
		
		return repository.save(funcionarioAtualizado);
	}

	@Override
	public void deteleFuncionario(Long id) {
		repository.deleteById(id);
		
	}

}
