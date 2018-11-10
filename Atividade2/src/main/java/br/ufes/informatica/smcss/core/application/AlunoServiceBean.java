package br.ufes.informatica.smcss.core.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.Aluno;
import br.ufes.informatica.smcss.core.persistence.AlunoDAO;

@Stateless @PermitAll
public class AlunoServiceBean extends CrudServiceBean<Aluno> implements AlunoService {

    private static final long serialVersionUID = 1L;

    @EJB
    private AlunoDAO alunoDAO;

    @Override
    public BaseDAO<Aluno> getDAO() {
        return alunoDAO;
    }
}
