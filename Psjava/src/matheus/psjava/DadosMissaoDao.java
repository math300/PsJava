package matheus.psjava;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class DadosMissaoDao {

	protected EntityManager entityManager;
	protected EntityManagerFactory entityManagerFactory;
	
	public DadosMissaoDao() {
	
		entityManagerFactory = Persistence.createEntityManagerFactory("psjava");
		entityManager = entityManagerFactory.createEntityManager();
	}

	public DadosMissao salvar(DadosMissao entity) {
		EntityTransaction t = entityManager.getTransaction();
		t.begin();
		entityManager.persist(entity);
		entityManager.flush();
		t.commit();
		return entity;
	}

	public DadosMissao atualizar(DadosMissao entity) {
		EntityTransaction t = entityManager.getTransaction();
		t.begin();
		entityManager.merge(entity);
		entityManager.flush();
		t.commit();
		return entity;
	}

	public void remover(int id) {
		DadosMissao entity = encontrar(id);
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		DadosMissao mergedEntity = entityManager.merge(entity);
		entityManager.remove(mergedEntity);
		entityManager.flush();
		tx.commit();
	}

	public List<DadosMissao> getList() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DadosMissao> query = builder.createQuery(DadosMissao.class);
		query.from(DadosMissao.class);
		return entityManager.createQuery(query).getResultList();
	}

	public DadosMissao encontrar(int id) {
		return entityManager.find(DadosMissao.class, id);
	}

}
