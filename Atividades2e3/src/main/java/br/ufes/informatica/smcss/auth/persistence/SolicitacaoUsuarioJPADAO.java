package br.ufes.informatica.smcss.auth.persistence;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufes.informatica.smcss.auth.domain.SolicitacaoUsuario;
import br.ufes.informatica.smcss.auth.domain.SolicitacaoUsuario_;
import br.ufes.informatica.smcss.util.SmcssBaseJPADAO;

@Stateless
public class SolicitacaoUsuarioJPADAO extends SmcssBaseJPADAO<SolicitacaoUsuario> implements SolicitacaoUsuarioDAO {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public SolicitacaoUsuario findByCodigo(String codigo) {
        return querySingleResultOrNull((cb, cq, root) -> {
            cq.where(cb.equal(root.get(SolicitacaoUsuario_.codigo), codigo));
        });
    }

    @Override
    public int deleteByLogin(String login) {
        return namedQuery("SolicitacaoUsuario.deleteByLogin")
                .setParameter("login", login)
                .executeUpdate()
                ;
    }

    @Override
    public int excluirAntigos(Date date) {
        return namedQuery("SolicitacaoUsuario.excluirAntigos")
                .setParameter("dataLimite", date)
                .executeUpdate()
                ;
    }

}
