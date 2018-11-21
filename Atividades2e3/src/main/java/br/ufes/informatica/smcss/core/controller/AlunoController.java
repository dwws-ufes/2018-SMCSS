package br.ufes.informatica.smcss.core.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.smcss.core.application.AlunoService;
import br.ufes.informatica.smcss.core.domain.Aluno;

@Named @SessionScoped
public class AlunoController extends CrudController<Aluno> {

    private static final long serialVersionUID = 1L;

    @EJB
    private AlunoService alunoService;

    @Override
    public CrudService<Aluno> getCrudService() {
        return alunoService;
    }

    @Override
    public void initFilters() {

    }
}
