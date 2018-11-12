package br.ufes.informatica.smcss.core.persistence;

import java.util.List;

import javax.ejb.Local;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.OfertaDisciplina;
import br.ufes.informatica.smcss.core.domain.PeriodoLetivo;

@Local
public interface OfertaDisciplinaDAO extends BaseDAO<OfertaDisciplina> {

    long countByPeriodoLetivo(PeriodoLetivo periodoLetivo);

    List<OfertaDisciplina> listByPeriodoLetivo(PeriodoLetivo periodoLetivo);

    List<OfertaDisciplina> listByPeriodoLetivo(PeriodoLetivo periodoLetivo, int firstIndex, int lastIndex);
}
