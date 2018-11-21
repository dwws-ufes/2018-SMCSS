package br.ufes.informatica.smcss.core.application;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.Pessoa;
import br.ufes.informatica.smcss.core.domain.Professor;
import br.ufes.informatica.smcss.core.persistence.PessoaDAO;
import br.ufes.informatica.smcss.core.persistence.ProfessorDAO;

@Stateless @PermitAll
public class ProfessorServiceBean extends CrudServiceBean<Professor> implements ProfessorService {

    private static final long serialVersionUID = 1L;

    @EJB
    private ProfessorDAO professorDAO;

    @Override
    public BaseDAO<Professor> getDAO() {
        return professorDAO;
    }

    @EJB
    private PessoaDAO pessoaDAO;

    @Override
    public List<Pessoa> findPessoaByNome(String nome) {
        return pessoaDAO.findByNome(nome);
    }
}
