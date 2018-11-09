package br.ufes.informatica.smcss.core.application;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.smcss.core.domain.Pessoa;
import br.ufes.informatica.smcss.core.domain.SolicitacaoMatricula;

@Local
public interface GerenciarSoliciacaoMatriculaService extends CrudService<SolicitacaoMatricula> {

}
