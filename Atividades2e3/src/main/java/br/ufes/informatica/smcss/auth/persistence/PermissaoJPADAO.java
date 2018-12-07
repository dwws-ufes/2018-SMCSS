package br.ufes.informatica.smcss.auth.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufes.informatica.smcss.auth.domain.Permissao;
import br.ufes.informatica.smcss.auth.domain.Permissao_;
import br.ufes.informatica.smcss.util.SmcssBaseJPADAO;

@Stateless
public class PermissaoJPADAO extends SmcssBaseJPADAO<Permissao> implements PermissaoDAO {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Permissao retrieveByNome(String nome) {
        return querySingleResult((cb, cq, root) -> {
            cq.where(cb.equal(root.get(Permissao_.nome), nome));
        });
    }
}
