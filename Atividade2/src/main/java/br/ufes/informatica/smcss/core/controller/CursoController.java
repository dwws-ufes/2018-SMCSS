package br.ufes.informatica.smcss.core.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.smcss.core.application.CursoService;
import br.ufes.informatica.smcss.core.domain.Curso;

@Named @SessionScoped
public class CursoController extends CrudController<Curso> {

    private static final long serialVersionUID = 1L;

    @EJB
    private CursoService cursoService;

    @Override
    public CrudService<Curso> getCrudService() {
        return cursoService;
    }

    @Override
    public void initFilters() {

    }
}
