package br.ufes.informatica.smcss.core.controller;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufes.informatica.smcss.core.application.PeriodoLetivoService;
import br.ufes.informatica.smcss.core.domain.PeriodoLetivo;

@Named @SessionScoped
public class NavegacaoPeriodoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private PeriodoLetivoService periodoLetivoService;
    
    @Inject
    private Event<PeriodoLetivo> periodoLetivoChange;

    private PeriodoLetivo periodoLetivo;
    private PeriodoLetivo periodoLetivoSeguinte;
    private PeriodoLetivo periodoLetivoAnterior;

    /**
     * Inicia período letivo. Busca iniciar com o período letivo anterior à data atual, 
     * se houver. Senão, busca período letivo seguinte à data atual. Em último caso,
     * periodoLetivo permanece nulo.
     */
    @Inject
    void initPeriodoLetivo() {
        PeriodoLetivo periodoLetivo = new PeriodoLetivo();
        periodoLetivo.getDuracao().setInicio(new Date());
        periodoLetivo = periodoLetivoService.retrievePeriodoLetivoAnterior(periodoLetivo);
        this.periodoLetivo = periodoLetivo != null ? periodoLetivo :
                periodoLetivoService.retrievePeriodoLetivoSeguinte(periodoLetivo);
    }

    public PeriodoLetivo getPeriodoLetivo() {
        return periodoLetivo;
    }

    public void setPeriodoLetivo(PeriodoLetivo periodoLetivo) {
        if (periodoLetivo != null && periodoLetivo != this.periodoLetivo) {
            this.periodoLetivo = periodoLetivo;
            this.periodoLetivoAnterior = periodoLetivoService.retrievePeriodoLetivoAnterior(periodoLetivo);
            this.periodoLetivoSeguinte = periodoLetivoService.retrievePeriodoLetivoSeguinte(periodoLetivo);
            periodoLetivoChange.fire(periodoLetivo);
        }
    }

    public PeriodoLetivo getPeriodoLetivoSeguinte() {
        return periodoLetivoSeguinte;
    }

    public PeriodoLetivo getPeriodoLetivoAnterior() {
        return periodoLetivoAnterior;
    }
    
    public boolean navegarParaPeriodoLetivoSeguinte() {
        if (periodoLetivoSeguinte != null) {
            this.setPeriodoLetivo(periodoLetivoSeguinte);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean navegarParaPeriodoLetivoAnterior() {
        if (periodoLetivoAnterior != null) {
            this.setPeriodoLetivo(periodoLetivoAnterior);
            return true;
        } else {
            return false;
        }
    }

    public boolean existePeriodoLetivoSeguinte() {
        return periodoLetivoSeguinte != null;
    }

    public boolean existePeriodoLetivoAnterior() {
        return periodoLetivoAnterior != null;
    }
    
    public static class PeriodoLetivoChangeEvent {
        
        private final PeriodoLetivo periodoLetivo;

        public PeriodoLetivoChangeEvent(PeriodoLetivo periodoLetivo) {
            this.periodoLetivo = periodoLetivo;
        }
        
        public PeriodoLetivo getPeriodoLetivo() {
            return periodoLetivo;
        }
    }
}
