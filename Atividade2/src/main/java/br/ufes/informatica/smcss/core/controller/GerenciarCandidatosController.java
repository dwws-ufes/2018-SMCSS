package br.ufes.informatica.smcss.core.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.smcss.core.application.GerenciarCandidatosService;
import br.ufes.informatica.smcss.core.domain.Candidato;

@Named @SessionScoped
public class GerenciarCandidatosController extends CrudController<Candidato> {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private GerenciarCandidatosService gerenciarCandidatoService;
	
	@Override
	protected CrudService<Candidato> getCrudService() {
		return gerenciarCandidatoService;
	}
	
	@Override
	protected void initFilters() {}
	
	public String save() {
		super.save();
		return "/core/gerenciarCandidatos/list.xhmtl";
		
	}
}
