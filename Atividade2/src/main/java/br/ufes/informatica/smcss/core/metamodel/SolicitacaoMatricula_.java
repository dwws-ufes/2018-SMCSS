package br.ufes.informatica.smcss.core.metamodel;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.smcss.core.domain.Aluno;
import br.ufes.informatica.smcss.core.domain.SituacaoSolicitacaoMatricula;
import br.ufes.informatica.smcss.core.domain.SolicitacaoMatricula;

@StaticMetamodel(SolicitacaoMatricula.class)
public abstract class SolicitacaoMatricula_ extends PersistentObjectSupport_ {
    public static final String ALUNO="aluno";
    public static volatile SingularAttribute<SolicitacaoMatricula, Aluno> aluno;
    public static final String SITUACAO="situacao";
    public static volatile SingularAttribute<SolicitacaoMatricula, SituacaoSolicitacaoMatricula> situacao;
}
