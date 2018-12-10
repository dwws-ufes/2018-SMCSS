package br.ufes.informatica.smcss.core.domain;

import br.ufes.informatica.smcss.util.EnumConverter;

public enum SituacaoSolicitacaoMatricula {

	AGUARDANDO_APROVACAO_ORIENTADOR, AGUARDANDO_ALTERACAO_ALUNO, AGUARDANDO_CONFIRMACAO_SECRETARIA, CONFIRMADA;

	@javax.persistence.Converter
	public static class Converter extends EnumConverter<SituacaoSolicitacaoMatricula> {}
}
