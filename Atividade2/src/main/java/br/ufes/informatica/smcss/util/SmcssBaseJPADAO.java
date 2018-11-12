package br.ufes.informatica.smcss.util;

import java.util.List;

import javax.persistence.EntityManager;
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
    
    public static interface Query<D, R> {
        R apply(Filter<D> filter); 
    }
    
    protected Query<T, Long> countBy() {        
        return (prepareQuery) -> {
            EntityManager entityManager = getEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<T> root = cq.from(getDomainClass());
            cq.select(cb.count(root));
            prepareQuery.filter(cb, cq, root);
            return entityManager.createQuery(cq).getSingleResult();
        };
    }
    
    protected TypedQuery<T> queryListBy(Filter<T> prepareQuery) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getDomainClass());
        Root<T> root = cq.from(getDomainClass());
        prepareQuery.filter(cb, cq, root);
        return entityManager.createQuery(cq);
    }
    
    protected T queryFirst(Filter<T> prepareQuery) {
        List<T> resultList = queryListBy(prepareQuery).setMaxResults(1).getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }
    
    protected T querySingleResult(Filter<T> prepareQuery) {
        return queryListBy(prepareQuery).getSingleResult();
    }

    protected List<T> queryList(Filter<T> prepareQuery) {
        return queryListBy(prepareQuery).getResultList();
    }
    
    protected Query<T, List<T>> listBy() {
        return (prepareQuery) -> {            
            return queryListBy(prepareQuery).getResultList();
        };
    }
    
    protected Query<T, List<T>> listBy(int firstIndex, int lastIndex) {
        return (prepareQuery) -> {
            TypedQuery<T> query = queryListBy(prepareQuery);
            query.setFirstResult(firstIndex);
            query.setMaxResults(lastIndex - firstIndex);
            return query.getResultList();
        };
    }    
}
