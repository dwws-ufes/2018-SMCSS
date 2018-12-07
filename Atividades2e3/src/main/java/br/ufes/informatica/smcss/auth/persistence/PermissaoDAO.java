package br.ufes.informatica.smcss.auth.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.auth.domain.Permissao;

@Local
public interface PermissaoDAO extends BaseDAO<Permissao> {

    Permissao retrieveByNome(String nome);
}
