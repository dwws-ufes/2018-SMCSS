package br.ufes.informatica.smcss.core.persistence;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Path;

import br.ufes.informatica.smcss.core.domain.PeriodoLetivo;
import br.ufes.informatica.smcss.core.domain.PeriodoLetivo_;
import br.ufes.informatica.smcss.core.domain.Periodo_;
import br.ufes.informatica.smcss.util.SmcssBaseJPADAO;

@Stateless
public class PeriodoLetivoJPADAO extends SmcssBaseJPADAO<PeriodoLetivo> implements PeriodoLetivoDAO {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<PeriodoLetivo> findByCodigo(String codigo) {
        return list((cb, cq, root) -> {
            String pattern = ilike(codigo);
            cq.where(cb.like(cb.lower(root.get(PeriodoLetivo_.codigo)), pattern));
            cq.orderBy(cb.asc(root.get(PeriodoLetivo_.codigo)));
        });
    }

    @Override
    public PeriodoLetivo retrieveByCodigo(String codigo) {
        return querySingleResult((cb, cq, root) -> {
            cq.where(cb.equal(root.get(PeriodoLetivo_.codigo), codigo));
        });
    }

    @Override
    public PeriodoLetivo retrieveAnterior(PeriodoLetivo periodoLetivo) {
        return queryFirst((cb, cq, root) -> {
            Path<Date> inicioPeriodo = root.get(PeriodoLetivo_.duracao).get(Periodo_.inicio);
            cq.where(cb.lessThan(inicioPeriodo, periodoLetivo.getDuracao().getInicio()));
            cq.orderBy(cb.desc(inicioPeriodo));
        });
    }

    @Override
    public PeriodoLetivo retrieveSeguinte(PeriodoLetivo periodoLetivo) {
        return queryFirst((cb, cq, root) -> {
            Path<Date> inicioPeriodo = root.get(PeriodoLetivo_.duracao).get(Periodo_.inicio);
            cq.where(cb.greaterThan(inicioPeriodo, periodoLetivo.getDuracao().getInicio()));
            cq.orderBy(cb.asc(inicioPeriodo));
        });
    }
}
