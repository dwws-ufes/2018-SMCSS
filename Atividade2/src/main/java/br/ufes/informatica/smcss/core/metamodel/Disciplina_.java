package br.ufes.informatica.smcss.core.metamodel;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.smcss.core.domain.Disciplina;

@StaticMetamodel(Disciplina.class)
public abstract class Disciplina_ extends PersistentObjectSupport_ {
    public static final String CURSO="curso";
    public static volatile SingularAttribute<Disciplina, Curso> curso;
    public static final String CODIGO="codigo";
    public static volatile SingularAttribute<Disciplina, String> codigo;
    public static final String NOME="nome";
    public static volatile SingularAttribute<Disciplina, String> nome;
    public static final String CARGAHORARIA="cargaHoraria";
    public static volatile SingularAttribute<Disciplina, String> cargaHoraria;
}
