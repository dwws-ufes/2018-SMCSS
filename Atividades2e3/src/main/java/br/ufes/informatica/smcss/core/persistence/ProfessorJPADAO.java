package br.ufes.informatica.smcss.core.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufes.informatica.smcss.core.domain.Pessoa_;
import br.ufes.informatica.smcss.core.domain.Professor;
import br.ufes.informatica.smcss.core.domain.Professor_;
import br.ufes.informatica.smcss.util.SmcssBaseJPADAO;

@Stateless
public class ProfessorJPADAO extends SmcssBaseJPADAO<Professor> implements ProfessorDAO {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<Professor> findByNome(String query) {
        return list((cb, cq, root) -> {
            String pattern = ilike(query);
            cq.where(cb.like(cb.lower(root.get(Professor_.pessoa).get(Pessoa_.nome)), pattern));
        });
    }
}
