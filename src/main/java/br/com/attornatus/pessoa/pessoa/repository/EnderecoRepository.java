package br.com.attornatus.pessoa.pessoa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.attornatus.pessoa.pessoa.model.Endereco;

@Repository
public class EnderecoRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public Endereco salvarEndereco(Endereco endereco) {
		entityManager.persist(endereco);
		return endereco;
	}

	@Transactional
	public Endereco editarEndereco(Endereco endereco) {
		entityManager.merge(endereco);
		return endereco;
	}

	public List<Endereco> listarTodos(Long pessoaId) {
		String sql = " from Endereco e where e.pessoa.id = :pessoaId";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("pessoaId", pessoaId);
		try {
			return query.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	public Endereco buscarEndereco(Long enderecoId) {
		String sql = " from Endereco e where e.id = :enderecoId ";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("enderecoId", enderecoId);
		try {
			return (Endereco) query.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	public void deletarEndereco(Endereco endereco) {
		entityManager.remove(endereco);
	}

}
