package br.ufes.informatica.smcss.core.domain;

import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class Aula extends PersistentObjectSupport {

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@ManyToOne
	private OfertaDisciplina ofertaDisciplina;

	@NotNull
	@Convert(converter=DiaDaSemana.Converter.class)
	private DiaDaSemana diaDaSemana;

	@NotNull
	@Temporal(TemporalType.TIME)
	private Date horarioInicio;

	@NotNull
	@Temporal(TemporalType.TIME)
	private Date horarioTermino;

	public OfertaDisciplina getOfertaDisciplina() {
		return ofertaDisciplina;
	}

	public void setOfertaDisciplina(OfertaDisciplina ofertaDisciplina) {
		this.ofertaDisciplina = ofertaDisciplina;
	}

	public DiaDaSemana getDiaDaSemana() {
		return diaDaSemana;
	}

	public void setDiaDaSemana(DiaDaSemana diaDaSemana) {
		this.diaDaSemana = diaDaSemana;
	}

	public Date getHorarioInicio() {
		return horarioInicio;
	}

	public void setHorarioInicio(Date horarioInicio) {
		this.horarioInicio = horarioInicio;
	}

	public Date getHorarioTermino() {
		return horarioTermino;
	}

	public void setHorarioTermino(Date horarioTermino) {
		this.horarioTermino = horarioTermino;
	}

	@Override
	@javax.persistence.Transient
	public String toString() {
		return "Aula{id:" + this.getId() + "}";
	}
}
