package br.ufes.informatica.smcss.core.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.smcss.core.application.PessoaService;
import br.ufes.informatica.smcss.core.domain.Pessoa;

@Named @SessionScoped
public class PessoaController extends CrudController<Pessoa> {

    private static final long serialVersionUID = 1L;

    @EJB
    private PessoaService pessoaService;

    @Override
    public CrudService<Pessoa> getCrudService() {
        return pessoaService;
    }

    @Override
    public void initFilters() {

    }
}
