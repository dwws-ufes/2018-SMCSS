package br.ufes.informatica.smcss.core.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.Disciplina;

@Local
public interface DisciplinaDAO extends BaseDAO<Disciplina> {

	Disciplina retrieveByCodigo(String codigo);

    Disciplina findByNome(String nomeDisciplina);

    List<Disciplina> findByCodigoOrNome(String texto);
}
