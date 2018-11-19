package br.ufes.informatica.smcss.core.persistence;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

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
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<PeriodoLetivo> cq = cb.createQuery(PeriodoLetivo.class);
		Root<PeriodoLetivo> root = cq.from(PeriodoLetivo.class);
		cq.where(cb.like(cb.lower(root.get(PeriodoLetivo_.codigo)), "%" + codigo.toLowerCase() + "%"));
		cq.orderBy(cb.asc(root.get(PeriodoLetivo_.codigo)));
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public PeriodoLetivo retrieveByCodigo(String codigo) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<PeriodoLetivo> cq = cb.createQuery(PeriodoLetivo.class);
		Root<PeriodoLetivo> root = cq.from(PeriodoLetivo.class);
		cq.where(cb.equal(root.get(PeriodoLetivo_.codigo), codigo));
		return entityManager.createQuery(cq).getSingleResult();
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
