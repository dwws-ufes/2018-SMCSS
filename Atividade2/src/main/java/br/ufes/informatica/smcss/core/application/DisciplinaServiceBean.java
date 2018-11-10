package br.ufes.informatica.smcss.core.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.Disciplina;
import br.ufes.informatica.smcss.core.persistence.DisciplinaDAO;

@Stateless @PermitAll
public class DisciplinaServiceBean extends CrudServiceBean<Disciplina> implements DisciplinaService {

    private static final long serialVersionUID = 1L;

    @EJB
    private DisciplinaDAO disciplinaDAO;

    @Override
    public BaseDAO<Disciplina> getDAO() {
        return disciplinaDAO;
    }
}
