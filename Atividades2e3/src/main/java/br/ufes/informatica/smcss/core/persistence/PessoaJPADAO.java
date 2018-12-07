package br.ufes.informatica.smcss.core.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.ufes.informatica.smcss.core.domain.Pessoa;
import br.ufes.informatica.smcss.core.domain.Pessoa_;
import br.ufes.informatica.smcss.util.SmcssBaseJPADAO;

@Stateless
public class PessoaJPADAO extends SmcssBaseJPADAO<Pessoa> implements PessoaDAO {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<Pessoa> findByNome(String nome) {
        return list((cb, cq, root) -> {
            cq.where(cb.like(cb.lower(root.get(Pessoa_.nome)), ilike(nome)));
            cq.orderBy(cb.asc(root.get(Pessoa_.nome)));
        });
    }

    @Override
    public Pessoa retrieveByNome(String nome) {
        return querySingleResult((cb, cq, root) -> {
            cq.where(cb.equal(root.get(Pessoa_.nome), nome));
        });
    }

    @Override
    public Pessoa retrieveByCpf(String cpf) {
        return querySingleResult((cb, cq, root) -> {
            cq.where(cb.equal(root.get(Pessoa_.cpf), cpf));
        });
    }

}
