package br.ufes.informatica.smcss.auth.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufes.informatica.smcss.auth.domain.Usuario;
import br.ufes.informatica.smcss.auth.domain.Usuario_;
import br.ufes.informatica.smcss.util.SmcssBaseJPADAO;

@Stateless
public class UsuarioJPADAO extends SmcssBaseJPADAO<Usuario> implements UsuarioDAO {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Usuario retrieveByLogin(String nomeConta) {
        return querySingleResultOrNull((cb, cq, root) -> {
            cq.where(cb.equal(cb.lower(root.get(Usuario_.login)), nomeConta.toLowerCase()));
        });
    }
}
