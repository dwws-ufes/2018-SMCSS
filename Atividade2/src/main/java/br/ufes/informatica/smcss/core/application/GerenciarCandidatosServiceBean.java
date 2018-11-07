package br.ufes.informatica.smcss.core.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudOperation;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;


import br.ufes.informatica.smcss.core.domain.Pessoa;
import br.ufes.informatica.smcss.core.persistence.PessoaDAO;


@Stateless @PermitAll
public class GerenciarCandidatosServiceBean extends CrudServiceBean<Pessoa> implements GerenciarCandidatosService {
	private static final long serialVersionUID = 1L;

	@EJB
	private PessoaDAO candidatoDAO;

	@Override
	public BaseDAO<Pessoa> getDAO() {
		return candidatoDAO;
	}
	
	/** @see br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean#validateCreate(br.ufes.inf.nemo.util.ejb3.persistence.PersistentObject) */
	@Override
	public void validateCreate(Pessoa entity) throws CrudException {
		// Possibly throwing a CRUD Exception to indicate validation error.
		CrudException crudException = null;
		String crudExceptionMessage = "A pessoa \"" + entity.getNome() + "\" não pode ser criada devido a erros de validação.";

		// Validates business rules.
		// Rule 1: não pode haver duas pessoas com o mesmo CPF
		try {
			Pessoa anotherEntity = candidatoDAO.retrieveByCPF(entity.getCpf());
			if (anotherEntity != null) {
				logger.log(Level.INFO, "Creation of institution \"{0}\" violates validation rule 1: institution with id {1} has same name", new Object[] { entity.getName(), anotherEntity.getId() });
				crudException = addValidationError(crudException, crudExceptionMessage, "name", "manageInstitutions.error.repeatedName", anotherEntity.getLastUpdateDate());
			}
		}
		catch (PersistentObjectNotFoundException e) {
			logger.log(Level.FINE, "Rule 1 OK - there's no other institution with the same name: {0}", entity.getName());
		}
		catch (MultiplePersistentObjectsFoundException e) {
			logger.log(Level.WARNING, "Creation of institution with name \"" + entity.getName() + "\" threw an exception: a query for institution with this name returned multiple results!", e);
			crudException = addValidationError(crudException, crudExceptionMessage, "name", "manageInstitutions.error.multipleInstancesError");
		}

		// If one of the rules was violated, throw the exception.
		if (crudException != null)
			throw crudException;
	}
	
}
