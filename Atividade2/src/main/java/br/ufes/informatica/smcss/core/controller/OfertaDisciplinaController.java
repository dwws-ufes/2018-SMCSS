package br.ufes.informatica.smcss.core.controller;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.smcss.core.application.OfertaDisciplinaService;
import br.ufes.informatica.smcss.core.domain.Disciplina;
import br.ufes.informatica.smcss.core.domain.OfertaDisciplina;
import br.ufes.informatica.smcss.core.domain.PeriodoLetivo;

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

    private PeriodoLetivo periodoLetivo;

    @Inject
    void initPeriodoLetivo() {
        this.periodoLetivo = new PeriodoLetivo();
        this.periodoLetivo.getDuracao().setInicio(new Date());
        PeriodoLetivo periodoLetivo = retrievePeriodoLetivoAnterior();
        this.periodoLetivo = periodoLetivo == null ? retrievePeriodoLetivoSeguinte() : periodoLetivo;
    }

    public PeriodoLetivo getPeriodoLetivo() {
        return periodoLetivo;
    }

    public void setPeriodoLetivo(PeriodoLetivo periodoLetivo) {
        if (periodoLetivo != null && periodoLetivo != this.periodoLetivo) {
            this.periodoLetivo = periodoLetivo;
            goFirst();
        }
    }

    private PeriodoLetivo retrievePeriodoLetivoSeguinte() {
        return ofertaDisciplinaService.retrievePeriodoLetivoSeguinte(periodoLetivo);
    }

    public boolean existePeriodoLetivoSeguinte() {
        return retrievePeriodoLetivoSeguinte() != null;
    }

    public void navegarParaPeriodoLetivoSeguinte() {
        setPeriodoLetivo(retrievePeriodoLetivoSeguinte());
    }

    private PeriodoLetivo retrievePeriodoLetivoAnterior() {
        return ofertaDisciplinaService.retrievePeriodoLetivoAnterior(periodoLetivo);
    }

    public boolean existePeriodoLetivoAnterior() {
        return retrievePeriodoLetivoAnterior() != null;
    }

    public void navegarParaPeriodoLetivoAnterior() {
        setPeriodoLetivo(retrievePeriodoLetivoAnterior());
    }

    public List<Disciplina> completeDisciplina(String query) {
        return ofertaDisciplinaService.findDisciplinaByCodigoOrNome(query);
    }

    /**
     * Sobrescrevendo métodos da classe base para implementar filtragem por período, em
     * função da falta de tempo hábil para estudar melhor o sistema de filtragem do JButler
     */
    protected void count() {

        if (periodoLetivo == null) {
            super.count();
            return;
        }

        entityCount = ofertaDisciplinaService.countOfertasByPeriodoLetivo(periodoLetivo);

        // Updates the index of the last entity and checks if it has gone over the limit.
        lastEntityIndex = firstEntityIndex + MAX_DATA_TABLE_ROWS_PER_PAGE;
        if (lastEntityIndex > entityCount) lastEntityIndex = (int) entityCount;
    }

    /**
     * Sobrescrevendo métodos da classe base para implementar filtragem por período, em
     * função da falta de tempo hábil para estudar melhor o sistema de filtragem do JButler
     */
    @Override
    protected void retrieveEntities() {

        if (periodoLetivo == null) {
            super.retrieveEntities();
            return;
        }
        // Checks if the last entity index is over the number of entities and correct it.
        if (lastEntityIndex > entityCount) lastEntityIndex = (int) entityCount;

        // Checks if there's an active filter.
        entities = ofertaDisciplinaService.listOfertasByPeriodoLetivo(
                periodoLetivo, firstEntityIndex, lastEntityIndex);

        // Adjusts the last entity index.
        lastEntityIndex = firstEntityIndex + entities.size();
    }

}
