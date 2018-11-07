package br.ufes.informatica.smcss.core.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.smcss.core.application.CandidatoService;
import br.ufes.informatica.smcss.core.domain.Candidato;

@Named @SessionScoped
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
}
