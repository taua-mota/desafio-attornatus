package br.com.attornatus.pessoa.pessoa.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.time.LocalDate;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.attornatus.pessoa.pessoa.model.Pessoa;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void novaPessoa() throws Exception {
		URI uri = new URI("/pessoas/nova-pessoa");
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Taua Mota");
		pessoa.setNascimento(LocalDate.now());

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String json = objectMapper.writeValueAsString(pessoa);

		final ResultActions resultActions = mockMvc
				.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(Response.SC_OK));

		// transforma a resposta em objeto para verificar se o mesmo foi criado
		// corretamente
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		Pessoa pessoaCriada = objectMapper.readValue(contentAsString, Pessoa.class);

		assertEquals(pessoa.getNome(), pessoaCriada.getNome());
		assertEquals(pessoa.getNascimento(), pessoaCriada.getNascimento());

	}
	
	@Test
	public void editarPessoa() throws Exception {
		
		// cria uma pessoa caso nao exista
    	novaPessoa();
    	
		URI uri = new URI("/pessoas/editar-pessoa");
		Pessoa pessoa = new Pessoa();
		pessoa.setId(1L);
		pessoa.setNome("Taua Mota Editado");
		pessoa.setNascimento(LocalDate.now());

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String json = objectMapper.writeValueAsString(pessoa);

		final ResultActions resultActions = mockMvc
				.perform(MockMvcRequestBuilders.patch(uri).content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(Response.SC_OK));

		// transforma a resposta em objeto para verificar se o mesmo foi criado
		// corretamente
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		Pessoa pessoaEditada = objectMapper.readValue(contentAsString, Pessoa.class);

		assertEquals(pessoa.getNome(), pessoaEditada.getNome());
		assertEquals(pessoa.getNascimento(), pessoaEditada.getNascimento());

	}

    @Test
    public void listarTodasPessoas() throws Exception {
        URI uri = new URI("/pessoas/listar-todas-pessoas");

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
            .andExpect(MockMvcResultMatchers.status().is(Response.SC_OK));
    }

    @Test
    public void buscarPessoaPorId() throws Exception {
    	// cria uma pessoa caso nao exista
    	novaPessoa();
    	
        //obtem a pessoa de id 1
        URI uri = new URI("/pessoas/obter-pessoa-por-id/1");

        //faz a requisição e já garante 200 como resposta.
        mockMvc
            .perform(MockMvcRequestBuilders
                         .get(uri))
            .andExpect(MockMvcResultMatchers.status().is(Response.SC_OK));
    }

}