package com.espanhol.controller;

import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.espanhol.model.Funcionario;
/*
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FuncionarioControllerWebClientTeste {
//teste para requisições reativas(webFlux)
	
	@Autowired
	WebTestClient webTestClient;
	
	private static String URI = "http://localhost:8080/api/funcionario";
	
	@Test
	@Order(1)
	void salvarFuncionarioTest() {
	//given	
		Funcionario funcionario = Funcionario.builder()
     			.id(1L)
     			.nome("Carlos")
     			.sobreNome("Alberto")
     			.email("alberto@gmail.com")
     			.build();
		//when
		webTestClient.post().uri(URI)
		              .contentType(MediaType.APPLICATION_JSON)
		              .bodyValue(funcionario)
		              .exchange() //envia a requisiçaõ
		//then
		              .expectStatus().isCreated()
		              .expectHeader().contentType(MediaType.APPLICATION_JSON)
		              .expectBody()
		              .jsonPath("$.id").isEqualTo(funcionario.getId())
		              .jsonPath("$.nome").isEqualTo(funcionario.getNome())
		              .jsonPath("$.sobreNome").isEqualTo(funcionario.getSobreNome())
		              .jsonPath("$.email").isEqualTo(funcionario.getEmail());
	}
	
	@Test
	@Order(2)
	void buscarFuncionarioPorId() {
		webTestClient.get().uri(URI + "/1").exchange()
		              .expectStatus().isOk()
		              .expectHeader().contentType(MediaType.APPLICATION_JSON)
		              .expectBody()
		              .jsonPath("$.id").isEqualTo(1)
		              .jsonPath("$.nome").isEqualTo("Carlos")
		              .jsonPath("$.sobreNome").isEqualTo("Alberto")
		              .jsonPath("$.email").isEqualTo("alberto@gmail.com");		
	}
	 
	@Test
	@Order(3)
	void listaFuncionariosTest() {
		webTestClient.get().uri(URI).exchange()
		              .expectStatus().isOk()
		              .expectHeader().contentType(MediaType.APPLICATION_JSON)
		              .expectBody()
		              .jsonPath("$[0].nome").isEqualTo("Carlos")
		              .jsonPath("$[0].sobreNome").isEqualTo("Alberto")
		              .jsonPath("$[0].email").isEqualTo("alberto@gmail.com")
		              .jsonPath("$").isArray()
		              .jsonPath("$").value(hasSize(1));
	}
	
	@Test
	@Order(4)
	void retornaListaFuncionariosTeste() {
		webTestClient.get().uri(URI).exchange()
		             .expectStatus().isOk()
		             .expectHeader().contentType(MediaType.APPLICATION_JSON)
		             .expectBodyList(Funcionario.class)
		             .consumeWith(response -> {
		            	 List<Funcionario> funcionarios = response.getResponseBody();
		            	 Assertions.assertEquals(1, funcionarios.size());
		            	 Assertions.assertNotNull(funcionarios);
		             });	
		
	}
	
	@Test
	@Order(5)
	void atualizaFuncionarioTest() {
		
		Funcionario funcionarioAtualizado = Funcionario.builder()
     			.nome("Uriel")
     			.sobreNome("Alberto")
     			.email("uriel@gmail.com")
     			.build();
		
		webTestClient.put().uri(URI + "/1")
		              .contentType(MediaType.APPLICATION_JSON)
		              .bodyValue(funcionarioAtualizado)
		              .exchange()
		//then
		              .expectStatus().isOk()
		              .expectHeader()
		              .contentType(MediaType.APPLICATION_JSON);
		              
	}
	
	@Test
	@Order(6)
	void deletaFuncionarioTest() {
		webTestClient.get().uri(URI).exchange()
		             .expectStatus().isOk()
		             .expectHeader()
		             .contentType(MediaType.APPLICATION_JSON)
		             .expectBodyList(Funcionario.class)
		             .hasSize(1);
		
		webTestClient.delete().uri(URI + "/1").exchange().expectStatus().isOk();
		 //verificando o tamanhao da lista
		webTestClient.get().uri(URI).exchange()
		             .expectStatus().isOk()
		             .expectHeader()
		             .contentType(MediaType.APPLICATION_JSON)
		             .expectBodyList(Funcionario.class)
		             .hasSize(0);
		//verificando se o is foi eleminado
		webTestClient.get().uri(URI + "/1").exchange().expectStatus().is4xxClientError();
	}
}
*/