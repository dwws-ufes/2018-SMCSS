package br.ufes.informatica.smcss.core.application;

import javax.ejb.Local;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.smcss.core.domain.PeriodoLetivo;

@Local
public interface PeriodoLetivoService extends CrudService<PeriodoLetivo> {
}
