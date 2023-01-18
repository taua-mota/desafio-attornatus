package br.com.attornatus.pessoa.pessoa.business;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.attornatus.pessoa.pessoa.model.Endereco;
import br.com.attornatus.pessoa.pessoa.model.Pessoa;
import br.com.attornatus.pessoa.pessoa.repository.EnderecoRepository;
import br.com.attornatus.pessoa.pessoa.repository.PessoaRepository;

@Service
public class EnderecoService {

	private final EnderecoRepository enderecoRepository;
	private final PessoaRepository pessoaRepository;

	public EnderecoService(EnderecoRepository enderecoRepository, PessoaRepository pessoaRepository) {
		this.enderecoRepository = enderecoRepository;
		this.pessoaRepository = pessoaRepository;
	}

	@Transactional(rollbackOn = Exception.class)
	public Endereco novoEndereco(Endereco endereco, Long pessoaId) throws Exception {

		final Pessoa pessoa = pessoaRepository.buscarPessoa(pessoaId);

		if (pessoa == null) {
			throw new Exception("Pessoa informada não existe!");
		}

		// verificacao de endereco
		if (pessoa.getEnderecos() != null && !pessoa.getEnderecos().isEmpty()) {
			// verificando se tem mais de um endereco como principal
			pessoa.getEnderecos().add(endereco);
			if (!isEnderecosOK(pessoa.getEnderecos())) {
				throw new Exception("É permitido somente um endereço como principal");
			}
		}
		pessoa.setEnderecos(new ArrayList<>());
		endereco.setPessoa(pessoa);

		final Endereco enderecoCriado = enderecoRepository.salvarEndereco(endereco);

		return enderecoCriado;
	}

	@Transactional(rollbackOn = Exception.class)
	public Endereco editarEndereco(Endereco endereco, Long pessoaId) throws Exception {
		
		final Pessoa pessoa = pessoaRepository.buscarPessoa(pessoaId);

		if (pessoa == null) {
			throw new Exception("Pessoa informada não existe!");
		}
		
		// verificacao de endereco
		if (pessoa.getEnderecos() != null && !pessoa.getEnderecos().isEmpty()) {
			// verificando se tem mais de um endereco como principal
			pessoa.getEnderecos().add(endereco);
			if (!isEnderecosOK(pessoa.getEnderecos())) {
				throw new Exception("É permitido somente um endereço como principal");
			}
		}
		
		pessoa.setEnderecos(null);
		endereco.setPessoa(pessoa);

		final Endereco enderecoEditado = enderecoRepository.editarEndereco(endereco);

		return enderecoEditado;
	}

	public List<Endereco> listarTodos(Long pessoaId) throws Exception {
		
		final Pessoa pessoa = pessoaRepository.buscarPessoa(pessoaId);

		if (pessoa == null) {
			throw new Exception("Pessoa informada não existe!");
		}
		
		return enderecoRepository.listarTodos(pessoaId);
	}

	public Endereco buscarEndereco(Long enderecoId) {
		return enderecoRepository.buscarEndereco(enderecoId);
	}

	public static boolean isEnderecosOK(List<Endereco> enderecos) {
		int qntEnderecoPrincipal = 0;
		ListIterator<Endereco> enderecoIt = enderecos.listIterator();
		while (enderecoIt.hasNext()) {
			Endereco iterado = enderecoIt.next();
			if (iterado.isPrincipal()) {
				qntEnderecoPrincipal++;
			}
		}

		if (qntEnderecoPrincipal > 1) {
			return false;
		}

		return true;
	}

}
