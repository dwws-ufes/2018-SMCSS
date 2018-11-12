package br.ufes.informatica.smcss.core.application;

import java.util.List;

import javax.ejb.Local;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.smcss.core.domain.Candidato;
import br.ufes.informatica.smcss.core.domain.PeriodoLetivo;
import br.ufes.informatica.smcss.core.domain.Pessoa;

@Local
public interface CandidatoService extends CrudService<Candidato> {

	List<Pessoa> findPessoaByNome(String nome);

	List<PeriodoLetivo> findPeriodoByCodigo(String codigo);
}
