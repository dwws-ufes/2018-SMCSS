package br.ufes.informatica.smcss.core.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.smcss.core.application.CandidatoService;
import br.ufes.informatica.smcss.core.domain.Candidato;
import br.ufes.informatica.smcss.core.domain.Pessoa;

@Named
@SessionScoped
public class CandidatoController extends CrudController<Candidato> {

	private static final long serialVersionUID = 1L;

	@EJB
	private CandidatoService candidatoService;

	@Override
	public CrudService<Candidato> getCrudService() {
		return candidatoService;
	}

	@Override
	public void initFilters() {

	}

	public List<Pessoa> completeNomePessoa(String nome) {
		return candidatoService.findPessoaByNome(nome);
	}

	public String save() {
		if (super.save() != null) {
			return "/core/candidato/index.xhmtl";
		} else {
			return null;
		}

	}
}
