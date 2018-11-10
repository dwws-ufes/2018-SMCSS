package br.ufes.informatica.smcss.core.domain;

import javax.persistence.AttributeConverter;

public enum SituacaoSolicitacaoMatricula {

	AGUARDANDO_APROVACAO_ORIENTADOR, AGUARDANDO_ALTERACAO_ALUNO, AGUARDANDO_CONFIRMACAO_SECRETARIA, CONFIRMADA;

	@javax.persistence.Converter
	static public class Converter implements AttributeConverter<SituacaoSolicitacaoMatricula, Integer> {

		@Override
		public Integer convertToDatabaseColumn(SituacaoSolicitacaoMatricula situacao) {
			return situacao.ordinal();
		}

		@Override
		public SituacaoSolicitacaoMatricula convertToEntityAttribute(Integer indice) {
			return SituacaoSolicitacaoMatricula.values()[indice];
		}
	}
}
