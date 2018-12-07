package br.ufes.informatica.smcss.auth.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.auth.domain.Usuario;
import br.ufes.informatica.smcss.auth.persistence.UsuarioDAO;

@Stateless
@PermitAll
public class UsuarioServiceBean extends CrudServiceBean<Usuario> implements UsuarioService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @EJB
    private UsuarioDAO usuarioDAO;

    @Override
    public BaseDAO<Usuario> getDAO() {
        return usuarioDAO;
    }
}
