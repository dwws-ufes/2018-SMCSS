package br.ufes.informatica.smcss.core.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.Curso;
import br.ufes.informatica.smcss.core.persistence.CursoDAO;

@Stateless @PermitAll
public class CursoServiceBean extends CrudServiceBean<Curso> implements CursoService {

    private static final long serialVersionUID = 1L;

    @EJB
    private CursoDAO cursoDAO;

    @Override
    public BaseDAO<Curso> getDAO() {
        return cursoDAO;
    }
}
