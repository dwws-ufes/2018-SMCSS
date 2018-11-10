package br.ufes.informatica.smcss.core.metamodel;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.smcss.core.domain.Candidato;
import br.ufes.informatica.smcss.core.domain.PeriodoLetivo;
import br.ufes.informatica.smcss.core.domain.Pessoa;

@StaticMetamodel(Candidato.class)
public abstract class Candidato_ extends PersistentObjectSupport_ {
    public static final String PERIODOLETIVO="periodoLetivo";
    public static volatile SingularAttribute<Candidato, PeriodoLetivo> periodoLetivo;
    public static final String PESSOA="pessoa";
    public static volatile SingularAttribute<Candidato, Pessoa> pessoa;
    public static final String PONTUACAOALCANCADA="pontuacaoAlcancada";
	public static volatile SingularAttribute<Candidato, Integer> pontuacaoAlcancada;
    public static final String CLASSIFICACAOFINAL="classificacaoFinal";
	public static volatile SingularAttribute<Candidato, Integer> classificacaoFinal;
}
