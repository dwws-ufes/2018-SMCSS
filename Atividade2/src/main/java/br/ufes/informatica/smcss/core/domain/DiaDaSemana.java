package br.ufes.informatica.smcss.core.domain;

import javax.persistence.AttributeConverter;


public enum DiaDaSemana {
	// Ordem da enumeração não pode ser alterada sob pena de geração de incoerência dos dados no banco,
	// a não ser que a implementação das rotinas de conversão da classe interna Converter sejam adequadamente
	// alterados.
	DOM, SEG, TER, QUA, QUI, SEX, SAB;

	@javax.persistence.Converter
	static public class Converter implements AttributeConverter<DiaDaSemana, Integer> {

		@Override
		public Integer convertToDatabaseColumn(DiaDaSemana diaDaSemana) {
			return diaDaSemana.ordinal();
		}

		@Override
		public DiaDaSemana convertToEntityAttribute(Integer indice) {
			return DiaDaSemana.values()[indice];
		}
	}
}
