package br.ufes.informatica.smcss.core.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.event.SelectEvent;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.smcss.core.application.OfertaDisciplinaService;
import br.ufes.informatica.smcss.core.domain.Aula;
import br.ufes.informatica.smcss.core.domain.DiaDaSemana;
import br.ufes.informatica.smcss.core.domain.Disciplina;
import br.ufes.informatica.smcss.core.domain.OfertaDisciplina;
import br.ufes.informatica.smcss.core.domain.PeriodoLetivo;
import br.ufes.informatica.smcss.core.domain.Professor;

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

    @Override
    protected OfertaDisciplina createNewEntity() {
        // TODO Auto-generated method stub
        OfertaDisciplina ofertaDisciplina = super.createNewEntity();
        ofertaDisciplina.setPeriodoLetivo(periodoLetivo);
        return ofertaDisciplina;
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

    public <T> T selectDomainClass(SelectEvent event, Function<Long, T> getById) {
        AutoComplete autoComplete = (AutoComplete) event.getComponent();
        Object newValue = event.getObject();
        return newValue == null ? null : getById.apply(Long.valueOf(newValue.toString()));
    }

    protected <T> String get(Function<OfertaDisciplina, T> field, Function<T, String> subField) {
        if (selectedEntity == null)
            return null;
        T object = field.apply(selectedEntity);
        return (object == null) ? null : subField.apply(object);
    }

    /**
     * Seleciona disciplina ao final do autocompletar.
     *
     * @param event Evento de seleção do autocompletar, com atributo object contendo
     *              o valor selecionado.
     */
    public void onSelectDisciplina(SelectEvent event) {
        selectedEntity.setDisciplina(selectDomainClass(event, ofertaDisciplinaService::retrieveDisciplinaById));
    }

    public List<Professor> completeProfessor(String query) {
        return ofertaDisciplinaService.findProfessorByNome(query);
    }

    public String getNomeProfessor() {
        return get(OfertaDisciplina::getProfessor, Professor::getNome);
    }

    public void setNomeProfessor(String nomeProfessor) {

    }

    public String getCodigoPeriodoLetivo() {
        return get(OfertaDisciplina::getPeriodoLetivo, PeriodoLetivo::getCodigo);
    }

    public void setCodigoPeriodoLetivo(String codigoPeriodoLetivo) {

    }

    /**
     * Seleciona professor ao final do autocompletar.
     *
     * @param event Evento de seleção do autocompletar, com atributo object contendo
     *              o valor selecionado.
     */
    public void onSelectProfessor(SelectEvent event) {
        selectedEntity.setProfessor(selectDomainClass(event, ofertaDisciplinaService::retrieveProfessorById));
    }

    public List<PeriodoLetivo> completePeriodoLetivo(String query) {
        return ofertaDisciplinaService.findPeriodoLetivoByCodigo(query);
    }

    /**
     * Seleciona professor ao final do autocompletar.
     *
     * @param event Evento de seleção do autocompletar, com atributo object contendo
     *              o valor selecionado.
     */
    public void onSelectPeriodoLetivo(SelectEvent event) {
        selectedEntity.setPeriodoLetivo(selectDomainClass(event, ofertaDisciplinaService::retrievePeriodoLetivoById));
    }

    public void setTextoDisciplina(String value) {

    }

    public String getTextoDisciplina() {
        return get(OfertaDisciplina::getDisciplina,
                (disciplina) -> String.format("%s: %s", disciplina.getCodigo(), disciplina.getNome()));
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

        // Since the entity count might have changed, force reloading of the lazy entity
        // model.
        lazyEntities = null;

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

    @Override
    protected void checkSelectedEntity() {
        loadAulaUI();
    }

    @Override
    protected void prepEntity() {
        saveAulaUI();
    }

    protected void loadAulaUI() {

        for (AulaUI aulaUI : aulaUIArray) {
            for (AulaUI.SituacaoDiaDaSemana situacao : aulaUI.diasDaSemana) {
                situacao.setSelecionado(false);
            }
        }

        for (Aula aula : selectedEntity.getAulas()) {
            DiaDaSemana diaDaSemana = aula.getDiaDaSemana();
            long horarioInicio = aula.getHorarioInicio().getTime() / (60 * 60 * 1000);
            long horarioTermino = aula.getHorarioTermino().getTime() / (60 * 60 * 1000);
            for (int horario = (int) horarioInicio; horario < horarioTermino; horario++) {
                if (horario >= HORA_INICIAL && horario < HORA_FINAL) {
                    aulaUIArray[horario - HORA_INICIAL].getSituacao(diaDaSemana).setSelecionado(true);
                }
            }
        }
    }

    protected void saveAulaUI() {

        List<Aula> aulas = new ArrayList<Aula>();

        Aula aula = null;
        for (DiaDaSemana diaDaSemana : DiaDaSemana.values()) {
            for (int horario = HORA_INICIAL; horario < HORA_FINAL; horario++) {
                if (aulaUIArray[horario - HORA_INICIAL].getSituacao(diaDaSemana).selecionado) {
                    if (aula == null) {
                        aula = new Aula();
                        aula.setOfertaDisciplina(selectedEntity);
                        aula.setDiaDaSemana(diaDaSemana);
                        aula.setHorarioInicio(new Date(horario * 60 * 60 * 1000));
                        aulas.add(aula);
                    }
                } else {
                    if (aula != null) {
                        aula.setHorarioTermino(new Date(horario * 60 * 60 * 1000));
                        aula = null;
                    }
                }
            }
            if (aula != null) {
                aula.setHorarioTermino(new Date(HORA_FINAL * 60 * 60 * 1000));
                aula = null;
            }
        }
        selectedEntity.setAulas(aulas);
    }

    private final AulaUI[] aulaUIArray = initAulas();
    private static final int HORA_INICIAL = 6;
    private static final int HORA_FINAL = 22;

    private AulaUI[] initAulas() {
        final AulaUI[] aulas = new AulaUI[HORA_FINAL - HORA_INICIAL];

        for (int hora = HORA_INICIAL; hora < HORA_FINAL; hora++) {
            aulas[hora - HORA_INICIAL] = new AulaUI(hora);
        }
        return aulas;
    }

    public AulaUI[] getAulas() {
        return aulaUIArray;
    }

    public DiaDaSemana[] getDiasDaSemana() {
        return DiaDaSemana.values();
    }

    public class AulaUI {

        private int horaInicial;

        private final SituacaoDiaDaSemana[] diasDaSemana;

        AulaUI(int horaInicial) {
            this.horaInicial = horaInicial;
            this.diasDaSemana = new SituacaoDiaDaSemana[DiaDaSemana.values().length];
            for (int i = 0; i < this.diasDaSemana.length; i++) {
                this.diasDaSemana[i] = new SituacaoDiaDaSemana();
            }
        }

        public SituacaoDiaDaSemana[] getDiasDaSemana() {
            return diasDaSemana;
        }

        public SituacaoDiaDaSemana getSituacao(DiaDaSemana diaDaSemana) {
            return this.diasDaSemana[diaDaSemana.ordinal()];
        }

        public String getHorarioInicial() {
            return (horaInicial) + "h";
        }

        public String getHorarioFinal() {
            return (horaInicial + 1) + "h";
        }

        public class SituacaoDiaDaSemana {

            private boolean selecionado;

            public void toggle() {
                this.selecionado = !this.selecionado;
            }

            public void setSelecionado(boolean selecionado) {
                this.selecionado = selecionado;
            }

            public String getStyleClass() {
                return selecionado ? "selecionado" : "";
            }
        }
    }
}
