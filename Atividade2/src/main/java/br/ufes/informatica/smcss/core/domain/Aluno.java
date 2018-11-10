package br.ufes.informatica.smcss.core.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class Aluno extends PersistentObjectSupport {
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@NotNull
	private Candidato candidato;

	@NotNull
	private String numeroMatricula;

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	@Override
	@javax.persistence.Transient
	public String toString() {
		return "Aluno{id:" + this.getId() + "}";
	}
}
