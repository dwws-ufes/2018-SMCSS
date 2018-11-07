package br.ufes.informatica.smcss.core.metamodel;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport_;
import br.ufes.informatica.smcss.core.domain.PeriodoLetivo;

@StaticMetamodel(PeriodoLetivo.class)
public abstract class PeriodoLetivo_ extends PersistentObjectSupport_ {
    public static final String CODIGO="codigo";
    public static volatile SingularAttribute<PeriodoLetivo, String> codigo;
    public static final String DURACAO="duracao";
    public static volatile SingularAttribute<PeriodoLetivo, Periodo> duracao;
    public static final String PERIODOMATRICULA="periodoMatricula";
    public static volatile SingularAttribute<PeriodoLetivo, Periodo> periodoMatricula;
    public static final String PERIODOINSCRICAO="periodoInscricao";
    public static volatile SingularAttribute<PeriodoLetivo, Periodo> periodoInscricao;
    public static final String PERIODOAJUSTEMATRICULA="periodoAjusteMatricula";
    public static volatile SingularAttribute<PeriodoLetivo, Periodo> periodoAjusteMatricula;
}
