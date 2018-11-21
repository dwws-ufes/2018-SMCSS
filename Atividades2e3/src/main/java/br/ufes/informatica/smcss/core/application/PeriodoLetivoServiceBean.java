package br.ufes.informatica.smcss.core.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.PeriodoLetivo;
import br.ufes.informatica.smcss.core.persistence.PeriodoLetivoDAO;

@Stateless @PermitAll
public class PeriodoLetivoServiceBean extends CrudServiceBean<PeriodoLetivo> implements PeriodoLetivoService {

    private static final long serialVersionUID = 1L;

    @EJB
    private PeriodoLetivoDAO periodoLetivoDAO;

    @Override
    public BaseDAO<PeriodoLetivo> getDAO() {
        return periodoLetivoDAO;
    }
}
