package br.ufes.informatica.smcss.core.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;

@StaticMetamodel(Pessoa.class)
public class Pessoa_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Pessoa, String> nome;
	public static volatile SingularAttribute<Pessoa, String> cpf;
	public static volatile SingularAttribute<Pessoa, String> email;
}