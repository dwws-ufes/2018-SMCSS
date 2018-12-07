package br.ufes.informatica.smcss.core.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.Pessoa;
import br.ufes.informatica.smcss.core.persistence.PessoaDAO;

@Stateless @PermitAll
public class PessoaServiceBean extends CrudServiceBean<Pessoa> implements PessoaService {

    private static final long serialVersionUID = 1L;

    @EJB
    private PessoaDAO pessoaDAO;

    @Override
    public BaseDAO<Pessoa> getDAO() {
        return pessoaDAO;
    }

    @Override
    public Pessoa retrievePessoaByEmail(String email) {
        return pessoaDAO.retrieveByEmail(email);
    }
}
