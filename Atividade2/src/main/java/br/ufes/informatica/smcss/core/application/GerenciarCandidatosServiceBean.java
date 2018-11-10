package br.ufes.informatica.smcss.core.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.Pessoa;
import br.ufes.informatica.smcss.core.persistence.PessoaDAO;

@Stateless @PermitAll
public class GerenciarCandidatosServiceBean extends CrudServiceBean<Pessoa> implements GerenciarCandidatosService {
	private static final long serialVersionUID = 1L;

	@EJB
	private PessoaDAO candidatoDAO;

	@Override
	public BaseDAO<Pessoa> getDAO() {
		return candidatoDAO;
	}
}
