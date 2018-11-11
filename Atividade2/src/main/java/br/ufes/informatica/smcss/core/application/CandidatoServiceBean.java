package br.ufes.informatica.smcss.core.application;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.informatica.smcss.core.domain.Candidato;
import br.ufes.informatica.smcss.core.domain.Pessoa;
import br.ufes.informatica.smcss.core.persistence.CandidatoDAO;
import br.ufes.informatica.smcss.core.persistence.PessoaDAO;

@Stateless @PermitAll
public class CandidatoServiceBean extends CrudServiceBean<Candidato> implements CandidatoService {

    private static final long serialVersionUID = 1L;

    @EJB
    private CandidatoDAO candidatoDAO;
    
    @EJB
    private PessoaDAO pessoaDAO;

    @Override
    public BaseDAO<Candidato> getDAO() {
        return candidatoDAO;
    }
    
    @Override
    public List<Pessoa> findPessoaByNome(String nome) {
        return pessoaDAO.findByNome(nome);
    }
    
	/** @see br.ufes.inf.nemo.util.ejb3.application.CrudServiceBean#validateCreate(br.ufes.inf.nemo.util.ejb3.persistence.PersistentObject) */
//	@Override
//	public void validateCreate(Pessoa entity) throws CrudException {
//		// Possibly throwing a CRUD Exception to indicate validation error.
//		CrudException crudException = null;
//		
//		FacesContext context = FacesContext.getCurrentInstance();
//		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");
//		String crudExceptionMessage = bundle.getString("gerenciarCandidatos.text.validationExistingPerson");		
//
//		// Validates business rules.
//		// Rule 1: não pode haver duas pessoas com o mesmo CPF
//		try {
//			Pessoa anotherEntity = candidatoDAO.retrieveByCpf(entity.getCpf());
//			Object[] parameters = new Object[] { anotherEntity.getCpf() };
//			if (anotherEntity != null) {
//				logger.log(Level.INFO, "Creation of applicant \"{0}\" violates validation rule 1: applicant with cpf {1} already exists", new Object[] { entity.getNome(), anotherEntity.getId() });
//				crudException = new CrudException(crudExceptionMessage, crudExceptionMessage, parameters);
//			}
//		}
//		catch (PersistentObjectNotFoundException e) {
//			logger.log(Level.FINE, "Rule 1 OK - there's no other Candidate with the same name: {0}", entity.getNome());
//		}
//		catch (MultiplePersistentObjectsFoundException e) {
//			logger.log(Level.WARNING, "Creation of institution with name \"" + entity.getNome() + "\" threw an exception: a query for institution with this name returned multiple results!", e);
//		}
//
//		// If one of the rules was violated, throw the exception.
//		if (crudException != null)
//			throw crudException;
//	}
    
}
