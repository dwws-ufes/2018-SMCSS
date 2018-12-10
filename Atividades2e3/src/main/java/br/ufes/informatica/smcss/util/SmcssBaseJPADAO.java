package br.ufes.informatica.smcss.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObject;

public abstract class SmcssBaseJPADAO<T extends PersistentObject> extends BaseJPADAO<T> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public static interface Filter<T> {
        void filter(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<T> root);
    }

    protected Long count(Filter<T> bySomething) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(getDomainClass());
        cq.select(cb.count(root));
        bySomething.filter(cb, cq, root);
        return entityManager.createQuery(cq).getSingleResult();
    }

    protected TypedQuery<T> query(Filter<T> bySomething) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getDomainClass());
        Root<T> root = cq.from(getDomainClass());
        bySomething.filter(cb, cq, root);
        return entityManager.createQuery(cq);
    }

    protected Query namedQuery(String queryName) {
        EntityManager entityManager = getEntityManager();
        return entityManager.createNamedQuery(queryName);
    }

    protected TypedQuery<T> typedNamedQuery(String queryName) {
        EntityManager entityManager = getEntityManager();
        return entityManager.createNamedQuery(queryName, getDomainClass());
    }

    protected <R> TypedQuery<R> typedNamedQuery(String queryName, Class<R> clazz) {
        EntityManager entityManager = getEntityManager();
        return entityManager.createNamedQuery(queryName, clazz);
    }

    protected T queryFirst(Filter<T> bySomething) {
        List<T> resultList = query(bySomething).setMaxResults(1).getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    protected T querySingleResult(Filter<T> bySomething) {
        return query(bySomething).getSingleResult();
    }

    protected T querySingleResultOrNull(Filter<T> bySomething) {
        try {
            return querySingleResult(bySomething);
        } catch(NoResultException ex) {
            return null;
        }
    }

    protected List<T> list(Filter<T> bySomething) {
        return query(bySomething).getResultList();
    }

    protected List<T> page(int firstIndex, int lastIndex, Filter<T> bySomething) {
        return query(bySomething).setFirstResult(firstIndex).setMaxResults(lastIndex - firstIndex).getResultList();
    }

    protected String ilike(String query) {
        return ("%" + query + "%").toLowerCase();
    }
}
