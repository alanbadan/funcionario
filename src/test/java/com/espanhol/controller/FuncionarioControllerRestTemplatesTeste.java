package com.espanhol.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.espanhol.model.Funcionario;

@TestMethodOrder(MethodOrderer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FuncionarioControllerRestTemplatesTeste {

//teste com restTemplate usado para requisições bolqueantes.	
	
	@Autowired
	TestRestTemplate testRestTemplate;
	
	private static String URL = "http://localhost:8080/api/funcionario";
	
	
	@Test
	@Order(1)
	void salvarFuncionarioTest() {
		
		Funcionario funcionario = Funcionario.builder()
     			.id(1L)
     			.nome("Carlos")
     			.sobreNome("Alberto")
     			.email("carlos@gmail.com")
     			.build();
		
		ResponseEntity<Funcionario> resposta = testRestTemplate.postForEntity(URL, funcionario, Funcionario.class);
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, resposta.getHeaders().getContentType());
		
		Funcionario funcionarioSalvo = resposta.getBody();
		assertNotNull(funcionarioSalvo);
		
		assertEquals(1L, funcionario.getId());
		assertEquals("Carlos", funcionarioSalvo.getNome());
		assertEquals("Alberto", funcionarioSalvo.getSobreNome());
		assertEquals("carlos@gmail.com", funcionarioSalvo.getEmail());
				
	}
	
	@Test
	@Order(2)
	void listarFuncionarioTest() {
		ResponseEntity<Funcionario[]> resposta = testRestTemplate.getForEntity(URL, Funcionario[].class);
		List<Funcionario> listFuncionario = Arrays.asList(resposta.getBody());
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, resposta.getHeaders().getContentType());
		
		assertEquals(1, listFuncionario.size());
		assertEquals(1L, listFuncionario.get(0).getId());
		assertEquals("Carlos", listFuncionario.get(0).getNome());
		assertEquals("Alberto", listFuncionario.get(0).getSobreNome());
		assertEquals("carlos@gmail.com", listFuncionario.get(0).getEmail());
		
	}
	
	@Test
	@Order(3)
	void buscarFuncionarioPorIdTeste() {
		ResponseEntity<Funcionario> resposta = testRestTemplate.getForEntity(URL + "/1", Funcionario.class);
		Funcionario funcionario =  resposta.getBody();
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, resposta.getHeaders().getContentType());
		
		assertNotNull(funcionario);
		assertEquals("Carlos", funcionario.getNome());
		assertEquals("Alberto", funcionario.getSobreNome());
		assertEquals("carlos@gmail.com", funcionario.getEmail());
		
	}
 
	@Test
	@Order(4)
	void deletarFuncionarioTest() {
		ResponseEntity<Funcionario[]> resposta = testRestTemplate.getForEntity(URL, Funcionario[].class);
		List<Funcionario> funcionarios = Arrays.asList(resposta.getBody());
		assertEquals(1, funcionarios.size());
		
		Map<String,Long> pathVariables = new HashMap<>();
		pathVariables.put("id", 1L);
		ResponseEntity<Void> exchange = testRestTemplate.exchange(URL + "/{id}", HttpMethod.DELETE, null, Void.class, pathVariables);
		
		assertEquals(HttpStatus.OK, exchange.getStatusCode());
		assertFalse(exchange.hasBody()); //comparando se existe corpo ( pq é um void não pode ter corpo
		
		//verificando se o funcionario foi deletado da lista
		resposta = testRestTemplate.getForEntity(URL, Funcionario[].class);
		funcionarios = Arrays.asList(resposta.getBody());
		assertEquals(0, funcionarios.size());	
	}
	
	@Test
	@Order(5)
	void buscaFuncionarioIdInexistentetest() {
		ResponseEntity<Funcionario> resposta = testRestTemplate.getForEntity(URL + "/2", Funcionario.class);
		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
		assertFalse(resposta.hasBody());
	}
	
}
