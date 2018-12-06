package br.ufes.informatica.smcss.core.application;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.informatica.smcss.core.domain.Disciplina;
import br.ufes.informatica.smcss.core.domain.OfertaDisciplina;
import br.ufes.informatica.smcss.core.domain.PeriodoLetivo;
import br.ufes.informatica.smcss.core.domain.Professor;

@Local
public interface OfertaDisciplinaService extends CrudService<OfertaDisciplina> {

    long countOfertasByPeriodoLetivo(PeriodoLetivo periodoLetivo);

    List<OfertaDisciplina> listOfertasByPeriodoLetivo(PeriodoLetivo periodoLetivo);

    List<OfertaDisciplina> listOfertasByPeriodoLetivo(
            PeriodoLetivo periodoLetivo, int firstIndex, int lastIndex);

    PeriodoLetivo retrievePeriodoLetivoSeguinte(PeriodoLetivo periodoLetivo);

    PeriodoLetivo retrievePeriodoLetivoAnterior(PeriodoLetivo periodoLetivo);

    Disciplina findDisciplinaByCodigo(String codigoDisciplina);

    Disciplina findDisciplinaByNome(String codigoDisciplina);

    List<Disciplina> findDisciplinaByCodigoOrNome(String query);

	Disciplina retrieveDisciplinaById(Long disciplinaId);

	List<Professor> findProfessorByNome(String query);

	Professor retrieveProfessorById(Long professorId);

	List<PeriodoLetivo> findPeriodoLetivoByCodigo(String query);

	PeriodoLetivo retrievePeriodoLetivoById(Long periodoLetivoId);
}
