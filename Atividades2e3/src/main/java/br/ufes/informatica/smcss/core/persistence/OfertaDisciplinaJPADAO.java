package br.ufes.informatica.smcss.core.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufes.informatica.smcss.core.domain.OfertaDisciplina;
import br.ufes.informatica.smcss.core.domain.OfertaDisciplina_;
import br.ufes.informatica.smcss.core.domain.PeriodoLetivo;
import br.ufes.informatica.smcss.util.SmcssBaseJPADAO;

@Stateless
public class OfertaDisciplinaJPADAO extends SmcssBaseJPADAO<OfertaDisciplina> implements OfertaDisciplinaDAO {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    private Filter<OfertaDisciplina> by(PeriodoLetivo periodoLetivo) {
        return (cb, cq, root) -> {
            cq.where(cb.equal(root.get(OfertaDisciplina_.periodoLetivo), periodoLetivo));
        };
    }

    @Override
    public long countByPeriodoLetivo(PeriodoLetivo periodoLetivo) {
        return count(by(periodoLetivo));
    }

    @Override
    public List<OfertaDisciplina> listByPeriodoLetivo(PeriodoLetivo periodoLetivo) {
        return list(by(periodoLetivo));
    }

    @Override
    public List<OfertaDisciplina> listByPeriodoLetivo(PeriodoLetivo periodoLetivo, int from, int to) {
        return page(from, to, by(periodoLetivo));
    }
}
