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

import br.com.attornatus.pessoa.pessoa.business.PessoaService;
import br.com.attornatus.pessoa.pessoa.model.Pessoa;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	private PessoaService pessoaService;

	public PessoaController(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}

	@PostMapping("/nova-pessoa")
	public ResponseEntity<?> novoPessoa(@RequestBody Pessoa pessoa) throws Exception {
		return ResponseEntity.ok(pessoaService.novaPessoa(pessoa));
	}
	
	@PatchMapping("/editar-pessoa")
	public ResponseEntity<?> editarPessoa(@RequestBody Pessoa pessoa) throws Exception {
		return ResponseEntity.ok(pessoaService.editarPessoa(pessoa));
	}

	@GetMapping("/listar-todas-pessoas")
	public List<Pessoa> listarPessoas() {
		return pessoaService.listarTodos();
	}

	@GetMapping("/obter-pessoa-por-id/{pessoaId}")
	public ResponseEntity<?> obterPessoaPorId(@PathVariable Long pessoaId) {
		final Pessoa pessoa = pessoaService.buscarPessoa(pessoaId);

		if (pessoa == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(pessoa);
	}

}
