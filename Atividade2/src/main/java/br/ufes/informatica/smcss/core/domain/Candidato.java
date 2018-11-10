package br.ufes.informatica.smcss.core.domain;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class Candidato extends PersistentObjectSupport {
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@NotNull
	private PeriodoLetivo periodoLetivo;

	@NotNull
	private Pessoa pessoa;

	@Min(0)
	private int pontuacaoAlcancada;

	@Min(1)
	private int classificacaoFinal;

	public PeriodoLetivo getPeriodoLetivo() {
		return periodoLetivo;
	}

	public void setPeriodoLetivo(PeriodoLetivo periodoLetivo) {
		this.periodoLetivo = periodoLetivo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public int getPontuacaoAlcancada() {
		return pontuacaoAlcancada;
	}

	public void setPontuacaoAlcancada(int pontuacaoAlcancada) {
		this.pontuacaoAlcancada = pontuacaoAlcancada;
	}

	public int getClassificacaoFinal() {
		return classificacaoFinal;
	}

	public void setClassificacaoFinal(int classificacaoFinal) {
		this.classificacaoFinal = classificacaoFinal;
	}

	@Override
	@javax.persistence.Transient
	public String toString() {
		return "Candidato{id:" + this.getId() + "}";
	}
}
