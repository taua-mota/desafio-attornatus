package br.com.attornatus.pessoa.pessoa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.attornatus.pessoa.pessoa.model.Pessoa;

@Repository
public class PessoaRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public Pessoa salvarPessoa(Pessoa pessoa) {
		entityManager.persist(pessoa);
		return pessoa;
	}
	
	@Transactional
	public Pessoa editarPessoa(Pessoa pessoa) {
		entityManager.merge(pessoa);
		return pessoa;
	}

	public List<Pessoa> listarTodos() {
		String sql = " from Pessoa p";

		final Query query = entityManager.createQuery(sql);
		return query.getResultList();
	}

	public Pessoa buscarPessoa(Long pessoaId) {
		String sql = " from Pessoa p where p.id = :pessoaId ";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("pessoaId", pessoaId);
		try {
			return (Pessoa) query.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	public void deletarPessoa(Pessoa pessoa) {
		entityManager.remove(pessoa);
	}

}
