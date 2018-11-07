package br.ufes.informatica.smcss.core.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.SolicitacaoMatricula;
import br.ufes.informatica.smcss.core.persistence.SolicitacaoMatriculaDAO;

@Stateless @PermitAll
public class SolicitacaoMatriculaServiceBean extends CrudServiceBean<SolicitacaoMatricula> implements SolicitacaoMatriculaService {

    private static final long serialVersionUID = 1L;

    @EJB
    private SolicitacaoMatriculaDAO solicitacaoMatriculaDAO;

    @Override
    public BaseDAO<SolicitacaoMatricula> getDAO() {
        return solicitacaoMatriculaDAO;
    }
}
