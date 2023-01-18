package br.com.attornatus.pessoa.pessoa.business;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.attornatus.pessoa.pessoa.model.Endereco;
import br.com.attornatus.pessoa.pessoa.model.Pessoa;
import br.com.attornatus.pessoa.pessoa.repository.PessoaRepository;

@Service
public class PessoaService {

	private final PessoaRepository pessoaRepository;

	public PessoaService(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}

	@Transactional(rollbackOn = Exception.class)
	public Pessoa novaPessoa(Pessoa pessoa) throws Exception {

		if (pessoa != null) {

			// verificacao de endereco
			if (pessoa.getEnderecos() != null && !pessoa.getEnderecos().isEmpty()) {
				// verificando se tem mais de um endereco como principal
				if (!EnderecoService.isEnderecosOK(pessoa.getEnderecos())) {
					throw new Exception("É permitido somente um endereço como principal");
				}

				List<Endereco> enderecos = new ArrayList<>();
				for (Endereco end : pessoa.getEnderecos()) {
					Endereco enderecoSetado = new Endereco(end.getLogradouro(), end.getCep(), end.getNumero(),
							end.getCidade(), end.isPrincipal());
					enderecoSetado.setPessoa(pessoa);
					enderecos.add(enderecoSetado);
				}
				
				pessoa.setEnderecos(enderecos);
			}

		}
		

		final Pessoa pessoaCriado = pessoaRepository.salvarPessoa(pessoa);

		return pessoaCriado;
	}
	
	@Transactional(rollbackOn = Exception.class)
	public Pessoa editarPessoa(Pessoa pessoa) throws Exception {

		if (pessoa != null) {

			// verificacao de endereco
			if (pessoa.getEnderecos() != null && !pessoa.getEnderecos().isEmpty()) {
				// verificando se tem mais de um endereco como principal
				if (!EnderecoService.isEnderecosOK(pessoa.getEnderecos())) {
					throw new Exception("É permitido somente um endereço como principal");
				}

				List<Endereco> enderecos = new ArrayList<>();
				for (Endereco end : pessoa.getEnderecos()) {
					Endereco enderecoSetado = new Endereco(end.getLogradouro(), end.getCep(), end.getNumero(),
							end.getCidade(), end.isPrincipal());
					enderecoSetado.setPessoa(pessoa);
					enderecos.add(enderecoSetado);
				}
				
				pessoa.setEnderecos(enderecos);
			}

		}
		

		final Pessoa pessoaCriado = pessoaRepository.editarPessoa(pessoa);

		return pessoaCriado;
	}

	public List<Pessoa> listarTodos() {
		return pessoaRepository.listarTodos();
	}

	public Pessoa buscarPessoa(Long pessoaId) {
		return pessoaRepository.buscarPessoa(pessoaId);
	}


}
