package br.ufes.informatica.smcss.core.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.PeriodoLetivo;

@Local
public interface PeriodoLetivoDAO extends BaseDAO<PeriodoLetivo> {

	List<PeriodoLetivo> findByCodigo(String codigo);

	PeriodoLetivo retrieveByCodigo(String codigo);
}
