package br.ufes.informatica.smcss.core.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.smcss.core.domain.Pessoa;
import br.ufes.informatica.smcss.core.domain.Pessoa_;

@Stateless
public class PessoaJPADAO extends BaseJPADAO<Pessoa> implements PessoaDAO {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<Pessoa> findByNome(String nome) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pessoa> cq = cb.createQuery(Pessoa.class);
        Root<Pessoa> root = cq.from(Pessoa.class);
        cq.where(cb.like(cb.lower(root.get(Pessoa_.nome)), "%" + nome.toLowerCase() + "%"));
        cq.orderBy(cb.asc(root.get(Pessoa_.nome)));
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Pessoa retrieveByNome(String nome) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pessoa> cq = cb.createQuery(Pessoa.class);
        Root<Pessoa> root = cq.from(Pessoa.class);
        cq.where(cb.equal(root.get(Pessoa_.nome), nome));
        return entityManager.createQuery(cq).getSingleResult();
    }
}
