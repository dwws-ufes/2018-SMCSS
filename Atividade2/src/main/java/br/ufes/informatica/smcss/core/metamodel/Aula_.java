package br.ufes.informatica.smcss.core.metamodel;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.smcss.core.domain.Aula;

@StaticMetamodel(Aula.class)
public abstract class Aula_ extends PersistentObjectSupport_ {
    public static final String OFERTADISCIPLINA="ofertaDisciplina";
    public static volatile SingularAttribute<Aula, OfertaDisciplina> ofertaDisciplina;
    public static final String DIADASEMANA="diaDaSemana";
    public static volatile SingularAttribute<Aula, DiaDaSemana> diaDaSemana;
    public static final String HORARIOINICIO="horarioInicio";
    public static volatile SingularAttribute<Aula, Date> horarioInicio;
    public static final String HORARIOTERMINO="horarioTermino";
    public static volatile SingularAttribute<Aula, Date> horarioTermino;
}
