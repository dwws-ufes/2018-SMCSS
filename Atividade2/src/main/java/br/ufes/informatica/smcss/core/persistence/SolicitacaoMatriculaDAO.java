package br.ufes.informatica.smcss.core.persistence;

import javax.ejb.Local;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.SolicitacaoMatricula;

@Local
public interface SolicitacaoMatriculaDAO extends BaseDAO<SolicitacaoMatricula> {
}
