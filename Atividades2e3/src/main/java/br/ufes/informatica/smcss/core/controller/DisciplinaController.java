package br.ufes.informatica.smcss.core.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.smcss.core.application.DisciplinaService;
import br.ufes.informatica.smcss.core.domain.Curso;
import br.ufes.informatica.smcss.core.domain.Disciplina;

@Named @SessionScoped
public class DisciplinaController extends CrudController<Disciplina> {

    private static final long serialVersionUID = 1L;

    @EJB
    private DisciplinaService disciplinaService;

    @Override
    public CrudService<Disciplina> getCrudService() {
        return disciplinaService;
    }

    @Override
    public void initFilters() {

    }

    public List<Curso> getCursos() {
        return disciplinaService.listCursos();
    }
}
