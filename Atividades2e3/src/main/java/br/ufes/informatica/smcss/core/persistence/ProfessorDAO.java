package br.ufes.informatica.smcss.core.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.Professor;

@Local
public interface ProfessorDAO extends BaseDAO<Professor> {

	List<Professor> findByNome(String query);
}
