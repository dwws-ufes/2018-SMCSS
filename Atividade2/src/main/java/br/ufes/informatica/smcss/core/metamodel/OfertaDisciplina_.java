package br.ufes.informatica.smcss.core.metamodel;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.smcss.core.domain.Disciplina;
import br.ufes.informatica.smcss.core.domain.OfertaDisciplina;
import br.ufes.informatica.smcss.core.domain.PeriodoLetivo;
import br.ufes.informatica.smcss.core.domain.Professor;

@StaticMetamodel(OfertaDisciplina.class)
public abstract class OfertaDisciplina_ extends PersistentObjectSupport_ {
    public static final String DISCIPLINA="disciplina";
    public static volatile SingularAttribute<OfertaDisciplina, Disciplina> disciplina;
    public static final String PROFESSOR="professor";
    public static volatile SingularAttribute<OfertaDisciplina, Professor> professor;
    public static final String PERIODOLETIVO="periodoLetivo";
    public static volatile SingularAttribute<OfertaDisciplina, PeriodoLetivo> periodoLetivo;
}
