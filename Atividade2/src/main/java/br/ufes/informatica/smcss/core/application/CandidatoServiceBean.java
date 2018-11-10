package br.ufes.informatica.smcss.core.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.Candidato;
import br.ufes.informatica.smcss.core.persistence.CandidatoDAO;

@Stateless @PermitAll
public class CandidatoServiceBean extends CrudServiceBean<Candidato> implements CandidatoService {

    private static final long serialVersionUID = 1L;

    @EJB
    private CandidatoDAO candidatoDAO;

    @Override
    public BaseDAO<Candidato> getDAO() {
        return candidatoDAO;
    }
}
