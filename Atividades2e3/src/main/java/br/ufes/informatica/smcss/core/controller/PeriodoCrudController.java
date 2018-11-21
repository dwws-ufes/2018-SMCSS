package br.ufes.informatica.smcss.core.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.smcss.core.application.PeriodoLetivoService;
import br.ufes.informatica.smcss.core.domain.PeriodoLetivo;

@Named @SessionScoped
public class PeriodoCrudController extends CrudController<PeriodoLetivo> {
	private static final long serialVersionUID = 1L;

	@EJB
	private PeriodoLetivoService periodoLetivoService;

	@Override
	protected CrudService<PeriodoLetivo> getCrudService() {
		return periodoLetivoService;
	}

	@Override
	protected void initFilters() {}
}
