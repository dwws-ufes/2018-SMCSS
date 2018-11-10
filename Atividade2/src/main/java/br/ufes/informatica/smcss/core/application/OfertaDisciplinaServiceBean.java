package br.ufes.informatica.smcss.core.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.OfertaDisciplina;
import br.ufes.informatica.smcss.core.persistence.OfertaDisciplinaDAO;

@Stateless @PermitAll
public class OfertaDisciplinaServiceBean extends CrudServiceBean<OfertaDisciplina> implements OfertaDisciplinaService {

    private static final long serialVersionUID = 1L;

    @EJB
    private OfertaDisciplinaDAO ofertaDisciplinaDAO;

    @Override
    public BaseDAO<OfertaDisciplina> getDAO() {
        return ofertaDisciplinaDAO;
    }
}
