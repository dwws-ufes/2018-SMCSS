package br.ufes.informatica.smcss.core.metamodel;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.smcss.core.domain.Aluno;

@StaticMetamodel(Aluno.class)
public abstract class Aluno_ extends PersistentObjectSupport_ {
    public static final String CANDIDATO="candidato";
    public static volatile SingularAttribute<Aluno, Candidato> candidato;
    public static final String NUMEROMATRICULA="numeroMatricula";
    public static volatile SingularAttribute<Aluno, String> numeroMatricula;
}
