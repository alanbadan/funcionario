package com.espanhol.repsosiory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.espanhol.model.Funcionario;
import com.espanhol.model.Funcionario.FuncionarioBuilder;
import com.espanhol.repository.FuncionarioRepository;

@DataJpaTest
public class FuncionarioRepositoryTeste {
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	private Funcionario funcionario;
	
	@BeforeEach
	void steUp() {
		funcionario = Funcionario.builder()
				.nome("Alberto")
				.sobreNome("Chaves")
				.email("chaves@gmail.com")
				.build();
	}
	
	@DisplayName("teste para salvar um funcionario")
	@Test
	void salvarFuncionarioTeste() {
		//given
		Funcionario funcionario = Funcionario.builder()
				.nome("Jose")
				.sobreNome("Carlos")
				.email("jose@gmail.com")
				.build();
		//when
		Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
		//then
		assertThat(funcionarioSalvo).isNotNull();
		assertThat(funcionarioSalvo.getId()).isGreaterThan(0);
	}
    
	@Test
	void listarFuncionariosTest() {
		//given
				Funcionario funcionario1 = Funcionario.builder()
						.nome("Maria")
						.sobreNome("Augusta")
						.email("maria@gmail.com")
						.build();
				
		funcionarioRepository.save(funcionario);
		funcionarioRepository.save(funcionario1);
		
		//when
		List<Funcionario> listaFuncionario = funcionarioRepository.findAll();
		//then
		assertThat(listaFuncionario).isNotNull();
		assertThat(listaFuncionario).isNotEmpty();
		assertThat(listaFuncionario.size()).isEqualTo(2);
	}
	
	@Test
	void buscaFuncionarioPorIdTeste() {
		funcionarioRepository.save(funcionario);
		
		//when
		Funcionario funcionarioSalvo = funcionarioRepository.findById(funcionario.getId()).get();
		//then
		assertThat(funcionarioSalvo).isNotNull();
	}
	
	@Test
	void atualizaFuncionarioTest() {
		funcionarioRepository.save(funcionario);
		
		//when
		Funcionario funcionarioSalvo =  funcionarioRepository.findById(funcionario.getId()).get();
		funcionarioSalvo.setNome("Alberto");
		funcionarioSalvo.setSobreNome("Chaves");
		funcionarioSalvo.setEmail("alberto@gmail.com");
		//then
		assertThat(funcionarioSalvo.getNome()).isEqualTo("Alberto");
		assertThat(funcionarioSalvo.getSobreNome()).isEqualTo("Chaves");
		assertThat(funcionarioSalvo.getEmail()).isNotEqualTo("chaves@gmail.com");
	}
	
	@Test
	void deletaFuncionarioTest() {
		funcionarioRepository.save(funcionario);
		//when
		funcionarioRepository.deleteById(funcionario.getId());
		Optional<Funcionario> funcionarioDeletado = funcionarioRepository.findById(funcionario.getId());
		//then
		assertThat(funcionarioDeletado).isEmpty();
		
	}
}
