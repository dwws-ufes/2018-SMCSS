package br.ufes.informatica.smcss.core.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.application.CrudException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


import br.ufes.informatica.smcss.core.domain.Pessoa;
import br.ufes.informatica.smcss.core.persistence.PessoaDAO;


@Stateless @PermitAll
public class GerenciarCandidatosServiceBean extends CrudServiceBean<Pessoa> implements GerenciarCandidatosService {
	private static final long serialVersionUID = 1L;
	
	/** The logger. */
	private static final Logger logger = Logger.getLogger(GerenciarCandidatosServiceBean.class.getCanonicalName());

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
		
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");
		String crudExceptionMessage = bundle.getString("gerenciarCandidatos.text.validationExistingPerson");		

		// Validates business rules.
		// Rule 1: não pode haver duas pessoas com o mesmo CPF
		try {
			Pessoa anotherEntity = candidatoDAO.retrieveByCpf(entity.getCpf());
			Object[] parameters = new Object[] { anotherEntity.getCpf() };
			if (anotherEntity != null) {
				logger.log(Level.INFO, "Creation of applicant \"{0}\" violates validation rule 1: applicant with cpf {1} already exists", new Object[] { entity.getNome(), anotherEntity.getId() });
				crudException = new CrudException(crudExceptionMessage, crudExceptionMessage, parameters);
			}
		}
		catch (PersistentObjectNotFoundException e) {
			logger.log(Level.FINE, "Rule 1 OK - there's no other Candidate with the same name: {0}", entity.getNome());
		}
		catch (MultiplePersistentObjectsFoundException e) {
			logger.log(Level.WARNING, "Creation of institution with name \"" + entity.getNome() + "\" threw an exception: a query for institution with this name returned multiple results!", e);
		}

		// If one of the rules was violated, throw the exception.
		if (crudException != null)
			throw crudException;
	}
	
}
