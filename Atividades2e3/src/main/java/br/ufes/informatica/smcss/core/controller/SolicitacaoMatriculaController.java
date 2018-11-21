package br.ufes.informatica.smcss.core.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.smcss.core.application.SolicitacaoMatriculaService;
import br.ufes.informatica.smcss.core.domain.SolicitacaoMatricula;

@Named @SessionScoped
public class SolicitacaoMatriculaController extends CrudController<SolicitacaoMatricula> {

    private static final long serialVersionUID = 1L;

    @EJB
    private SolicitacaoMatriculaService solicitacaoMatriculaService;

    @Override
    public CrudService<SolicitacaoMatricula> getCrudService() {
        return solicitacaoMatriculaService;
    }

    @Override
    public void initFilters() {

    }
}
