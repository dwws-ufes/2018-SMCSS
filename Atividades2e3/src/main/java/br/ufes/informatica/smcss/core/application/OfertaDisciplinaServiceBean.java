package br.ufes.informatica.smcss.core.application;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.Disciplina;
import br.ufes.informatica.smcss.core.domain.OfertaDisciplina;
import br.ufes.informatica.smcss.core.domain.PeriodoLetivo;
import br.ufes.informatica.smcss.core.domain.Professor;
import br.ufes.informatica.smcss.core.persistence.DisciplinaDAO;
import br.ufes.informatica.smcss.core.persistence.OfertaDisciplinaDAO;
import br.ufes.informatica.smcss.core.persistence.PeriodoLetivoDAO;
import br.ufes.informatica.smcss.core.persistence.ProfessorDAO;

@Stateless @PermitAll
public class OfertaDisciplinaServiceBean extends CrudServiceBean<OfertaDisciplina> implements OfertaDisciplinaService {

    private static final long serialVersionUID = 1L;

    @EJB
    private OfertaDisciplinaDAO ofertaDisciplinaDAO;

    @Override
    public BaseDAO<OfertaDisciplina> getDAO() {
        return ofertaDisciplinaDAO;
    }

    @EJB
    private PeriodoLetivoDAO periodoLetivoDAO;

	@EJB
	private ProfessorDAO professorDAO;

    @Override
    public long countOfertasByPeriodoLetivo(PeriodoLetivo periodoLetivo) {
        return ofertaDisciplinaDAO.countByPeriodoLetivo(periodoLetivo);
    }

    @Override
    public List<OfertaDisciplina> listOfertasByPeriodoLetivo(PeriodoLetivo periodoLetivo) {
        return ofertaDisciplinaDAO.listByPeriodoLetivo(periodoLetivo);
    }

    @Override
    public List<OfertaDisciplina> listOfertasByPeriodoLetivo(
            PeriodoLetivo periodoLetivo, int firstIndex, int lastIndex) {
        return ofertaDisciplinaDAO.listByPeriodoLetivo(periodoLetivo, firstIndex, lastIndex);
    }

    @Override
    public PeriodoLetivo retrievePeriodoLetivoSeguinte(PeriodoLetivo periodoLetivo) {
        return periodoLetivoDAO.retrieveSeguinte(periodoLetivo);
    }

    @Override
    public PeriodoLetivo retrievePeriodoLetivoAnterior(PeriodoLetivo periodoLetivo) {
        return periodoLetivoDAO.retrieveAnterior(periodoLetivo);
    }

    @EJB
    private DisciplinaDAO disciplinaDAO;

    @Override
    public Disciplina findDisciplinaByCodigo(String codigoDisciplina) {
		return disciplinaDAO.retrieveByCodigo(codigoDisciplina);
    }

    @Override
    public Disciplina findDisciplinaByNome(String nomeDisciplina) {
        return disciplinaDAO.findByNome(nomeDisciplina);
    }

    @Override
    public List<Disciplina> findDisciplinaByCodigoOrNome(String query) {
        return disciplinaDAO.findByCodigoOrNome(query);
    }

	@Override
	public Disciplina retrieveDisciplinaById(Long disciplinaId) {
		return disciplinaDAO.retrieveById(disciplinaId);
	}

	@Override
	public List<Professor> findProfessorByNome(String query) {
		return professorDAO.findByNome(query);
	}

	@Override
	public Professor retrieveProfessorById(Long professorId) {
		return professorDAO.retrieveById(professorId);
	}

	@Override
	public List<PeriodoLetivo> findPeriodoLetivoByCodigo(String query) {
		return periodoLetivoDAO.findByCodigo(query);
	}

	@Override
	public PeriodoLetivo retrievePeriodoLetivoById(Long periodoLetivoId) {
		return periodoLetivoDAO.retrieveById(periodoLetivoId);
	}
}
