package br.ufes.informatica.smcss.core.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.Aula;
import br.ufes.informatica.smcss.core.persistence.AulaDAO;

@Stateless @PermitAll
public class AulaServiceBean extends CrudServiceBean<Aula> implements AulaService {

    private static final long serialVersionUID = 1L;

    @EJB
    private AulaDAO aulaDAO;

    @Override
    public BaseDAO<Aula> getDAO() {
        return aulaDAO;
    }
}
