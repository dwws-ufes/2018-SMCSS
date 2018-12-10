package br.ufes.informatica.smcss.auth.persistence;

import java.util.Date;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.auth.domain.SolicitacaoUsuario;

@Local
public interface SolicitacaoUsuarioDAO extends BaseDAO<SolicitacaoUsuario> {

    SolicitacaoUsuario findByCodigo(String key);

    int deleteByLogin(String login);

    int excluirAntigos(Date date);
}
