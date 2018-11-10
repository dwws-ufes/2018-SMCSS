package br.ufes.informatica.smcss.core.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.informatica.smcss.core.domain.Disciplina;

@Stateless
public class DisciplinaJPADAO extends BaseJPADAO<Disciplina> implements DisciplinaDAO {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
