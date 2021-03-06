package br.ufes.informatica.smcss.core.controller;

import java.util.logging.Logger;

import javax.ejb.EJB;

import br.ufes.inf.nemo.jbutler.ejb.controller.JSFController;
import br.ufes.informatica.smcss.core.application.GerenciarSoliciacaoMatriculaService;
import br.ufes.informatica.smcss.core.persistence.PeriodoLetivoDAO;
import br.ufes.informatica.smcss.core.persistence.ProfessorDAO;
import br.ufes.informatica.smcss.core.persistence.SolicitacaoMatriculaDAO;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "GerrenciarSolicitacaoMatricula".
 * 
 * Vitor E. Silva Souza (vitorsouza@gmail.com)
 */

//@Named @SessionScoped
public class GerenciarSolicitacaoMatriculaController extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
		
	/** Path to the folder where the view files (web pages) for this action are placed. */
	private static final String VIEW_PATH = "/core/gerenciarSolicitacaoMatriculaController/";

	/** The logger. */
	private static final Logger logger = Logger.getLogger(GerenciarSolicitacaoMatriculaController.class.getCanonicalName());

	/** The "Register" service. */
	//@EJB
	private GerenciarSoliciacaoMatriculaService soliciacaoMatriculaService;
	
	//@EJB
	private PeriodoLetivoDAO periodoLetivoDAO;

	//@EJB
	private SolicitacaoMatriculaDAO solicitacaoMatriculaDAO;	
	
	private ProfessorDAO professorDAO;
	
	// @Inject
	// private SessionController session; 

}