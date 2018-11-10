package br.ufes.informatica.smcss.core.metamodel;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.smcss.core.domain.Curso;

@StaticMetamodel(Curso.class)
public abstract class Curso_ extends PersistentObjectSupport_ {
    public static final String NOME="nome";
    public static volatile SingularAttribute<Curso, String> nome;
}
