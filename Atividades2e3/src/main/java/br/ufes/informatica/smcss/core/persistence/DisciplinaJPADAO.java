package br.ufes.informatica.smcss.core.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufes.informatica.smcss.core.domain.Disciplina;
import br.ufes.informatica.smcss.core.domain.Disciplina_;
import br.ufes.informatica.smcss.util.SmcssBaseJPADAO;

@Stateless
public class DisciplinaJPADAO extends SmcssBaseJPADAO<Disciplina> implements DisciplinaDAO {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    
    @Override
    public Disciplina retrieveByCodigo(String codigoDisciplina) {
        return queryFirst((cb, cq, root) -> {
            cq.where(cb.equal(root.get(Disciplina_.codigo), codigoDisciplina));
        });
    }

    @Override
    public Disciplina findByNome(String nomeDisciplina) {
        return queryFirst((cb, cq, root) -> {
            cq.where(cb.equal(root.get(Disciplina_.nome), nomeDisciplina));
        });
    }

    @Override
    public List<Disciplina> findByCodigoOrNome(String texto) {
        return queryList((cb, cq, root) -> {
            String pattern = ("%" + texto + "%").toLowerCase();
            cq.where(cb.or(
                cb.like(cb.lower(root.get(Disciplina_.codigo)), pattern),
                cb.like(cb.lower(root.get(Disciplina_.nome)), pattern)));
        });
    }
}
