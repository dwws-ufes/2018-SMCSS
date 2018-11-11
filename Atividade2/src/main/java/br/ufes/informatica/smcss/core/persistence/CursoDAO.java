package br.ufes.informatica.smcss.core.persistence;

import javax.ejb.Local;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.Curso;

@Local
public interface CursoDAO extends BaseDAO<Curso> {

    Curso retrieveByNome(String nome);
}
