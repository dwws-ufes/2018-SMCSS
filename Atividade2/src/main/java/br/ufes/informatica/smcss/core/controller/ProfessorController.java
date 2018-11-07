package br.ufes.informatica.smcss.core.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.smcss.core.application.ProfessorService;
import br.ufes.informatica.smcss.core.domain.Professor;

@Named @SessionScoped
public class ProfessorController extends CrudController<Professor> {

    private static final long serialVersionUID = 1L;

    @EJB
    private ProfessorService professorService;

    @Override
    public CrudService<Professor> getCrudService() {
        return professorService;
    }

    @Override
    public void initFilters() {

    }
}
