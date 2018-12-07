package br.ufes.informatica.smcss.auth.application;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.smcss.auth.domain.Usuario;

@Local
public interface UsuarioService extends CrudService<Usuario> {

}
