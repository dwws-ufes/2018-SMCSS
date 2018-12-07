package br.ufes.informatica.smcss.auth.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.auth.domain.Usuario;

@Local
public interface UsuarioDAO extends BaseDAO<Usuario> {

    Usuario retrieveByLogin(String nomeConta);
}
