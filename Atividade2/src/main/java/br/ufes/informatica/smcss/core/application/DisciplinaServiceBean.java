package br.ufes.informatica.smcss.core.application;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.Curso;
import br.ufes.informatica.smcss.core.domain.Disciplina;
import br.ufes.informatica.smcss.core.persistence.CursoDAO;
import br.ufes.informatica.smcss.core.persistence.DisciplinaDAO;

@Stateless @PermitAll
public class DisciplinaServiceBean extends CrudServiceBean<Disciplina> implements DisciplinaService {

    private static final long serialVersionUID = 1L;
	
    @EJB
    private DisciplinaDAO disciplinaDAO;

    @Override
    public BaseDAO<Disciplina> getDAO() {
        return disciplinaDAO;
    }

    @EJB
    private CursoDAO cursoDAO;

    @Override
    public List<Curso> listCursos() {
        return cursoDAO.retrieveAll();
    }

    
	/** @see br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean#createNewEntity() */
	@Override
	protected Disciplina createNewEntity() {
		return new Disciplina();
	}

	/** @see br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean#authorize() */
	@Override
	public void authorize() {
		// TODO 
		// Overridden to implement authorization. @RolesAllowed is placed in the whole class.
	}

	/** @see br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean#validateCreate(br.ufes.inf.nemo.util.ejb3.persistence.PersistentObject) */
	@Override
	public void validateCreate(Disciplina entity) throws CrudException {
		//FIXME implementar validações do CRUD Disciplinas
		//TODO internacionalização
	}
	
	/** @see br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean#validateUpdate(br.ufes.inf.nemo.util.ejb3.persistence.PersistentObject) */
	@Override
	public void validateUpdate(Disciplina entity) throws CrudException {
		//FIXME implementar validações do CRUD Disciplinas
		//TODO internacionalização
	}

	/** @see br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean#validate(br.ufes.inf.nemo.util.ejb3.persistence.PersistentObject, br.ufes.inf.nemo.util.ejb3.persistence.PersistentObject) */
	@Override
	protected Disciplina validate(Disciplina newEntity, Disciplina oldEntity) {
		//FIXME implementar validações do CRUD Disciplinas
		//TODO internacionalização
		return newEntity;
	}
    
}
