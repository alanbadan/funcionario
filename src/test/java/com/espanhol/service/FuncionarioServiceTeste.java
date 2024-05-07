package com.espanhol.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.espanhol.exception.ResourceNotFoundException;
import com.espanhol.model.Funcionario;
import com.espanhol.repository.FuncionarioRepository;
import com.espanhol.serviceImpl.FuncionarioServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTeste {
	
	@Mock
	FuncionarioRepository funcionarioRepository;
	
	@InjectMocks
	FuncionarioServiceImpl funcionarioServiceImpl;
	
	private Funcionario funcionario;
	
	@BeforeEach
	void setUp() {
		funcionario = Funcionario.builder()
				.id(1L)
				.nome("Marlene")
				.sobreNome("Silva")
				.email("silva@gmail.com")
				.build();
		
	}
	@Test
	void salvarFuncionarioTest() {
		given(funcionarioRepository.findByEmail(funcionario.getEmail())).willReturn(Optional.empty());
		given(funcionarioRepository.save(funcionario)).willReturn(funcionario);
		//when
		Funcionario funcionarioSalvo = funcionarioServiceImpl.saveFuncionario(funcionario);
		//then
		assertThat(funcionarioSalvo).isNotNull();
		verify(funcionarioRepository).save(funcionarioSalvo);
	}
   
	@Test
	void naoSalvaFuncionarioExistenteComExceptionTest() {
		given(funcionarioRepository.findByEmail(funcionario.getEmail())).willReturn(Optional.of(funcionario));
		//when
		assertThrows(ResourceNotFoundException.class, () -> {
		     funcionarioServiceImpl.saveFuncionario(funcionario);
	});
		//then
		verify(funcionarioRepository,never()).save(any(Funcionario.class));
	}		
    
	@Test
	void deveRetonarListaFuncionariosTeste() {
		//give
		Funcionario funcionario1 = Funcionario.builder()
				                    .nome("Andre")
				                    .sobreNome("Scarpa")
				                    .email("scarpa@gmail.com")
				                    .build();
		given(funcionarioRepository.findAll()).willReturn(List.of(funcionario, funcionario1));
		//ehwn
		List<Funcionario> listaFuncionario = funcionarioServiceImpl.getAllFuncionario();
		//then
		assertThat(listaFuncionario).isNotNull();
		assertThat(listaFuncionario.size()).isEqualTo(2);
		verify(funcionarioRepository).findAll();
	}
	
	@Test
	void deveriaRetornarListaVaziaTeste() {
/*		//given
		Funcionario funcionario1 = Funcionario.builder()
                .nome("Andre")
                .sobreNome("Scarpa")
                .email("scarpa@gmail.com")
                .build();
 */  

		given(funcionarioRepository.findAll()).willReturn(Collections.emptyList());
		//when
		List<Funcionario> listaFunconario = funcionarioServiceImpl.getAllFuncionario();
		//then
		assertThat(listaFunconario).isEmpty();
		assertThat(listaFunconario.size()).isEqualTo(0);
		verify(funcionarioRepository).findAll();
	}
	
	@Test
	void deveriaRetorarFuncionarioPorIdTeste() {
		//given
		given(funcionarioRepository.findById(1L)).willReturn(Optional.of(funcionario));
		//when
		Funcionario funcionarioId = funcionarioServiceImpl.getfuncionarioById(funcionario.getId()).get();
		//then
		assertThat(funcionarioId).isNotNull();
		verify(funcionarioRepository).findById(1L);
	}
	
	@Test
	void deveriaAtualizarFuncionarioTest() {
		//give
		given(funcionarioRepository.save(funcionario)).willReturn(funcionario);
		funcionario.setNome("Pedro");
		funcionario.setEmail("jose@gmail.com");
		//when
		Funcionario funcionarioAtualizado = funcionarioServiceImpl.updateFuncionario(funcionario);
		//then
		assertThat(funcionarioAtualizado.getNome()).isEqualTo("Pedro");
		assertThat(funcionarioAtualizado.getEmail()).isEqualTo("jose@gmail.com");
	}
	
	@Test
	void deveriaDeletarFuncionarioTeste() {
		Long funcionarioId = 1L;
		willDoNothing().given(funcionarioRepository).deleteById(funcionarioId);
		//when
		funcionarioServiceImpl.deteleFuncionario(funcionarioId);
		//then
		verify(funcionarioRepository,times(1)).deleteById(funcionarioId);
	}
}
