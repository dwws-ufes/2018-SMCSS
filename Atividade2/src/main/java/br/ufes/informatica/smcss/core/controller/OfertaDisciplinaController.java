package br.ufes.informatica.smcss.core.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.smcss.core.application.OfertaDisciplinaService;
import br.ufes.informatica.smcss.core.domain.OfertaDisciplina;

@Named @SessionScoped
public class OfertaDisciplinaController extends CrudController<OfertaDisciplina> {

    private static final long serialVersionUID = 1L;

    @EJB
    private OfertaDisciplinaService ofertaDisciplinaService;

    @Override
    public CrudService<OfertaDisciplina> getCrudService() {
        return ofertaDisciplinaService;
    }

    @Override
    public void initFilters() {

    }
}
