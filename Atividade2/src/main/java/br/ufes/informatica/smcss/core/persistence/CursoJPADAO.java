package br.ufes.informatica.smcss.core.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.smcss.core.domain.Curso;
import br.ufes.informatica.smcss.core.domain.Curso_;

@Stateless
public class CursoJPADAO extends BaseJPADAO<Curso> implements CursoDAO {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Curso retrieveByNome(String nome) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Curso> cq = cb.createQuery(Curso.class);
        Root<Curso> root = cq.from(Curso.class);
        cq.where(cb.equal(root.get(Curso_.nome), nome));
        return entityManager.createQuery(cq).getSingleResult();
    }
}
