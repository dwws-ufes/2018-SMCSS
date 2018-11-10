package br.ufes.informatica.smcss.core.metamodel;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.smcss.core.domain.Pessoa;

@StaticMetamodel(Pessoa.class)
public abstract class Pessoa_ extends PersistentObjectSupport_ {
    public static final String NOME="nome";
    public static volatile SingularAttribute<Pessoa, String> nome;
    public static final String EMAIL="email";
    public static volatile SingularAttribute<Pessoa, String> email;
    public static final String CPF="cpf";
    public static volatile SingularAttribute<Pessoa, String> cpf;
    public static final String DATANASCIMENTO="dataNascimento";
    public static volatile SingularAttribute<Pessoa, Date> dataNascimento;
}
