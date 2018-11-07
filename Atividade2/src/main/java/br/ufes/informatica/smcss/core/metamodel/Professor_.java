package br.ufes.informatica.smcss.core.metamodel;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.smcss.core.domain.Professor;

@StaticMetamodel(Professor.class)
public abstract class Professor_ extends PersistentObjectSupport_ {
    public static final String PESSOA="pessoa";
    public static volatile SingularAttribute<Professor, Pessoa> pessoa;
}
