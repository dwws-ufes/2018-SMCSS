package br.ufes.informatica.smcss.core.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.smcss.core.domain.Disciplina;
import br.ufes.informatica.smcss.core.domain.Disciplina_;

@Stateless
public class DisciplinaJPADAO extends BaseJPADAO<Disciplina> implements DisciplinaDAO {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    
    @Override
    public Disciplina retrieveByCodigo(String codigo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Disciplina> cq = cb.createQuery(Disciplina.class);
        Root<Disciplina> root = cq.from(Disciplina.class);
        cq.where(cb.equal(root.get(Disciplina_.codigo), codigo));
        return entityManager.createQuery(cq).getSingleResult();
    }
}
