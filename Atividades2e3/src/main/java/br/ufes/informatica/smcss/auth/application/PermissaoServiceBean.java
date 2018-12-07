package br.ufes.informatica.smcss.auth.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.auth.domain.Permissao;
import br.ufes.informatica.smcss.auth.persistence.PermissaoDAO;

@Stateless
@PermitAll
public class PermissaoServiceBean extends CrudServiceBean<Permissao> implements PermissaoService {

    private static final long serialVersionUID = 1L;

    @EJB
    private PermissaoDAO permissaoDAO;

    @Override
    public BaseDAO<Permissao> getDAO() {
        return permissaoDAO;
    }
}
