package com.espanhol.service;

import java.util.List;
import java.util.Optional;

import com.espanhol.model.Funcionario;

public interface FuncionarioService {
	
	Funcionario saveFuncionario(Funcionario funcionario);
	
	List<Funcionario> getAllFuncionario();
	
	Optional<Funcionario> getfuncionarioById(Long id);
	
	Funcionario updateFuncionario(Funcionario funcionarioAtualizado);
	
	void deteleFuncionario(Long id);


	

}
