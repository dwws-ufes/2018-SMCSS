package br.ufes.informatica.smcss.core.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.smcss.core.application.AulaService;
import br.ufes.informatica.smcss.core.domain.Aula;

@Named @SessionScoped
public class AulaController extends CrudController<Aula> {

    private static final long serialVersionUID = 1L;

    @EJB
    private AulaService aulaService;

    @Override
    public CrudService<Aula> getCrudService() {
        return aulaService;
    }

    @Override
    public void initFilters() {

    }
}
