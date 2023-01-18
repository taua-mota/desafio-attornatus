package br.com.attornatus.pessoa.pessoa.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.attornatus.pessoa.pessoa.business.EnderecoService;
import br.com.attornatus.pessoa.pessoa.model.Endereco;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

	private EnderecoService enderecoService;

	public EnderecoController(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}

	@PostMapping("/novo-endereco/pessoa/{pessoaId}")
	public ResponseEntity<?> novoEndereco(@RequestBody Endereco endereco, @PathVariable Long pessoaId) throws Exception {
		return ResponseEntity.ok(enderecoService.novoEndereco(endereco, pessoaId));
	}

	@PatchMapping("/editar-endereco/pessoa/{pessoaId}")
	public ResponseEntity<?> editarEndereco(@RequestBody Endereco endereco, @PathVariable Long pessoaId) throws Exception {
		return ResponseEntity.ok(enderecoService.editarEndereco(endereco, pessoaId));
	}

	@GetMapping("/listar-todos-enderecos/{pessoaId}")
	public List<Endereco> listarEnderecos(@PathVariable Long pessoaId) throws Exception {
		return enderecoService.listarTodos(pessoaId);
	}

	@GetMapping("/obter-endereco-por-id/{enderecoId}")
	public ResponseEntity<?> obterEnderecoPorId(@PathVariable Long enderecoId) {
		final Endereco endereco = enderecoService.buscarEndereco(enderecoId);

		if (endereco == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(endereco);
	}

}
