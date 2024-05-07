package com.espanhol.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.espanhol.model.Funcionario;
import com.espanhol.service.FuncionarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@WebMvcTest
public class FuncionarioControllerTeste {
	

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	FuncionarioService funcionarioService;
	
	@Autowired
	ObjectMapper objectMapper;
	
    
    @Test
    void deveriaSalvarFuncionarioTeste() throws Exception {
    	
    Funcionario	funcionario = Funcionario.builder()
    			.id(1L)
    			.nome("Carlos")
    			.sobreNome("Alberto")
    			.email("carlos@gmail.com")
    			.build();
    			
    	given(funcionarioService.saveFuncionario(any(Funcionario.class))).willAnswer((invocation) -> invocation.getArgument(0));
    	
    	ResultActions response =  mockMvc.perform(post("/api/funcionario")
    			.contentType(MediaType.APPLICATION_JSON)
    	        .content(objectMapper.writeValueAsString(funcionario)));
    	//then
    	
    	response.andDo(print())
    	         .andExpect(status().isCreated())
    	         .andExpect(jsonPath("$.nome",is(funcionario.getNome())))
    	         .andExpect(jsonPath("$.sobreNome",is(funcionario.getSobreNome())))
    	         .andExpect(jsonPath("$.email",is(funcionario.getEmail())));
    	
    }
    
     @Test
     void deveriaListarFuncionarioTeste() throws Exception {
    	 //given
    	 List<Funcionario> listFuncionario = new ArrayList<>();
    	 listFuncionario.add(Funcionario.builder().nome("Jose").sobreNome("Alberto").email("jose1@gmail.com").build());
    	 listFuncionario.add(Funcionario.builder().nome("Jose").sobreNome("Alberto").email("jose2@gmail.com").build());
    	 listFuncionario.add(Funcionario.builder().nome("Jose").sobreNome("Alberto").email("jose3@gmail.com").build());
    	 listFuncionario.add(Funcionario.builder().nome("Jose").sobreNome("Alberto").email("jose4@gmail.com").build());
    	 listFuncionario.add(Funcionario.builder().nome("Jose").sobreNome("Alberto").email("jose5@gmail.com").build());
    	 given(funcionarioService.getAllFuncionario()).willReturn(listFuncionario);
    	 
    	 //when
    	 ResultActions response = mockMvc.perform(get("/api/funcionario"));
    	 
    	 //then
    	 response.andExpect(status().isOk())
    	          .andDo(print())
    	          .andExpect(jsonPath("$.size()", is(listFuncionario.size())));
     }
     
     @Test
     void deveriaRetornarFuncionarioPorId() throws Exception {
    	 Long idFuncionario = 1L;
    	 Funcionario	funcionario = Funcionario.builder()
     			.id(1L)
     			.nome("Carlos")
     			.sobreNome("Alberto")
     			.email("carlos@gmail.com")
     			.build();
    	 given(funcionarioService.getfuncionarioById(idFuncionario)).willReturn(Optional.of(funcionario));
    	 //when
    	 ResultActions response = mockMvc.perform(get("/api/funcionario/{id}", idFuncionario));
    	 response.andExpect(status().isOk())
    	          .andDo(print())
    	          .andExpect(jsonPath("$.nome", is(funcionario.getNome())))
    	          .andExpect(jsonPath("$.sobreNome", is(funcionario.getSobreNome())))
                  .andExpect(jsonPath("$.email", is(funcionario.getEmail())));
     }
     
	@Test
	void deveriaRetornarFuncionarioNaoEncontradoTeste() throws Exception {
		 Long idFuncionario = 1L;
		 Funcionario funcionario = Funcionario.builder()
	     			.id(1L)
	     			.nome("Carlos")
	     			.sobreNome("Alberto")
	     			.email("carlos@gmail.com")
	     			.build();
		 
    	 given(funcionarioService.getfuncionarioById(idFuncionario)).willReturn(Optional.empty());
    	 ResultActions response = mockMvc.perform(get("/api/funcionario/{id}", funcionario.getId()));
    	 response.andExpect(status().isNotFound())
    	          .andDo(print());
    	 
	}
	@Test
	void naoDeveriaAtualizarFuncioanrioNaoEncontardoTest() throws Exception {
		 Long idFuncionario = 1L;
		 
		 Funcionario funcionarioAtualizado = Funcionario.builder()
	     			.nome("Carlos")
	     			.sobreNome("Alberto")
	     			.email("carlosjose@gmail.com")
	     			.build(); 
		 
		 given(funcionarioService.getfuncionarioById(idFuncionario)).willReturn(Optional.empty());
		 given(funcionarioService.updateFuncionario(any(Funcionario.class))).willAnswer((invocation) -> invocation.getArgument(0));
		 
		 ResultActions response = mockMvc.perform(put("/api/funcionario/{id}", idFuncionario)
		                          .contentType(MediaType.APPLICATION_JSON)
		                          .content(objectMapper.writeValueAsBytes(funcionarioAtualizado)));
		 
		 response.andExpect(status().isNotFound())
		         .andDo(print());
	}	         
	
	@Test
	void deveriaAtualizarFuncioanrioTest() throws Exception {
		 Long idFuncionario = 1L;
		 Funcionario funcionario = Funcionario.builder()
	     			.nome("Carlos")
	     			.sobreNome("Alberto")
	     			.email("carlos@gmail.com")
	     			.build();
		 
		 Funcionario funcionarioAtualizado = Funcionario.builder()
	     			.nome("Carlos")
	     			.sobreNome("Alberto")
	     			.email("carlosjose@gmail.com")
	     			.build(); 
		 
		 given(funcionarioService.getfuncionarioById(idFuncionario)).willReturn(Optional.of(funcionario));
		 given(funcionarioService.updateFuncionario(any(Funcionario.class))).willAnswer((invocation) -> invocation.getArgument(0));
		 
		 ResultActions response = mockMvc.perform(put("/api/funcionario/{id}", idFuncionario)
		                          .contentType(MediaType.APPLICATION_JSON)
		                          .content(objectMapper.writeValueAsBytes(funcionarioAtualizado)));
		 
		 response.andExpect(status().isOk())
		         .andDo(print())
		         .andExpect(jsonPath("$.nome", is(funcionarioAtualizado.getNome())))
                 .andExpect(jsonPath("$.nome", is(funcionarioAtualizado.getNome())))
                 .andExpect(jsonPath("$.nome", is(funcionarioAtualizado.getNome())));
	}
	
	@Test
	void deveriaDeletarFuncionarioTeste() throws Exception {
		Long idFuncionario = 1L;
		willDoNothing().given(funcionarioService).deteleFuncionario(idFuncionario);
		ResultActions response = mockMvc.perform(delete("/api/funcionario/{id}", idFuncionario));
		response.andExpect(status().isOk())
		         .andDo(print());
	}
}
